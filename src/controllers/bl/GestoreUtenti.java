/**
 * 
 */
package controllers.bl;

import java.sql.SQLException;
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

	Dao<UserDAO, String> userDao; 


	private GestoreUtenti() {
	
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
			
			//RENDI PERSISTENTE L'OPERAZIONE
			if(utente.getRuolo() != Roles.EXTERNAL_USER) {
				UserDAO persistentUser = new UserDAO();
				persistentUser.setAddress("");
				persistentUser.setName(utente.getNome());
				persistentUser.setPhone("");
				persistentUser.setRole(utente.getRuolo().ordinal());
				persistentUser.setSurname(utente.getCognome());
				persistentUser.setId(utente.getIdTessera());
				persistentUser.setCodiceFiscale(utente.getCodiceFiscale());
				try {
					synchronized (this) {
						userDao.create(persistentUser);
					}
				} catch (SQLException e) {
					e.printStackTrace(); //CREATE EXCEPTION
					return null;
				}
			}
	
			return utente;
		}
	}
	
	public boolean rimuoviUtente(String idTessera, String codiceFiscale) {
		
		//RENDI PERSISTENTE L'OPERAZIONE 
		UserDAO toBeDeleted = new UserDAO();
		toBeDeleted.setId(this.idTessera);
		toBeDeleted.setCodiceFiscale(codiceFiscale);
		
		synchronized (this) {
			try {
				userDao.delete(toBeDeleted);
				return true;
			} catch (SQLException e) {
				e.printStackTrace(); //DELETE EXCEPTION'
				return false;
			}
		}
		
	}
	
	public boolean modificaUtente(Utente utenteMod) {
		
		
		synchronized (this) {

			//RENDI PERSISTENTE L'OPERAZIONE 
			try {
				List<UserDAO> users = userDao.queryForAll();
				UserDAO userFound = null;
				for (UserDAO user : users) {
					if(user.getId() == utenteMod.getIdTessera()) {
						userFound = new UserDAO();
						userFound.setCodiceFiscale(utenteMod.getCodiceFiscale());
						userFound.setName(utenteMod.getNome());
						userFound.setSurname(utenteMod.getCognome());
						userFound.setRole(utenteMod.getRuolo().ordinal());
					}
				}
				if(userFound != null)
					userDao.createOrUpdate(userFound);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
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
