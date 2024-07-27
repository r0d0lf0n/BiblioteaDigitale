package controllers.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.j256.ormlite.support.ConnectionSource;
import bibliotecaDigitale.CatalogMain;
import bibliotecaDigitale.Orchestrator;
import database.DatabaseConfig;
import models.users.Roles;
import views.ExternalUserView;
import views.LandingPageView;
import views.RegisteredUserView;
import views.UserAdminView;

public class LandingPageController {
	LandingPageView landingPageView;
	List<JFrame> instanciatedPanels = new ArrayList<JFrame>();
	private static final int adminNumber = 1;
	private static final int regUserNumber = 3;
	private static final int extUserNumber = 2;
	private static final String[] FIRST_NAMES = {"Mario", "Luigi", "Giovanni", "Anna", "Maria", "Luca"};
    private static final String[] LAST_NAMES = {"Rossi", "Bianchi", "Verdi", "Neri", "Gialli", "Marroni"};

	public LandingPageController(LandingPageView view) {
		landingPageView = view;	
		System.out.println("Loading pane.......");
		ShowView();
	}
	
	public void ShowView() {
		System.out.println("Loading view.......");
		landingPageView.setVisible(true);
		
		JButton openLoans = landingPageView.getOpenLoansButton();
		openLoans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicking openLoans");
			}
		});
		
		JButton openCatalog = landingPageView.getOpenCatalogButton();
		openCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeOpenedPanels();		
				CatalogMain catalog = new CatalogMain();
				instanciatedPanels.add(catalog.getView());
			}
		});
		
		JButton openAdvancedSearch = landingPageView.getAdvancedSearchButton();
		openAdvancedSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicking openAdvancedSearch");
				closeOpenedPanels();		
				
				Orchestrator orchestrator = new Orchestrator();
				
				//create users
				for(int i=0; i< adminNumber; i++) {
					orchestrator.startUser(Roles.ADMIN, generateUser());
				}
				
				for(int i=0; i< regUserNumber; i++) {
					orchestrator.startUser(Roles.REGULAR_USER, generateUser());
				}
				
				
				for(int i=0; i< extUserNumber; i++) {
					orchestrator.startUser(Roles.EXTERNAL_USER, generateUser());
				}
				//start main app
				orchestrator.startApp();
				instanciatedPanels.add(orchestrator.getUserAdminView());
				instanciatedPanels.add(orchestrator.getRegisteredUserView());
				instanciatedPanels.add(orchestrator.getExternalUserView());
				System.out.println(instanciatedPanels);
			}
		});
	}
	
	private static List<String> generateUser() {
        List<String> user = new ArrayList<>();
        Random random = new Random();

         String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
         String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
         String codFiscale = generateCodiceFiscale(firstName, lastName);

         user.add(firstName);
         user.add(lastName);
         user.add(codFiscale);

        return user;
    }

    private static String generateCodiceFiscale(String firstName, String lastName) {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";

        StringBuilder codiceFiscale = new StringBuilder();

        codiceFiscale.append(getThreeCharacters(lastName.toUpperCase(), characters, random));
        codiceFiscale.append(getThreeCharacters(firstName.toUpperCase(), characters, random));

        for (int i = 0; i < 2; i++) {
            codiceFiscale.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        codiceFiscale.append(characters.charAt(random.nextInt(characters.length())));

        for (int i = 0; i < 2; i++) {
            codiceFiscale.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        for (int i = 0; i < 4; i++) {
            codiceFiscale.append(characters.charAt(random.nextInt(characters.length())));
        }

        return codiceFiscale.toString();
    }

    private static String getThreeCharacters(String name, String characters, Random random) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            if (i < name.length()) {
                result.append(name.charAt(i));
            } else {
                result.append(characters.charAt(random.nextInt(characters.length())));
            }
        }

        return result.toString();
    }
    
    private void closeOpenedPanels() {
    	for (JFrame panel : instanciatedPanels) {
    		if (panel != null) {
    			System.out.println("Closing panel: " + panel.getTitle());
    			panel.setVisible(false);
    		}
    	}
    }
}
