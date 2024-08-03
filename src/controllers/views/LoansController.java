package controllers.views;

import java.util.ArrayList;
import java.util.List;

import models.bl.LoanModel;
import models.db.LoanDAO;
import utils.Observable;
import utils.Observer;

public class LoansController implements Observable{

	private LoanModel loanModel;
	private List<Observer> observers = null;
    private Object obj;

	public LoansController() {
		this.loanModel = new LoanModel();
		observers = new ArrayList<Observer>();

	}
	
	public List<LoanDAO> getLoans() {
		return loanModel.getAllLoans();
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
}