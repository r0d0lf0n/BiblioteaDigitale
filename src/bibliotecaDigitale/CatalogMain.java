package bibliotecaDigitale;

import com.j256.ormlite.dao.Dao;
import controllers.views.CatalogController;
import database.Book;
import models.bl.CatalogModel;
import views.CatalogView;

public class CatalogMain {
	CatalogView catalogView;
	Dao<Book, String> bookDao = null;
	CatalogModel catalogModel;

	public CatalogMain() {
		ShowView();
	}

	public void ShowView() {
		catalogModel = new CatalogModel();
	    catalogView = new CatalogView();
	    catalogView.setTitle("Books Catalog");
		CatalogController catalogController = new CatalogController(catalogView, catalogModel);
	}
	
	public CatalogView getView() {
		return catalogView;
	}
}
