package models.bl;

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
			  .selectColumns("title")
			  .where()
			  .like("title", "%"+criteria+"%")
			  .query();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}