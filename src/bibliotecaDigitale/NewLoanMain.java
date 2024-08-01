package bibliotecaDigitale;

import com.j256.ormlite.dao.Dao;
import controllers.views.LoansController;
import database.Loan;
import models.bl.LoanModel;
import views.LoanView;
import views.NewLoanView;

public class NewLoanMain {
	NewLoanView newLoanView;
	Dao<Loan, String> loanDao = null;
	LoanModel loanModel;

	public NewLoanMain() {
		ShowView();
	}

	private void ShowView() {
		loanModel = new LoanModel();
		newLoanView = new LoanView();
		newLoanView.setTitle("New Loan");
		NewLoanController newLoanController = new NewLoanController(newLoanView, loanModel);
	}
	
	public LoanView getView() {
		return newLoanView;
	}
}
