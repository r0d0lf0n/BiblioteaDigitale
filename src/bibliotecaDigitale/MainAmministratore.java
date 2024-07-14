/**
 * 
 */
package bibliotecaDigitale;

import controllers.GestoreUtenti;
import users.Amministratore;
import users.Utente;

/**
 * 
 */
public class MainAmministratore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String nome = "Mario";
		String cognome = "Rossi";
		String codiceFiscale = "MRORSS53H13D085M";
		
		Utente admin = GestoreUtenti.getInstance().creaUtente(new Amministratore(nome,	cognome, codiceFiscale));
		System.out.println("ADMIN "+admin.getNome()+" "+admin.getCognome()+", "+admin.getCodiceFiscale()+". TESSERA NUM: "+((Amministratore)admin).getIdTessera());

		String nome1 = "Paolo";
		String cognome1 = "Verde";
		String codiceFiscale1 = "PLOVRD54M63I114W";
		
		Utente admin1 = GestoreUtenti.getInstance().creaUtente(new Amministratore(nome1, cognome1, codiceFiscale1));
		System.out.println("ADMIN 1 "+admin1.getNome()+" "+admin1.getCognome()+", "+admin1.getCodiceFiscale()+". TESSERA NUM: "+((Amministratore)admin).getIdTessera());
		
	}

}
