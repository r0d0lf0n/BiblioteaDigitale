package database;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public final class DatabaseConfig {
    private static DatabaseConfig INSTANCE;
    private String info = "Initial info class";
    
	String dbfilepath  = "jdbc:sqlite:biblio.db";
	ConnectionSource connectionSource;
	Dao<User, String> userDao;
	Dao<Loan, String> loanDao;
	Dao<Book, String> bookDao;
	
	private DatabaseConfig() {
	     try {
		  	System.out.println("Creating DB connection...");
	        connectionSource = new JdbcConnectionSource(dbfilepath);
	        TableUtils.createTableIfNotExists(connectionSource, Book.class);
	        TableUtils.createTableIfNotExists(connectionSource, User.class);
	        TableUtils.createTableIfNotExists(connectionSource, Loan.class);
	        
			bookDao = DaoManager.createDao(connectionSource, Book.class);
			userDao = DaoManager.createDao(connectionSource, User.class);
			loanDao = DaoManager.createDao(connectionSource, Loan.class);
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
        if(INSTANCE == null) {
            INSTANCE = new DatabaseConfig();
        }
        
        return INSTANCE;
    }
	
	public ConnectionSource getdbConnection() {
		return connectionSource;
	}
	
	public Dao<User, String> getUserDao() {
		return userDao;
	}
	
	public Dao<Loan, String> getLoanDao() {
		return loanDao;
	}
	
	public Dao<Book, String> getBookDao() {
		return bookDao;
	}
}