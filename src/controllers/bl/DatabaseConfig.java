package controllers.bl;


import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;


public final class DatabaseConfig {
    private static DatabaseConfig _instance;
    
	String dbfilepath  = "jdbc:sqlite:biblio.db";
	ConnectionSource connectionSource;


	private DatabaseConfig() {
	     try {
		  	System.out.println("Creating DB connection...");
	        connectionSource = new JdbcConnectionSource(dbfilepath);
	        TableUtils.createTableIfNotExists(connectionSource, BookDAO.class);
	        TableUtils.createTableIfNotExists(connectionSource, UserDAO.class);
	        TableUtils.createTableIfNotExists(connectionSource, LoanDAO.class);
	        
			GestoreCatalogo.getInstance().setBookDao(DaoManager.createDao(connectionSource, BookDAO.class));
			GestoreUtenti.getInstance().setUserDao(DaoManager.createDao(connectionSource, UserDAO.class));
			GestorePrestiti.getInstance().setLoanDao(DaoManager.createDao(connectionSource, LoanDAO.class));
			System.out.println("DB connection created!");
	     } catch (Exception e) {
	          e.printStackTrace();
	     } finally {
	          try {
	        	  connectionSource.close();
	          } catch (Exception e) {
	               e.printStackTrace();
	          }
	     }
	}
	
	 public static synchronized DatabaseConfig getInstance() {
        if(_instance == null) {
            _instance = new DatabaseConfig();
        }
        
        return _instance;
    }
	
	public ConnectionSource getdbConnection() {
		return connectionSource;
	}
	
}