package bibliotecaDigitale;

import com.j256.ormlite.dao.Dao;
import controllers.views.CatalogController;
import database.Book;
import models.bl.BookModel;
import views.CatalogView;

public class CatalogMain {
	CatalogView catalogView;
	Dao<Book, String> bookDao = null;
	BookModel bookModel;

	public CatalogMain() {
		ShowView();
	}

	public void ShowView() {
		bookModel = new BookModel();
	    catalogView = new CatalogView();
	    catalogView.setTitle("Books Catalog");
		CatalogController catalogController = new CatalogController(catalogView, bookModel);
	}
	
	public CatalogView getView() {
		return catalogView;
	}
}
