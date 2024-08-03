/**
 * 
 */
package controllers.bl;

import com.j256.ormlite.dao.Dao;

import models.db.BookDAO;

/**
 * 
 */
public class GestoreCatalogo {
	
	private static GestoreCatalogo _instance = null;
	Dao<BookDAO, String> bookDao = null;
	
	private GestoreCatalogo() {
		
	}
	
	public static GestoreCatalogo getInstance() {
		if(_instance == null)
			_instance = new GestoreCatalogo();
		return _instance;
	}

	/**
	 * @return the bookDao
	 */
	public Dao<BookDAO, String> getBookDao() {
		return bookDao;
	}

	/**
	 * @param bookDao the bookDao to set
	 */
	public void setBookDao(Dao<BookDAO, String> bookDao) {
		this.bookDao = bookDao;
	}

}
