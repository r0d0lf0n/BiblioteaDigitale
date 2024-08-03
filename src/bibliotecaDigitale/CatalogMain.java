package bibliotecaDigitale;

import com.j256.ormlite.dao.Dao;

import controllers.views.CatalogController;
import models.bl.CatalogModel;
import models.db.BookDAO;
import views.Catalog.CatalogView;

public class CatalogMain {
	CatalogView catalogView;
	Dao<BookDAO, String> bookDao = null;
	CatalogModel catalogModel;

	public CatalogMain() {
		ShowView();
	}

	private void ShowView() {
		catalogModel = new CatalogModel();
	    catalogView = new CatalogView();
	    catalogView.setTitle("Books Catalog");
		CatalogController catalogController = new CatalogController(catalogView, catalogModel);
	}
	
	public CatalogView getView() {
		return catalogView;
	}
}
