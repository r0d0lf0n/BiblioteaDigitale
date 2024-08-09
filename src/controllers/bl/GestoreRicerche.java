package controllers.bl;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public void search(String isbn, String autore, String titolo, String casaEditrice, String anno, Utente user){
		
		boolean isbnValid = isbn!=null && !isbn.trim().equals("");
		boolean autoreValid = autore!=null && !autore.trim().equals("");
		boolean titoloValid = titolo!=null && !titolo.trim().equals("");
		boolean casaEditriceValid = casaEditrice!=null && !casaEditrice.trim().equals("");
		boolean annoValid = anno!=null && anno.matches("\\d{4}");
		
		synchronized (this) {
			
			String[] params = {"","","","",""};
			
			if(isbnValid)
				params[0]=isbn.trim();
			if(autoreValid)
				params[1]=autore.trim();
			if(titoloValid)
				params[2]=titolo.trim();
			if(casaEditriceValid)
				params[3]=casaEditrice.trim();
			if(annoValid)
				params[4]=anno.trim();
			
			if(user != null) 
				new SearchThread(user, params).start();
			else
				new SearchThread(null, params).start();
			
		}
	}
	
	public class SearchThread extends Thread {
	    private Utente user = null;
	    private String[] params;

	    public SearchThread(Utente user, String[] params ) {
	        this.user = user;
	        this.params = params;
	    }

	    @Override
	    public void run() {
	        List<BookDAO> results = model.getAllBooks().stream()
	            .filter(book -> 
	                (!params[0].equals("") && book.getIsbn().contains(params[0])) ||
	                (!params[1].equals("") && book.getAuthor().contains(params[1])) ||
	                (!params[2].equals("") && book.getTitle().contains(params[2])) ||
	                (!params[3].equals("") && book.getEditor().contains(params[3])) ||
	                (!params[4].equals("") && book.getYear().equals(params[4]))
	            )
	            .collect(Collectors.toList());

	        processResults(results);
	    }

	    private void processResults(List<BookDAO> results) {
	        if (user != null) {
	            // Invia i risultati all'utente o fai altre operazioni necessarie
	          // user.notifySearchResults(results);
	        	System.out.println(results);
	        } else {
	            // Altri comportamenti per quando l'utente è null
	            System.out.println("Risultati trovati: " + results.size()+" "+results);
	        }
	    }
	}

	public void setCatalog(CatalogModel catalogModel) {
		this.model = catalogModel;
		
	}
	
}
