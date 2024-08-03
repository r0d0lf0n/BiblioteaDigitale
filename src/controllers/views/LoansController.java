package controllers.views;

import java.util.ArrayList;
import java.util.List;

import models.bl.LoanModel;
import models.db.LoanDAO;
import utils.Observable;
import utils.Observer;

public class LoansController implements Observable{
	//private List<LoanDAO> loans;
//	private LoanView loanView;
//	private DefaultTableModel model;
//	private JTable loanTable;
//	private JLabel noLoansBtn;
	private LoanModel loanModel;
//	private CustomDialog dialog;
	private List<Observer> observers = null;
    private Object obj;

	public LoansController() {
//		loanView = view;
		this.loanModel = new LoanModel();
		observers = new ArrayList<Observer>();

//		getLoans();
//		getComponents();
//		dialog = new CustomDialog();
//		observers = new ArrayList<Observer>();
//
//		
//		if (loans.size() > 0) {
//			configView();
//		} else {
//			showNoLoansLabel(true);
//			JDialog d = dialog.showDialog(loanView, "Warning!", "There are no loand yet!", "New Loan", "Cancel");
//			JButton btnOne = dialog.getButtonOne();
//			JButton btnTwo = dialog.getButtonTwo();
//			
//			btnOne.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					System.out.println("New loan!");
//					d.setVisible(false);
//					NewLoanView newLoanView = new NewLoanView();
//					LoanModel loanModel = new LoanModel();
//					NewLoanController newLoanController = new NewLoanController(newLoanView, loanModel);
//				}
//			});
//		    
//			btnTwo.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					System.out.println("Closing dialog!");
//					d.setVisible(false);
//				}
//			});
//
//			
//			d.setVisible(true);
//		}
	}
	
//	private void configView() {
//		showNoLoansLabel(false);
//		showView();
//		InitialiazeTable();
//	}
//	
//	private void showView() {
//		loanView.setVisible(true);
//	}
//	
//	private void getComponents() {
//		loanTable = loanView.getLoanTable();
//		noLoansBtn = loanView.getNoLoanBtn();
//	}
//	
	public List<LoanDAO> getLoans() {
		return loanModel.getAllLoans();
	}
//
//	private void showNoLoansLabel(boolean flag) {
//		if (flag) {
//			noLoansBtn.setVisible(true);
//		} else {
//			noLoansBtn.setVisible(false);
//		}
//	}
	



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