/**
 * 
 */
package controllers.views;

import java.util.ArrayList;
import java.util.List;

import utils.Observable;
import utils.Observer;

/**
 * 
 */
public class ExternalUserController implements Observable{
	
	private List<Observer> observers = null;
    private Object obj;
	
	public ExternalUserController() {
		observers = new ArrayList<Observer>();
	}

    public void setChanged(String type, Object obj) {
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
	
	
	public void getGenereList() {
		String[] list = {"LISTA", "DA", "RIEMPIRE", "DA", "DB"}; //TODO itera sulle categorie, crea la lista e torna il risultato tramite observer
		
		setChanged("COMBOBOX_GENERI", list);
		
	}

	public void search(String text, String text2, String string) {
		// TODO Auto-generated method stub
		String[] results = {};
		
		setChanged("SEARCH_RESULTS", results);
	}

}
