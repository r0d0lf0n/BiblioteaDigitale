/**
 * 
 */
package bibliotecaDigitale;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.j256.ormlite.support.ConnectionSource;

import controllers.bl.DatabaseConfig;
import controllers.bl.GestoreCatalogo;
import controllers.bl.GestoreUtenti;
import models.db.BookDAO;
import models.db.UserDAO;
import models.users.Amministratore;
import models.users.Roles;
import models.users.Utente;
import models.users.UtenteEsterno;
import models.users.UtenteRegistrato;
import views.Catalog.CatalogView;
import views.Catalog.CatalogViewLite;
import views.Landing.LandingPageView;
import views.Loan.LoanView;
import views.users.ExternalUserView;
import views.users.RegisteredUserView;

/**
 * 
 */
public class Orchestrator {

	private LandingPageView landingPage = null;
	private LoanView loansPage = null;
	private CatalogView catalogPage = null;
	private CatalogViewLite catalogPageLite = null;
	//private UserAdminView adminView = null;
	private int maxNumberOfRegUsers = 3;
	private int maxNumberOfExtUsers = 3;
	private int currentNumberOfRegUsers = 0;
	private int currentNumberOfExtUsers = 0;

	private ConnectionSource connectionSource;
	private List<BookDAO> books = null;
	private Utente admin = null;
	private List<Utente> registeredUsers = null;
	private List<Utente> unregisteredUsers = null;
	private List<JFrame> viewList = null;
	private List<UserDAO> users = null;


	public Orchestrator() {
		landingPage = new LandingPageView();
		registeredUsers = new ArrayList<Utente>();
		unregisteredUsers = new ArrayList<Utente>();
		viewList = new ArrayList<JFrame>();
	}

	public void startApp() {

		landingPage.setVisible(true);
		loansPage = new LoanView(landingPage.getLandingController(), -1);
		catalogPage = new CatalogView(landingPage.getLandingController());
		catalogPageLite = new CatalogViewLite(landingPage.getLandingController(), null);
		catalogPage.addUser(admin);
		
		disposeUserUIPositions();

	}

	private void disposeUserUIPositions() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Calcola il punto centrale dello schermo
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        int panelWidth = 500;
        int panelHeight = 400;
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

	public void createUsers(Roles role, List<String> user) {

		if (role.equals(Roles.ADMIN)) {
			admin = GestoreUtenti.getInstance()
					.creaUtente(new Amministratore(user.get(0), user.get(1), user.get(2)), true);
				//viewList.add(new UserAdminView(landingPage.getLandingController(), admin.getNome(), admin.getCognome(), admin.getCodiceFiscale()));

			System.out.println("ADMIN " + admin.getNome() + " " + admin.getCognome() + ", " + admin.getCodiceFiscale()
					+ ". TESSERA NUM: " + ((Amministratore) admin).getIdTessera());
			
		} else if (role.equals(Roles.REGULAR_USER)) {
			Utente regUser1 = GestoreUtenti.getInstance()
					.creaUtente(new UtenteRegistrato(user.get(0), user.get(1), user.get(2)), true);
			if(currentNumberOfRegUsers < maxNumberOfRegUsers) {
				viewList.add( new RegisteredUserView(landingPage.getLandingController(), regUser1));
				currentNumberOfRegUsers++;
			}
			registeredUsers.add(regUser1);
			
			System.out.println("UTENTE REGISTRATO " + regUser1.getNome() + " " + regUser1.getCognome() + ", "
					+ regUser1.getCodiceFiscale() + ". TESSERA NUM: " + regUser1.getIdTessera());
			
		} else if (role.equals(Roles.EXTERNAL_USER)) {
			Utente user1 = GestoreUtenti.getInstance().creaUtente(new UtenteEsterno(), false);
			if(currentNumberOfExtUsers < maxNumberOfExtUsers) {
				viewList.add(new ExternalUserView(landingPage.getLandingController(), user1));
				currentNumberOfExtUsers++;
			}
			unregisteredUsers.add(user1);

			System.out.println("USER " + user1.getNome() + " " + user1.getCognome() + ", " + user1.getCodiceFiscale()
					+ ". TESSERA NUM: " + user1.getIdTessera());
		}
	}	

	
	public void loadUsers() {
		for (int i = 0; i < users.size(); i++) {
			int currentRole = users.get(i).getRole();
			if(currentRole == Roles.EXTERNAL_USER.ordinal()) {
				Utente user1 = GestoreUtenti.getInstance().creaUtente(new UtenteEsterno(), false);
				if(currentNumberOfExtUsers < maxNumberOfExtUsers) {
					viewList.add(new ExternalUserView(landingPage.getLandingController(), user1));
					currentNumberOfExtUsers++;
				}
				unregisteredUsers.add(user1);
			}
			else if(currentRole == Roles.REGULAR_USER.ordinal()) {
				Utente regUser1 = GestoreUtenti.getInstance()
						.creaUtente(new UtenteRegistrato(users.get(i).getName(), users.get(i).getSurname(), users.get(i).getCodiceFiscale()), false);
				if(currentNumberOfRegUsers < maxNumberOfRegUsers) {
					viewList.add( new RegisteredUserView(landingPage.getLandingController(), regUser1));
					currentNumberOfRegUsers++;
				}
				registeredUsers.add(regUser1);
			}
			else if(currentRole == Roles.ADMIN.ordinal()) {
				admin = GestoreUtenti.getInstance()
						.creaUtente(new Amministratore(users.get(i).getName(), users.get(i).getSurname(), users.get(i).getCodiceFiscale()), false);
			}
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
		try {
			books = GestoreCatalogo.getInstance().getBookDao().queryForAll();
		/*	if (books.size() == 0) {
			//	createBasicBookCatalog(GestoreCatalogo.getInstance().getBookDao());
			} else {
				System.out.println("Basic book catalog already loaded!");
			} */
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*private void createBasicBookCatalog(Dao<BookDAO, String> bookDao) {
		System.out.println("Inserting 100 book for the first time....");
		List<String[]> book_catalog = new ArrayList<String[]>();
		try {

			book_catalog = GestoreCatalogo.getInstance().readBookCatalog("/assets/Books-light.csv");
			for (int i = 1; i <= 100; i++) {
				// create an instance of Account
				BookDAO book = new BookDAO();
				book.setTitle(book_catalog.get(i)[1]);
				book.setAuthor(book_catalog.get(i)[2]);
				book.setYear(book_catalog.get(i)[3]);
				book.setDescription(
						book_catalog.get(i)[1] + " - " + book_catalog.get(i)[2] + " - " + book_catalog.get(i)[3]);
				book.setIsbn(book_catalog.get(i)[0]);
				book.setEditor(book_catalog.get(i)[4]);
				bookDao.create(book);
			//	if(i==1)
			//		System.out.println(book);
			//	System.out.println("Saving book: ");
			//	System.out.println(book_catalog.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} */

	public boolean usersExist() {
		try {
			users = GestoreUtenti.getInstance().getUserDao().queryForAll();
			if(users == null)
				return false;
			return users.size() > 0; 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	

}
