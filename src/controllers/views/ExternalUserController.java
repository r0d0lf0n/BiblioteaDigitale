/**
 * 
 */
package controllers.views;

import java.util.ArrayList;
import java.util.List;

import controllers.bl.GestoreRicerche;
import models.db.BookDAO;
import models.users.Utente;
import utils.Observable;
import utils.Observer;

/**
 * 
 */
public class ExternalUserController implements Observable, GenericController{
	
	private List<Observer> observers = null;
    private Object obj;
	
	public ExternalUserController() {
		observers = new ArrayList<Observer>();
	}

    private void setChanged(String type, Object obj) {
        this.obj = obj;
        notifyObservers(type, obj); // Notifica gli osservatori con il nuovo stato
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String type, Object arg) {
        for (Observer observer : observers) {
            observer.update(type, arg); // Aggiorna ciascun osservatore con il nuovo stato
        }
    }
	

	public void search(String isbn, String autore, String titolo, String casaEditrice, String anno, Utente user) {
		try {
			 GestoreRicerche.getInstance().search(isbn, autore, titolo, casaEditrice, anno, user, this);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void returnSearchResults(List<BookDAO> books, Utente user) {
		
		Object[] couple = new Object[2];
		couple[0] = books;
		couple[1] = user;
		setChanged("NEW_SEARCH_READY", couple);
		
	}


}
