package bibliotecaDigitale;

import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.opencsv.CSVReader;

import controllers.views.LandingPageController;
import database.Book;
import database.DatabaseConfig;
import views.LandingPageView;

public class LandingPageMain {
	DatabaseConfig config;
	ConnectionSource connectionSource;
	List<Book> books = null;
	Dao<Book, String> bookDao = null;
	
	public LandingPageMain() {
		ShowView();
		setDataBaseForFirstTime();
	}

	private void ShowView() {
		LandingPageView landingPageView = new LandingPageView();
		LandingPageController landingPageController = new LandingPageController(landingPageView);
		
		configDatabase();
	}
	
	private void configDatabase() {
		config = DatabaseConfig.getInstance();
		connectionSource = config.getdbConnection();
		System.out.println(connectionSource);
	}
	
	private void setDataBaseForFirstTime() {
		bookDao = config.getBookDao();
		try {
			books = bookDao.queryForAll();
			if (books.size() == 0) {
				createBasicBookCatalog();
			} else {
				System.out.println("Basic book catalog already loaded!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createBasicBookCatalog() {
		System.out.println("Inserting book for the first time....");
		List<String[]> book_catalog = new ArrayList<String[]>();
		try {
			book_catalog = readBookCatalog();
			for(int i = 1; i < 1000; i++) {
		        // create an instance of Account
		        Book book = new Book();
		        book.setTitle(book_catalog.get(i)[1]);
		        book.setAuthor(book_catalog.get(i)[2]);
		        book.setYear(book_catalog.get(i)[3]);
		        book.setDescription(book_catalog.get(i)[1] + " - " + book_catalog.get(i)[2] + " - " + book_catalog.get(i)[3]);
		        book.setIsbn(book_catalog.get(i)[0]);
				bookDao.create(book);
				System.out.println("Saving book: ");
				System.out.println(book_catalog.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private List<String[]> readBookCatalog() throws Exception {
		String filepath = "/assets/Books.csv";
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
