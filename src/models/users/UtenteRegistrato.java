/**
 * 
 */
package models.users;

/**
 * 
 */
public class UtenteRegistrato extends Utente {


	public UtenteRegistrato(String nome, String cognome, String codiceFiscale) {
		super(Roles.REGULAR_USER);
		setNome(nome);
		setCognome(cognome);
		setCodiceFiscale(codiceFiscale);
	}



}
