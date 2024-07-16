/**
 * 
 */
package models.users;

/**
 * 
 */
public class Amministratore extends Utente {

	public Amministratore(String nome, String cognome, String codiceFiscale) {
		super(Roles.ADMIN);
		setNome(nome);
		setCognome(cognome);
		setCodiceFiscale(codiceFiscale);
	}

}
