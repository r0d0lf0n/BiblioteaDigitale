package bibliotecaDigitale;

import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.opencsv.CSVReader;
import controllers.views.CatalogController;
import controllers.views.LoansController;
import database.Book;
import database.DatabaseConfig;
import database.Loan;
import database.User;
import models.bl.BookModel;
import models.bl.LoanModel;
import views.CatalogView;
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
