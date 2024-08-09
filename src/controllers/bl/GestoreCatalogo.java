/**
 * 
 */
package controllers.bl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.opencsv.CSVReader;

import models.db.BookDAO;

/**
 * 
 */
public class GestoreCatalogo {
	
	private static GestoreCatalogo _instance = null;
	Dao<BookDAO, String> bookDao = null;
	private boolean loadingInProgress = false;
	
	private GestoreCatalogo() {
		
	}
	
	public static GestoreCatalogo getInstance() {
		if(_instance == null)
			_instance = new GestoreCatalogo();
		return _instance;
	}

	/**
	 * @return the bookDao
	 */
	public Dao<BookDAO, String> getBookDao() {
		return bookDao;
	}

	/**
	 * @param bookDao the bookDao to set
	 */
	public void setBookDao(Dao<BookDAO, String> bookDao) {
		this.bookDao = bookDao;
	}

	public boolean populateCatalog(String filepath) {
	List<String[]> book_catalog = new ArrayList<String[]>();
	Instant currentTimestamp = null;
	Instant previousTimestamp = null;
		try {
			loadingInProgress=true;
			book_catalog = GestoreCatalogo.getInstance().readBookCatalog(filepath);
			for (int i = 1; i < book_catalog.size(); i++) {
				//System.out.println(book_catalog.get(i)[0]);
				BookDAO book = new BookDAO();
				book.setTitle(book_catalog.get(i)[1]);
				book.setAuthor(book_catalog.get(i)[2]);
				book.setYear(book_catalog.get(i)[3]);
				book.setDescription(
						book_catalog.get(i)[1] + " - " + book_catalog.get(i)[2] + " - " + book_catalog.get(i)[3]);
				book.setIsbn(book_catalog.get(i)[0]);
				book.setEditor(book_catalog.get(i)[4]);
				bookDao.createOrUpdate(book);
				if(i%10000==0) {
					System.out.println("Loaded "+i+" books into DB");
					 currentTimestamp = Instant.now();
		
			        if (previousTimestamp != null) {
			            Duration delta = Duration.between(previousTimestamp, currentTimestamp);
			            System.out.println("Delta time: " + delta.toMillis() + " milliseconds");
			        } 
			        previousTimestamp = currentTimestamp;
				}

			}
			loadingInProgress = false;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadingInProgress =false;
		return false;
	}
	
	public List<String[]> readBookCatalog(String filepath) {

		InputStream inputStream = null;
		BufferedReader reader = null;
		try {

			if (new File(filepath).isAbsolute()) {
				// Percorso assoluto
				inputStream = new FileInputStream(filepath);
			} else {
				// Percorso relativo al classpath
				inputStream = this.getClass().getResourceAsStream(filepath);
				if (inputStream == null) {
					throw new RuntimeException("File not found: " + filepath);
				}
			}
			reader = new BufferedReader(new InputStreamReader(inputStream));

			try (CSVReader csvReader = new CSVReader(reader)) {
				return csvReader.readAll();
			}
			finally {
				// Chiude il reader e l'input stream
				if (reader != null)
					reader.close();
				if (inputStream != null)
					inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isLoadingInProgress() {
		return loadingInProgress;
	}

	public void removeBook(BookDAO book) {
		try {
			bookDao.delete(book);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addNewBook(BookDAO newBook) {
		try {
			bookDao.create(newBook);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
