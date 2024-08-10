package controllers.bl;

import java.util.List;
import java.util.stream.Collectors;

import controllers.views.GenericController;
import models.bl.CatalogModel;
import models.db.BookDAO;
import models.users.Utente;

public class GestoreRicerche {

	/**
	 * singleton
	 */
	private static GestoreRicerche _instance = null;
	private CatalogModel model = null;


	private GestoreRicerche() {
	}

	public static GestoreRicerche getInstance() {
		if (_instance == null)
			_instance = new GestoreRicerche();
		return _instance;
	}
	
	public void search(String isbn, String autore, String titolo, String casaEditrice, String anno, Utente user, GenericController controller){
		
		boolean isbnValid = isbn!=null && !isbn.trim().equals("");
		boolean autoreValid = autore!=null && !autore.trim().equals("");
		boolean titoloValid = titolo!=null && !titolo.trim().equals("");
		boolean casaEditriceValid = casaEditrice!=null && !casaEditrice.trim().equals("");
		boolean annoValid = anno!=null && anno.matches("\\d{4}");
		
		synchronized (this) {
			
			String[] params = {"","","","",""};
			
			if(isbnValid)
				params[0]=isbn.trim().toLowerCase();
			if(autoreValid)
				params[1]=autore.trim().toLowerCase();
			if(titoloValid)
				params[2]=titolo.trim().toLowerCase();
			if(casaEditriceValid)
				params[3]=casaEditrice.trim().toLowerCase();
			if(annoValid)
				params[4]=anno.trim().toLowerCase();
			
			if(user != null) {
				new SearchThread(controller, user, params).start();
			}
			else
				System.out.println("SEARCH - Violazione Utente");

		}
		
	}
	
	public class SearchThread extends Thread {
	    private Utente user = null;
	    private String[] params;
	    private GenericController controller = null;

	    public SearchThread(GenericController controller, Utente user, String[] params ) {
	        this.user = user;
	        this.params = params;
	        this.controller = controller;
	    }

	    @Override
	    public void run() {
	    	List<BookDAO> results = model.getAllBooks().stream()
		        .filter(book -> {
		            boolean matches = true;
	
		            if (params[0] != null) {
		                matches = matches && book.getIsbn().contains(params[0]);
		            }
		            if (params[1] != null) {
		                matches = matches && book.getAuthor().contains(params[1]);
		            }
		            if (params[2] != null) {
		                matches = matches && book.getTitle().contains(params[2]);
		            }
		            if (params[3] != null) {
		                matches = matches && book.getEditor().contains(params[3]);
		            }
		            if (params[4] != null) {
		                matches = matches && book.getYear().equals(params[4]);
		            }
	
		            return matches;
		        })
		        .collect(Collectors.toList());
	    	
	    	if(results != null)
	    		controller.returnSearchResults(results, user);
	    	else
	    		System.out.println("SEARCH - QUERY ERROR");
	    }
	}

	public void setCatalog(CatalogModel catalogModel) {
		this.model = catalogModel;
		
	}
	
}
