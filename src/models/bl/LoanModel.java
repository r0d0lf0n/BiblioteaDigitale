package models.bl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import controllers.bl.GestoreCatalogo;
import controllers.bl.GestorePrestiti;
import controllers.bl.GestoreUtenti;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;

public class LoanModel {
	Dao<LoanDAO, String> loanDao;

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
	
	public int updateLoanById(int id, Date newStartDate, Date newEndDate) {
	    int rowsUpdated = 0;
	    try {
	        loanDao = GestorePrestiti.getInstance().getLoanDao();	        
	        UpdateBuilder<LoanDAO, String> updateBuilder = loanDao.updateBuilder();
	        updateBuilder.updateColumnValue("start_date", newStartDate);
	        updateBuilder.updateColumnValue("end_Date", newEndDate);
	        updateBuilder.where().eq("id", id);
	        rowsUpdated = updateBuilder.update();	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return rowsUpdated;
	}
	
	public LoanDAO getLoanById(int idPrestito) {
		LoanDAO loan = null;
		try {
			loanDao = GestorePrestiti.getInstance().getLoanDao();			
			List<LoanDAO> list = loanDao.queryBuilder()
					  .where()
					  .eq("id", idPrestito)
					  .query();
			if (list.size()>0)
				loan = list.get(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loan;
	}
	
	public void saveLoan(LoanDAO loan) {
		try {
			GestorePrestiti.getInstance().getLoanDao().createIfNotExists(loan);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<LoanDAO> getLoansByUserId(int id) {
		List<LoanDAO> list = null;
		try {
			loanDao = GestorePrestiti.getInstance().getLoanDao();			
			list = loanDao.queryBuilder()
					  .where()
				      .eq("user_id", id)
					  .query();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	

 	
//QueryBuilder<Order, Integer> orderQb = orderDao.queryBuilder();
//orderQb.where().ge("amount", 100.0F);
//QueryBuilder<Account, Integer> accountQb = accountDao.queryBuilder();
//// join with the order query
//List<Account> results = accountQb.join(orderQb).query();

	public List<LoanDAO> getLoansByUser(String criteria) {
		List<UserDAO> userList = null;
		Dao<UserDAO, String> userDao = GestoreUtenti.getInstance().getUserDao();
		QueryBuilder<UserDAO, String> userQB = userDao.queryBuilder();
		
		List<LoanDAO> list = null;
		loanDao = GestorePrestiti.getInstance().getLoanDao();
		QueryBuilder<LoanDAO, String> loanQB = loanDao.queryBuilder();
		
		try {
			userQB
			.where()
			.like("name", "%"+criteria+"%")
			.or()
			.like("surname", "%"+criteria+"%")
			.query();
			
			list = loanQB
					.join(userQB)
					.query();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<LoanDAO> getLoansByTitle(String criteria) {
		Dao<BookDAO, String> bookDao = GestoreCatalogo.getInstance().getBookDao();
		QueryBuilder<BookDAO, String> bookQB = bookDao.queryBuilder();
		
		List<LoanDAO> list = null;
		loanDao = GestorePrestiti.getInstance().getLoanDao();
		QueryBuilder<LoanDAO, String> loanQB = loanDao.queryBuilder();
		
		try {			
			bookQB
			.where()
			.like("title", "%"+criteria+"%")
			.query();
			
			list = loanQB
					.join(bookQB)
					.query();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<LoanDAO> getLoansByTesseraId(int id) {
		List<LoanDAO> list = null;
		try {
			loanDao = GestorePrestiti.getInstance().getLoanDao();			
			list = loanDao.queryBuilder()
					  .where()
				      .eq("user_id", id)
					  .query();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}