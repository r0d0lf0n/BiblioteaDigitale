/**
 * 
 */
package controllers;

import users.Roles;
import users.Utente;

/**
 * 
 */
public class GestoreUtenti {

	
	/**
	 * singleton
	 */
	private static GestoreUtenti _instance = null;
	private int idTessera = 0;
	
	
	private GestoreUtenti() {
	}
	
	public static GestoreUtenti getInstance() {
		if(_instance == null)
			_instance = new GestoreUtenti();
		return _instance;
	}


	public int getIdTessera() {
		return idTessera;
	}

	public Utente creaUtente(Utente utente) {

		if(utente.getRuolo().equals(Roles.ADMIN))
			utente.setIdTessera(0);
		else if(utente.getRuolo().equals(Roles.EXTERNAL_USER)) {
			utente.setIdTessera(-1);
			utente.setNome("EXTERNAL");
			utente.setCognome("USER");
		}
		else {
			utente.setIdTessera(++idTessera);
		}
		return utente;
	}

	
}
