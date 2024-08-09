package controllers.views;

import java.util.ArrayList;
import java.util.Date;
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
	
	public UserDAO getUserById(int id) {
		return userModel.getUserById(id);
	}
	

	public BookDAO getBookById(int id) {
		return catalogModel.getBookById(id);
	}
	
	public int updateLoan(int id, Date startDate, Date endDate) {
		return loanModel.updateLoanById(id,startDate,endDate);
	}
	
	public LoanDAO getLoanById(int id) {
		return loanModel.getLoanById(id);
	}
	
	public void saveLoan(LoanDAO loan) {
		loanModel.saveLoan(loan);
		setChanged("CLOSE_NEW_LOAN", null);

	}
	
	public void closeNewLoan() {
		setChanged("CLOSE_NEW_LOAN", null);

	}
	
	public List<LoanDAO> getLoans() {
		return loanModel.getAllLoans();
	}
	
	public UserDAO getUserByTesseraId(int id) {
		return userModel.getUserByTesseraId(id);
	}
	
	public List<UserDAO> getUsers() {
		return userModel.getAllUsers();
	}
	
	public List<LoanDAO> getLoansByUserId(int id) {
		return loanModel.getLoansByUserId(id);
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
    
    public void closeLoansDetailPanel() {
		setChanged("CLOSE_LOANS_DETAIL", null);	
		setChanged("REFRESH_LOANS_DETAIL", null);	
	}
    
    public void openNewLoan() {
    	setChanged("OPEN_NEW_LOAN", null);
    }
}