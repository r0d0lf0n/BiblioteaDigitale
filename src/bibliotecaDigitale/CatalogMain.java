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
import database.DatabaseConfig;
import database.User;
import views.CatalogView;

public class CatalogMain {
	
	DatabaseConfig config;
	ConnectionSource connectionSource;

	public CatalogMain() {
		ShowView();
		config = new DatabaseConfig();
		connectionSource = config.getdbConnection();
		System.out.println(connectionSource);
	}

	public void ShowView() {
		List<String[]> book_catalog = new ArrayList<String[]>();
		try {
			book_catalog = readBookCatalog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CatalogView catalogView = new CatalogView();
		CatalogController catalogController = new CatalogController(catalogView, book_catalog);
	}
	
	public List<String[]> readBookCatalog() throws Exception {
		String filepath = "/assets/book_catalog.csv";
		URI resourcePath = this.getClass().getResource(filepath).toURI();
	    Path path = Paths.get(resourcePath);
	    return readAllLines(path);
	}
		
	public List<String[]> readAllLines(Path filePath) throws Exception {
//		System.out.println(filePath);
	    try (Reader reader = Files.newBufferedReader(filePath)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	            return csvReader.readAll();
	        }
	    }
	}
}
