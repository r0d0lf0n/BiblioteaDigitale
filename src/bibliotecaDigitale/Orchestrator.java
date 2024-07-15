/**
 * 
 */
package bibliotecaDigitale;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import controllers.GestoreUtenti;
import users.Amministratore;
import users.Roles;
import users.Utente;
import users.UtenteEsterno;
import users.UtenteRegistrato;
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
           new UserAdminView(admin.getNome(), admin.getCognome(), admin.getCodiceFiscale()).setVisible(true);
        });
		
		for (int i = 0; i < externalUsers.size(); i++) {
			SwingUtilities.invokeLater(() -> {
				new ExternalUserView().setVisible(true);
			});
		}
		
		for (int i = 0; i < registeredUsers.size(); i++) {
			Utente utente = registeredUsers.get(i);
			SwingUtilities.invokeLater(() -> {
				new RegisteredUserView(utente).setVisible(true);
			});
		}
		
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
