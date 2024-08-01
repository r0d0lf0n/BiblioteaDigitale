package models.bl;

import java.util.List;
import com.j256.ormlite.dao.Dao;
import database.DatabaseConfig;
import database.Loan;

public class LoanModel {
	DatabaseConfig config;
	Dao<Loan, String> loanDao = null;

	public LoanModel() {
		config = DatabaseConfig.getInstance();
		loanDao = config.getLoanDao();
	}
	
	public List<Loan> getAllLoans() {
		List<Loan> loans = null;
		try {
			loans = loanDao.queryForAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loans;
	}
}