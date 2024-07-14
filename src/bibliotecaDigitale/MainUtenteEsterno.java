/**
 * 
 */
package bibliotecaDigitale;

import controllers.GestoreUtenti;
import users.Utente;
import users.UtenteEsterno;

/**
 * 
 */
public class MainUtenteEsterno {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	
		Utente user1 = GestoreUtenti.getInstance().creaUtente(new UtenteEsterno());
		System.out.println("USER "+user1.getNome()+" "+user1.getCognome()+", "+user1.getCodiceFiscale()+". TESSERA NUM: "+user1.getIdTessera());
		
		Utente user2 = GestoreUtenti.getInstance().creaUtente(new UtenteEsterno());
		System.out.println("USER "+user2.getNome()+" "+user2.getCognome()+", "+user2.getCodiceFiscale()+". TESSERA NUM: "+user2.getIdTessera());
		
		Utente user3 = GestoreUtenti.getInstance().creaUtente(new UtenteEsterno());
		System.out.println("USER "+user3.getNome()+" "+user3.getCognome()+", "+user3.getCodiceFiscale()+". TESSERA NUM: "+user3.getIdTessera());

	}

}
