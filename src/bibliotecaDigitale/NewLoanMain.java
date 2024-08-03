package bibliotecaDigitale;

import com.j256.ormlite.dao.Dao;

import controllers.views.NewLoanController;
import models.bl.LoanModel;
import models.db.LoanDAO;
import views.Loan.NewLoanView;

public class NewLoanMain {
	NewLoanView newLoanView;
	Dao<LoanDAO, String> loanDao = null;
	LoanModel loanModel;

	public NewLoanMain() {
		ShowView();
	}

	private void ShowView() {
		loanModel = new LoanModel();
		newLoanView = new NewLoanView();
		newLoanView.setTitle("New Loan");
		NewLoanController newLoanController = new NewLoanController(newLoanView, loanModel);
	}
	
	public NewLoanView getView() {
		return newLoanView;
	}
}
