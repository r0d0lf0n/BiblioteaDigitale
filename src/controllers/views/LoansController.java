package controllers.views;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import database.Loan;
import models.bl.LoanModel;
import views.LoanView;

public class LoansController {
	private List<Loan> loans;
	private LoanView loanView;
	private DefaultTableModel model;
	private JTable loanTable;
	private JLabel noLoansBtn;
	private LoanModel loanModel;

	public LoansController(LoanView view, LoanModel loanModel) {
		loanView = view;
		this.loanModel = loanModel;
		view.setVisible(true);
		InitialiazeTable();
	}

	public void InitialiazeTable() {
		loans = loanModel.getAllLoans();
		Object[] columns = { "id", "User", "Book" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(int i = 1; i < loans.size(); i++) {
//			System.out.println(loans.get(i)[0]);
				model.addRow(new Object[] {i, loans.get(i).getUser_id(), loans.get(i).getBook_id()});
		}

		loanTable = loanView.getLoanTable();
		noLoansBtn = loanView.getNoLoanBtn();
		if (loans.size() > 0) {
			noLoansBtn.setVisible(false);
		} else {
			noLoansBtn.setVisible(true);
		}
		loanTable.setModel(model);
		loanView.setLoanTable(loanTable);
	}
}