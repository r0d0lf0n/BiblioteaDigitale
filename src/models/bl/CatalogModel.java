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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}