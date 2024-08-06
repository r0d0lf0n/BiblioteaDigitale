package models.bl;

import java.util.List;
import com.j256.ormlite.dao.Dao;
import controllers.bl.GestoreCatalogo;
import models.db.BookDAO;

public class CatalogModel {
	Dao<BookDAO, String> bookDao;

	public CatalogModel() {
		//empty
	}
	
	public List<BookDAO> getAllBooks() {
		try {
			return GestoreCatalogo.getInstance().getBookDao().queryForAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<BookDAO> getBooksByRegex(String criteria) {
		System.out.println(criteria);
		List<BookDAO> list = null;
		try {
			bookDao = GestoreCatalogo.getInstance().getBookDao();	
			list = bookDao.queryBuilder()
			  .selectColumns("title")
			  .where()
			  .like("title", "%"+criteria+"%")
			  .query();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}