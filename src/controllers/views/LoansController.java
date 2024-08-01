package controllers.views;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import database.Book;
import database.Loan;
import views.LoanView;

public class LoansController {
	private List<Loan> loans;
	private LoanView loanView;
	private DefaultTableModel model;
	private JTable loanTable;

	public LoansController(LoanView view, List<Loan> loans) {
		loanView = view;
		this.loans = loans;
		view.setVisible(true);
		InitialiazeTable();
	}

	public void InitialiazeTable() {
		Object[] columns = { "id", "User", "Book" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		for(int i = 1; i < loans.size(); i++) {
//			System.out.println(loans.get(i)[0]);
				model.addRow(new Object[] {i, loans.get(i).getUser_id(), loans.get(i).getBook_id()});
		}

		loanTable = loanView.getLoanTable();
		loanTable.setModel(model);
		loanView.setLoanTable(loanTable);
	}
}
