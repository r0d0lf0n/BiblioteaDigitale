package bibliotecaDigitale;

import com.j256.ormlite.dao.Dao;
import controllers.views.LoansController;
import database.Loan;
import models.bl.LoanModel;
import views.LoanView;

public class LoansMain {
	LoanView loanView;
	Dao<Loan, String> loanDao = null;
	LoanModel loanModel;

	public LoansMain() {
		ShowView();
	}

	private void ShowView() {
		loanModel = new LoanModel();
	    loanView = new LoanView();
	    loanView.setTitle("Loans");
		LoansController loansController = new LoansController(loanView, loanModel);
	}
	
	public LoanView getView() {
		return loanView;
	}
}
