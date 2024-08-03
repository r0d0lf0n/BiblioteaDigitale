/**
 * 
 */
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
import com.j256.ormlite.support.ConnectionSource;
import com.opencsv.CSVReader;

import controllers.bl.DatabaseConfig;
import controllers.bl.GestoreCatalogo;
import controllers.bl.GestoreUtenti;
import models.db.BookDAO;
import models.users.Amministratore;
import models.users.Roles;
import models.users.Utente;
import models.users.UtenteEsterno;
import models.users.UtenteRegistrato;
import views.Landing.LandingPageView;
import views.Loan.LoanView;

/**
 * 
 */
public class Orchestrator {

	LandingPageView landingPage = null;
	LoanView loansPage = null;

	ConnectionSource connectionSource;
	List<BookDAO> books = null;


	public Orchestrator() {

		landingPage = new LandingPageView();
	}

	public void startApp() {

		landingPage.setVisible(true);
		loansPage = new LoanView(landingPage.getLandingController());
		

	}

	public void startUser(Roles role, List<String> user) {

		if (role.equals(Roles.ADMIN)) {
			Utente admin = GestoreUtenti.getInstance()
					.creaUtente(new Amministratore(user.get(0), user.get(1), user.get(2)));
			System.out.println("ADMIN " + admin.getNome() + " " + admin.getCognome() + ", " + admin.getCodiceFiscale()
					+ ". TESSERA NUM: " + ((Amministratore) admin).getIdTessera());
		} else if (role.equals(Roles.REGULAR_USER)) {
			Utente regUser1 = GestoreUtenti.getInstance()
					.creaUtente(new UtenteRegistrato(user.get(0), user.get(1), user.get(2)));
			System.out.println("UTENTE REGISTRATO " + regUser1.getNome() + " " + regUser1.getCognome() + ", "
					+ regUser1.getCodiceFiscale() + ". TESSERA NUM: " + regUser1.getIdTessera());
		} else if (role.equals(Roles.EXTERNAL_USER)) {
			Utente user1 = GestoreUtenti.getInstance().creaUtente(new UtenteEsterno());
			System.out.println("USER " + user1.getNome() + " " + user1.getCognome() + ", " + user1.getCodiceFiscale()
					+ ". TESSERA NUM: " + user1.getIdTessera());
		}

	}

	public void startDB() {
		
		configDatabase();
		setDataBaseForFirstTime();
	}

	private void configDatabase() {
		connectionSource = DatabaseConfig.getInstance().getdbConnection();
		System.out.println(connectionSource);
	}

	private void setDataBaseForFirstTime() {
		Dao<BookDAO, String> bookDao = GestoreCatalogo.getInstance().getBookDao();
		try {
			books = bookDao.queryForAll();
			if (books.size() == 0) {
				createBasicBookCatalog(bookDao);
			} else {
				System.out.println("Basic book catalog already loaded!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createBasicBookCatalog(Dao<BookDAO, String> bookDao) {
		System.out.println("Inserting book for the first time....");
		List<String[]> book_catalog = new ArrayList<String[]>();
		try {
			book_catalog = readBookCatalog();
			for (int i = 1; i < 1000; i++) {
				// create an instance of Account
				BookDAO book = new BookDAO();
				book.setTitle(book_catalog.get(i)[1]);
				book.setAuthor(book_catalog.get(i)[2]);
				book.setYear(book_catalog.get(i)[3]);
				book.setDescription(
						book_catalog.get(i)[1] + " - " + book_catalog.get(i)[2] + " - " + book_catalog.get(i)[3]);
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
		//	System.out.println(filePath);
		try (Reader reader = Files.newBufferedReader(filePath)) {
			try (CSVReader csvReader = new CSVReader(reader)) {
				return csvReader.readAll();
			}
		}
	}

}
