/**
 * 
 */
package controllers.bl;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import models.db.UserDAO;
import models.users.Roles;
import models.users.Utente;

/**
 * 
 */
public class GestoreUtenti {

	/**
	 * singleton
	 */
	private static GestoreUtenti _instance = null;
	private int idTessera = 0;
	private List<Utente> registeredUsers = null;
	private List<Utente> externalUsers = null;

	Dao<UserDAO, String> userDao; //TODO serve ?



	private GestoreUtenti() {
		
		registeredUsers = new ArrayList<Utente>();
		externalUsers = new ArrayList<Utente>();
		
	}

	public static GestoreUtenti getInstance() {
		if (_instance == null)
			_instance = new GestoreUtenti();
		return _instance;
	}

	public int getIdTessera() {
		return idTessera;
	}

	public Utente creaUtente(Utente utente) {

		synchronized (this) {
			if (utente.getRuolo().equals(Roles.ADMIN))
				utente.setIdTessera(0);
			else if (utente.getRuolo().equals(Roles.EXTERNAL_USER)) {
				utente.setIdTessera(-1);
				utente.setNome("EXTERNAL");
				utente.setCognome("USER");
			} else {
				utente.setIdTessera(++idTessera);
			}
			
			//RENDI PERSISTENTE L'OPERAZIONE TODO
	
			return utente;
		}
	}
	
	public boolean rimuoviUtente(String idTessera, String codiceFiscale) {
		
		synchronized (this) {
			//TODO
			//RENDI PERSISTENTE L'OPERAZIONE TODO
	
			return false;
		}
		
	}
	
	public boolean modificaUtente(Utente utenteMod) {
		
		synchronized (this) {
			//TODO
			//RENDI PERSISTENTE L'OPERAZIONE TODO
	
			return false;
		}
	}
	
	
	/**
	 * @return the userDao
	 */
	public Dao<UserDAO, String> getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(Dao<UserDAO, String> userDao) {
		this.userDao = userDao;
	}
	
}
