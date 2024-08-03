/**
 * 
 */
package models.users;

import java.util.Hashtable;

import models.db.LoanDAO;

/**
 * 
 */
public class UtenteRegistrato extends Utente {

	private Hashtable<Integer, LoanDAO> storicoPrestiti = null;

	public UtenteRegistrato(String nome, String cognome, String codiceFiscale) {
		super(Roles.REGULAR_USER);
		setNome(nome);
		setCognome(cognome);
		setCodiceFiscale(codiceFiscale);
		storicoPrestiti = new Hashtable<Integer, LoanDAO>();
	}

	public Hashtable<Integer, LoanDAO> getStoricoPrestiti() {
		return storicoPrestiti;
	}

	public void addPrestito(LoanDAO prestito) {
	//	storicoPrestiti.put(prestito.getIdTransazione(), prestito); //FIXME serve identificativo per la mappa!
	}

}
