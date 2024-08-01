package models.bl;

import java.util.List;
import com.j256.ormlite.dao.Dao;
import database.Book;
import database.DatabaseConfig;

public class BookModel {
	DatabaseConfig config;
	Dao<Book, String> bookDao = null;

	public BookModel() {
		config = DatabaseConfig.getInstance();
		bookDao = config.getBookDao();
	}
	
	public List<Book> getAllBooks() {
		List<Book> book_catalog = null;
		try {
			book_catalog = bookDao.queryForAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book_catalog;
	}
}