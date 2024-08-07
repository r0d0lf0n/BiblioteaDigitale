/**
 * 
 */
package bibliotecaDigitale;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.opencsv.CSVReader;

import controllers.bl.DatabaseConfig;
import controllers.bl.GestoreCatalogo;
import controllers.bl.GestorePrestiti;
import controllers.bl.GestoreUtenti;
import models.db.BookDAO;
import models.db.LoanDAO;
import models.db.UserDAO;
import models.users.Amministratore;
import models.users.Roles;
import models.users.Utente;
import models.users.UtenteEsterno;
import models.users.UtenteRegistrato;
import views.Catalog.CatalogView;
import views.Landing.LandingPageView;
import views.Loan.LoanView;
import views.users.ExternalUserView;
import views.users.RegisteredUserView;
import views.users.UserAdminView;

/**
 * 
 */
public class Orchestrator {

	private LandingPageView landingPage = null;
	private LoanView loansPage = null;
	private CatalogView catalogPage = null;
	//private UserAdminView adminView = null;

	private ConnectionSource connectionSource;
	private List<BookDAO> books = null;
	private Utente admin = null;
	private List<Utente> registeredUsers = null;
	private List<Utente> unregisteredUsers = null;
	private List<JFrame> viewList = null;


	public Orchestrator() {
		landingPage = new LandingPageView();
		registeredUsers = new ArrayList<Utente>();
		unregisteredUsers = new ArrayList<Utente>();
		viewList = new ArrayList<JFrame>();
	}

	public void startApp() {

		landingPage.setVisible(true);
		loansPage = new LoanView(landingPage.getLandingController());
		catalogPage = new CatalogView(landingPage.getLandingController());
		
		disposeUserUIPositions();

	}

	private void disposeUserUIPositions() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Calcola il punto centrale dello schermo
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        int panelWidth = 400;
        int panelHeight = 300;
        // Calcola le posizioni di ciascun pannello
        int[][] positions = {
            {centerX - panelWidth, centerY - panelHeight}, // Top-left
            {centerX, centerY - panelHeight},             // Top-center
            {centerX + panelWidth, centerY - panelHeight}, // Top-right
            {centerX - panelWidth, centerY},               // Middle-left
            {centerX, centerY},                            // Middle-center
            {centerX + panelWidth, centerY}                // Middle-right
        };

        // Creazione e posizionamento dei pannelli
        for (int i = 0; i < 6; i++) {
            JFrame frame = viewList.get(i);
            frame.setSize(panelWidth, panelHeight);
            frame.setLocation(positions[i][0], positions[i][1]);
        }
	}

	public void startUser(Roles role, List<String> user) {

		if (role.equals(Roles.ADMIN)) {
			admin = GestoreUtenti.getInstance()
					.creaUtente(new Amministratore(user.get(0), user.get(1), user.get(2)));
			viewList.add(new UserAdminView(landingPage.getLandingController(), admin.getNome(), admin.getCognome(), admin.getCodiceFiscale()));

			System.out.println("ADMIN " + admin.getNome() + " " + admin.getCognome() + ", " + admin.getCodiceFiscale()
					+ ". TESSERA NUM: " + ((Amministratore) admin).getIdTessera());
			
		} else if (role.equals(Roles.REGULAR_USER)) {
			Utente regUser1 = GestoreUtenti.getInstance()
					.creaUtente(new UtenteRegistrato(user.get(0), user.get(1), user.get(2)));
			viewList.add( new RegisteredUserView(landingPage.getLandingController(), regUser1));	
			registeredUsers.add(regUser1);
			
			System.out.println("UTENTE REGISTRATO " + regUser1.getNome() + " " + regUser1.getCognome() + ", "
					+ regUser1.getCodiceFiscale() + ". TESSERA NUM: " + regUser1.getIdTessera());
			
		} else if (role.equals(Roles.EXTERNAL_USER)) {
			Utente user1 = GestoreUtenti.getInstance().creaUtente(new UtenteEsterno());
			viewList.add(new ExternalUserView(landingPage.getLandingController(), user1));
			unregisteredUsers.add(user1);

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
		Dao<LoanDAO, String> loanDao = GestorePrestiti.getInstance().getLoanDao();
		createFakeLoans(loanDao);
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
	
	//TODO ELIMINARE
	private void createFakeLoans(Dao<LoanDAO, String> loanDao) {
		System.out.println("Inserting fake loans....");
		List<String[]> loans_list = new ArrayList<String[]>();
		try {
			loans_list = readBookCatalog();
			for (int i = 1; i < 10; i++) {
				// create an instance of Account
				LoanDAO loan = new LoanDAO();
				UserDAO user = new UserDAO();
				user.setId(i);
				user.setName("Nome"+i);
				loan.setUser_id(user);
				BookDAO book = new BookDAO();
				book.setId(i);
				book.setTitle("Titolo"+i);
				loan.setUser_id(user);
				loan.setBook_id(book);
				loanDao.create(loan);
				System.out.println("Saving loan: ");
				System.out.println(loans_list.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
