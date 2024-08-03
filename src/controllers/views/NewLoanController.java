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
import models.bl.LoanModel;
import models.db.LoanDAO;
import utils.CustomDialog;
import views.Loan.NewLoanView;

public class NewLoanController {
	private List<LoanDAO> loans;
	private NewLoanView newLoanView;
	private DefaultTableModel model;
	private JTable loanTable;
	private JLabel noLoansBtn;
	private LoanModel loanModel;
	private CustomDialog dialog;

	public NewLoanController(NewLoanView view, LoanModel loanModel) {
		newLoanView = view;
		this.loanModel = loanModel;
		showView();
//		getLoans();
//		getComponents();
//		helpers = new Helpers();
//		
//		if (loans.size() > 0) {
//			configView();
//		} else {
//			showNoLoansLabel(true);
//			JDialog d = helpers.showDialog(newLoanView, "Warning!", "There are no loand yet!", "New Loan", "Cancel");
//			d.setVisible(true);
//		}
	}
	
//	private void configView() {
//		showNoLoansLabel(false);
//		showView();
//		InitialiazeTable();
//	}
//	
	private void showView() {
		newLoanView.setVisible(true);
	}
//	
//	private void getComponents() {
//		loanTable = loanView.getLoanTable();
//		noLoansBtn = loanView.getNoLoanBtn();
//	}
//	
//	private void getLoans() {
//		loans = loanModel.getAllLoans();
//	}
//
//	private void showNoLoansLabel(boolean flag) {
//		if (flag) {
//			noLoansBtn.setVisible(true);
//		} else {
//			noLoansBtn.setVisible(false);
//		}
//	}
//	
//	private void InitialiazeTable() {
//		Object[] columns = { "id", "User", "Book" };
//		model = new DefaultTableModel();
//		model.setColumnIdentifiers(columns);
//		
//		for(int i = 1; i < loans.size(); i++) {
////			System.out.println(loans.get(i)[0]);
//				model.addRow(new Object[] {i, loans.get(i).getUser_id(), loans.get(i).getBook_id()});
//		}
//
//		loanTable.setModel(model);
//		loanView.setLoanTable(loanTable);
//	}
}