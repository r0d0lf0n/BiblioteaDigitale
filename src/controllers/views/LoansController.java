package controllers.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import bibliotecaDigitale.CatalogMain;
import database.Loan;
import models.bl.LoanModel;
import utils.CustomDialog;
import views.Loan.NewLoanView;
import views.Loan.LoanView;

public class LoansController {
	private List<Loan> loans;
	private LoanView loanView;
	private DefaultTableModel model;
	private JTable loanTable;
	private JLabel noLoansBtn;
	private LoanModel loanModel;
	private CustomDialog dialog;

	public LoansController(LoanView view, LoanModel loanModel) {
		loanView = view;
		this.loanModel = loanModel;
		getLoans();
		getComponents();
		dialog = new CustomDialog();
		
		if (loans.size() > 0) {
			configView();
		} else {
			showNoLoansLabel(true);
			JDialog d = dialog.showDialog(loanView, "Warning!", "There are no loand yet!", "New Loan", "Cancel");
			JButton btnOne = dialog.getButtonOne();
			JButton btnTwo = dialog.getButtonTwo();
			
			btnOne.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("New loan!");
					d.setVisible(false);
					NewLoanView newLoanView = new NewLoanView();
					LoanModel loanModel = new LoanModel();
					NewLoanController newLoanController = new NewLoanController(newLoanView, loanModel);
				}
			});
		    
			btnTwo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Closing dialog!");
					d.setVisible(false);
				}
			});

			
			d.setVisible(true);
		}
	}
	
	private void configView() {
		showNoLoansLabel(false);
		showView();
		InitialiazeTable();
	}
	
	private void showView() {
		loanView.setVisible(true);
	}
	
	private void getComponents() {
		loanTable = loanView.getLoanTable();
		noLoansBtn = loanView.getNoLoanBtn();
	}
	
	private void getLoans() {
		loans = loanModel.getAllLoans();
	}

	private void showNoLoansLabel(boolean flag) {
		if (flag) {
			noLoansBtn.setVisible(true);
		} else {
			noLoansBtn.setVisible(false);
		}
	}
	
	private void InitialiazeTable() {
		Object[] columns = { "id", "User", "Book" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(int i = 1; i < loans.size(); i++) {
//			System.out.println(loans.get(i)[0]);
				model.addRow(new Object[] {i, loans.get(i).getUser_id(), loans.get(i).getBook_id()});
		}

		loanTable.setModel(model);
		loanView.setLoanTable(loanTable);
	}
}