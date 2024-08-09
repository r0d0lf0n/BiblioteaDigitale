package controllers.bl;

import java.util.ArrayList;

import models.db.BookDAO;
import models.users.Utente;

public class GestoreRicerche {

	/**
	 * singleton
	 */
	private static GestoreRicerche _instance = null;


	private GestoreRicerche() {
	}

	public static GestoreRicerche getInstance() {
		if (_instance == null)
			_instance = new GestoreRicerche();
		return _instance;
	}
	
	public ArrayList<BookDAO> search(String isbn, String autore, String titolo, String casaEditrice, String anno, Utente user){
		
		boolean isbnValid = isbn!=null && !isbn.trim().equals("");
		boolean autoreValid = autore!=null && !autore.trim().equals("");
		boolean titoloValid = titolo!=null && !titolo.trim().equals("");
		boolean casaEditriceValid = casaEditrice!=null && !casaEditrice.trim().equals("");
		boolean annoValid = anno!=null && anno.matches("\\d{4}");
		
		synchronized (this) {
			
			if(isbnValid)
				System.out.println("isbn: "+isbn);
			if(autoreValid)
				System.out.println("autore: "+autore);
			if(titoloValid)
				System.out.println("titolo: "+titolo);
			if(casaEditriceValid)
				System.out.println("casa ed: "+casaEditrice);
			if(annoValid)
				System.out.println("anno: "+anno);
			
			if(user != null) {
				System.out.println("USER: "+user.getIdTessera());
			}
			//REALIZZA RICERCA SU DB CON GESTIONE DELLA CONCORRENZA
			// E THREAD PER OPERAZIONE ASINCRONA
			// TODO 
			
			return new ArrayList<BookDAO>();
		}
	}
	
}
