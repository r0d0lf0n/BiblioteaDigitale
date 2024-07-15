/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Observable;
import models.Observer;

/**
 * 
 */
public class ExternalUserController implements Observable{
	
	private List<Observer> observers = new ArrayList<Observer>();
    private Object obj;
	
	public ExternalUserController() {
		// empty
	}

    public void setChanged(Object obj) {
        this.obj = obj;
        notifyObservers(obj); // Notifica gli osservatori con il nuovo stato
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
    public void notifyObservers(Object arg) {
        for (Observer observer : observers) {
            observer.update(arg); // Aggiorna ciascun osservatore con il nuovo stato
        }
    }
	
	


}
