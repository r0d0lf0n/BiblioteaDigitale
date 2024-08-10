package controllers.bl;

import java.util.ArrayList;
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
		
		boolean isbnValid = isbn!=null && !isbn.equals("");
		boolean autoreValid = autore!=null && !autore.equals("");
		boolean titoloValid = titolo!=null && !titolo.equals("");
		boolean casaEditriceValid = casaEditrice!=null && !casaEditrice.equals("");
		boolean annoValid = anno!=null && anno.matches("\\d{4}");
		
		synchronized (this) {
			
			String[] params = {"","","","",""};
			
			if(isbnValid)
				params[0]=isbn;
			if(autoreValid)
				params[1]=autore;
			if(titoloValid)
				params[2]=titolo;
			if(casaEditriceValid)
				params[3]=casaEditrice;
			if(annoValid)
				params[4]=anno;
			
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
	    	List<BookDAO> results = new ArrayList<BookDAO>();

		    boolean hasValidParams = false;
		    for (String param : params) {
		        if (param != null && !param.trim().isEmpty()) {
		            hasValidParams = true;
		            break;
		        }
		    }
		    if (hasValidParams) {
		        results = model.getAllBooks().stream()
		            .filter(book -> {
		                boolean matches = true;

		                if (params[0] != null && !params[0].trim().isEmpty()) {
		                    matches = matches && book.getIsbn().toLowerCase().contains(params[0].trim().toLowerCase());
		                }
		                if (params[1] != null && !params[1].trim().isEmpty()) {
		                    matches = matches && book.getAuthor().toLowerCase().contains(params[1].trim().toLowerCase());
		                }
		                if (params[2] != null && !params[2].trim().isEmpty()) {
		                    matches = matches && book.getTitle().toLowerCase().contains(params[2].trim().toLowerCase());
		                }
		                if (params[3] != null && !params[3].trim().isEmpty()) {
		                    matches = matches && book.getEditor().toLowerCase().contains(params[3].trim().toLowerCase());
		                }
		                if (params[4] != null && !params[4].trim().isEmpty()) {
		                    matches = matches && book.getYear().equals(params[4].trim());
		                }

		                return matches;
		            })
		            .collect(Collectors.toList());
		    }

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
