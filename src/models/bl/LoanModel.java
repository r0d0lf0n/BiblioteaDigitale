package models.bl;

import java.sql.SQLException;
import java.util.List;

import controllers.bl.GestorePrestiti;
import models.db.LoanDAO;

public class LoanModel {

	public LoanModel() {
		//emtpy
	}
	
	public List<LoanDAO> getAllLoans() {
		try {
			return GestorePrestiti.getInstance().getLoanDao().queryForAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveLoan(LoanDAO loan) {
		try {
			GestorePrestiti.getInstance().getLoanDao().createIfNotExists(loan);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}