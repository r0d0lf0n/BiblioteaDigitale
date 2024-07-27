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
//	DatabaseConfig config;
	ConnectionSource connectionSource;
	CatalogView catalogView;

	public CatalogMain() {
		ShowView();
//		config = new DatabaseConfig();
//		connectionSource = config.getdbConnection();
//		System.out.println(connectionSource);
		
		

	 	
//        // create an instance of Account
//		Dao<User, String> userDao = config.getUserDao();
//        User user = new User();
//        user.setName("Jim");
//        user.setSurname("Coakley");
//        user.setPhone(null);
//        user.setRole(0);
//        user.setAddress("avenue 45");
        

//        // persist the account object to the database
//        try {
////			userDao.create(user);
//	        // retrieve the account from the database by its id field (name)
////	        User userTwo = userDao.queryForId(null)
////	        System.out.println("User: " + userTwo.getName());
//	        // close the connection source
////	        connectionSource.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void ShowView() {
		List<String[]> book_catalog = new ArrayList<String[]>();
		try {
			book_catalog = readBookCatalog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    catalogView = new CatalogView();
	    catalogView.setTitle("Books Catalog");
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
	
	public CatalogView getView() {
		return catalogView;
	}
}
