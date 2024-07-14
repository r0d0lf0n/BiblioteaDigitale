/**
 * 
 */
package bibliotecaDigitale;

import controllers.GestoreUtenti;
import users.Amministratore;
import users.Utente;
import users.UtenteRegistrato;

/**
 * 
 */
public class MainUtenteRegistrato {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String nome = "Mario";
		String cognome = "Rossi";
		String codiceFiscale = "MRORSS53H13D085M";
		
		Utente regUser1 = GestoreUtenti.getInstance().creaUtente(new UtenteRegistrato(nome,	cognome, codiceFiscale));
		System.out.println("UTENTE REGISTRATO "+regUser1.getNome()+" "+regUser1.getCognome()+", "+regUser1.getCodiceFiscale()+". TESSERA NUM: "+regUser1.getIdTessera());
		
		Utente regUser2 = GestoreUtenti.getInstance().creaUtente(new UtenteRegistrato(nome,	cognome, codiceFiscale));
		System.out.println("UTENTE REGISTRATO "+regUser2.getNome()+" "+regUser2.getCognome()+", "+regUser2.getCodiceFiscale()+". TESSERA NUM: "+regUser2.getIdTessera());
		
		Utente regUser3 = GestoreUtenti.getInstance().creaUtente(new UtenteRegistrato(nome,	cognome, codiceFiscale));
		System.out.println("UTENTE REGISTRATO "+regUser3.getNome()+" "+regUser3.getCognome()+", "+regUser3.getCodiceFiscale()+". TESSERA NUM: "+regUser3.getIdTessera());

	}

}
