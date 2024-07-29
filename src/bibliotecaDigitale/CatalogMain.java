package bibliotecaDigitale;

import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.opencsv.CSVReader;
import controllers.views.CatalogController;
import database.Book;
import database.DatabaseConfig;
import database.User;
import views.CatalogView;

public class CatalogMain {
	DatabaseConfig config;
	ConnectionSource connectionSource;
	CatalogView catalogView;
	Dao<Book, String> bookDao = null;

	public CatalogMain() {
		ShowView();
	}

	public void ShowView() {
		List<Book> book_catalog = null;
		config = DatabaseConfig.getInstance();
		bookDao = config.getBookDao();
		try {
			book_catalog = bookDao.queryForAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    catalogView = new CatalogView();
	    catalogView.setTitle("Books Catalog");
		CatalogController catalogController = new CatalogController(catalogView, book_catalog);
	}
	
	public CatalogView getView() {
		return catalogView;
	}
}
