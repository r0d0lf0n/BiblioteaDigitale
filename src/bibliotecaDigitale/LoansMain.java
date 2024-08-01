package bibliotecaDigitale;

import java.util.List;
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

	public void ShowView() {
		loanModel = new LoanModel();
		List<Loan> loans = null;
		loans = loanModel.getAllLoans();
		
	    loanView = new LoanView();
	    loanView.setTitle("Loans");
		LoansController loansController = new LoansController(loanView, loans);
	}
	
	public LoanView getView() {
		return loanView;
	}
}
