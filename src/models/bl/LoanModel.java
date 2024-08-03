package models.bl;

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
}