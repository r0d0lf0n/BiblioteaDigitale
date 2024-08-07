package controllers.views;

import java.util.ArrayList;
import java.util.List;

import models.bl.CatalogModel;
import models.bl.LoanModel;
import models.bl.UserModel;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;
import utils.Observable;
import utils.Observer;

public class LoansController implements Observable{

	private LoanModel loanModel;
	private UserModel userModel;
	private CatalogModel catalogModel;
	private List<Observer> observers = null;
    private Object obj;

	public LoansController() {
		this.loanModel = new LoanModel();
		this.userModel = new UserModel();
		this.catalogModel = new CatalogModel();
		observers = new ArrayList<Observer>();

	}
	
	public void saveLoan(LoanDAO loan) {
		loanModel.saveLoan(loan);
	}
	
	public List<LoanDAO> getLoans() {
		return loanModel.getAllLoans();
	}
	
	public List<UserDAO> getUsersByRegex(String criteria) {
		return userModel.getUsersByRegex(criteria);
	}
	
	public List<BookDAO> getBooksByRegex(String criteria) {
		return catalogModel.getBooksByRegex(criteria);
	}
	
	public List<LoanDAO> getLoansByRegex(String criteria) {
		return loanModel.getLoansByRegex(criteria);
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