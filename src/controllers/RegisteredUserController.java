package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Observable;
import models.Observer;

public class RegisteredUserController implements Observable{
	
	private List<Observer> observers = null;
    private Object obj;
	
	public RegisteredUserController() {
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
	

}
