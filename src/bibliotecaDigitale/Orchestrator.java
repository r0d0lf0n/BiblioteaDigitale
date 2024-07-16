/**
 * 
 */
package bibliotecaDigitale;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import controllers.bl.GestoreUtenti;
import models.users.Amministratore;
import models.users.Roles;
import models.users.Utente;
import models.users.UtenteEsterno;
import models.users.UtenteRegistrato;
import views.ExternalUserView;
import views.RegisteredUserView;
import views.UserAdminView;

/**
 * 
 */
public class Orchestrator {
	
	private Utente admin = null;
	private List<Utente> registeredUsers = null;
	private List<Utente> externalUsers = null;
	
	public Orchestrator() {
		registeredUsers = new ArrayList<Utente>();
		externalUsers = new ArrayList<Utente>();
	}

	public void startApp() {

		
		 SwingUtilities.invokeLater(() -> {
	            int totalFrames = 1 + externalUsers.size() + registeredUsers.size(); // Total number of frames to display
	            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	            int frameWidth = 400;
	            int frameHeight = 300;
	            int rows = 2;
	            int cols = 3;
	            int gap = 10;
	            int startX = (screenSize.width - (cols * frameWidth + (cols - 1) * gap)) / 2;
	            int startY = (screenSize.height - (rows * frameHeight + (rows - 1) * gap)) / 2;

	            // Position the admin view
	            UserAdminView adminFrame = new UserAdminView(admin.getNome(), admin.getCognome(), admin.getCodiceFiscale());
	            adminFrame.setLocation(startX, startY);
	            adminFrame.setVisible(true);

	            int x = startX;
	            int y = startY + frameHeight + gap;

	            // Position the external user views
	            for (Utente user : externalUsers) {
	                ExternalUserView externalUserFrame = new ExternalUserView();
	                externalUserFrame.setLocation(x, y);
	                externalUserFrame.setVisible(true);
	                x += frameWidth + gap;
	                if (x + frameWidth > screenSize.width) {
	                    x = startX;
	                    y += frameHeight + gap;
	                }
	            }

	            // Position the registered user views
	            for (Utente user : registeredUsers) {
	                RegisteredUserView registeredUserFrame = new RegisteredUserView(user);
	                registeredUserFrame.setLocation(x, y);
	                registeredUserFrame.setVisible(true);
	                x += frameWidth + gap;
	                if (x + frameWidth > screenSize.width) {
	                    x = startX;
	                    y += frameHeight + gap;
	                }
	            }
	        });
	/*	SwingUtilities.invokeLater(() -> {
           UserAdminView frame = new UserAdminView(admin.getNome(), admin.getCognome(), admin.getCodiceFiscale());
           frame.setVisible(true);
        });
		
		for (int i = 0; i < externalUsers.size(); i++) {
			SwingUtilities.invokeLater(() -> {
				ExternalUserView frame = new ExternalUserView();
				frame.setVisible(true);
			});
		}
		
		for (int i = 0; i < registeredUsers.size(); i++) {
			Utente utente = registeredUsers.get(i);
			SwingUtilities.invokeLater(() -> {
				RegisteredUserView frame = new RegisteredUserView(utente);
				frame.setVisible(true);
			});
		}*/
		
		
	}

	public void startUser(Roles role, List<String> user) {
		
		if(role.equals(Roles.ADMIN)) {
			admin = GestoreUtenti.getInstance().creaUtente(new Amministratore(user.get(0), user.get(1), user.get(2)));
			System.out.println("ADMIN " + admin.getNome() + " " + admin.getCognome() + ", " + admin.getCodiceFiscale()
				+ ". TESSERA NUM: " + ((Amministratore) admin).getIdTessera());
		}
		else if(role.equals(Roles.REGULAR_USER)) {
			Utente regUser1 =  GestoreUtenti.getInstance().creaUtente(new UtenteRegistrato(user.get(0), user.get(1), user.get(2)));
			System.out.println("UTENTE REGISTRATO " + regUser1.getNome() + " " + regUser1.getCognome() + ", "
					+ regUser1.getCodiceFiscale() + ". TESSERA NUM: " + regUser1.getIdTessera());
			registeredUsers.add(regUser1);
		}
		else if(role.equals(Roles.EXTERNAL_USER)) {
			Utente user1 = GestoreUtenti.getInstance().creaUtente(new UtenteEsterno());
			System.out.println("USER " + user1.getNome() + " " + user1.getCognome() + ", " + user1.getCodiceFiscale()
					+ ". TESSERA NUM: " + user1.getIdTessera());
			externalUsers.add(user1);
		}
		
	}

}

