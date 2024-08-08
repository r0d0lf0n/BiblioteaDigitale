package models.bl;

import java.sql.SQLException;
import java.util.List;

import controllers.bl.GestoreCatalogo;
import models.db.BookDAO;

public class CatalogModel {

	public CatalogModel() {
		//empty
	}
	
	public List<BookDAO> getAllBooks() {
		try {
			return GestoreCatalogo.getInstance().getBookDao().queryForAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<BookDAO> getBooksByRegex(String criteria) {
		System.out.println(criteria);
		List<BookDAO> list = null;
		try {

			list = GestoreCatalogo.getInstance().getBookDao().queryBuilder()
			  .where()
			  .like("title", "%"+criteria+"%")
			  .query();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public BookDAO getBookById(int id) {
		BookDAO book = null;
		try {
			List<BookDAO> list = GestoreCatalogo.getInstance().getBookDao().queryBuilder()
					  .where()
					  .eq("id", id)
					  .query();
			if (list.size()>0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}
	
	public void saveBook(BookDAO book) {
		try {
//			System.out.println(book);
			GestoreCatalogo.getInstance().getBookDao().update(book);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}