/**
 * 
 */
package users;

import java.util.Hashtable;

import models.Prestito;

/**
 * 
 */
public class UtenteRegistrato extends Utente {
	
	private Hashtable<Integer, Prestito> storicoPrestiti = null;

	public UtenteRegistrato(String nome, String cognome, String codiceFiscale) {
		super(Roles.REGULAR_USER);
		setNome(nome);
		setCognome(cognome);
		setCodiceFiscale(codiceFiscale);
		storicoPrestiti = new Hashtable<Integer, Prestito>();
	}

	public Hashtable<Integer, Prestito> getStoricoPrestiti() {
		return storicoPrestiti;
	}

	public void addPrestito(Prestito prestito) {
		storicoPrestiti.put(prestito.getIdTransazione(), prestito);
	}

}
