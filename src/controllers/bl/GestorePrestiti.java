/**
 * 
 */
package controllers.bl;

import com.j256.ormlite.dao.Dao;

import models.db.LoanDAO;
import models.users.Utente;

/**
 * 
 */
public class GestorePrestiti {

	/**
	 * singleton
	 */
	private static GestorePrestiti _instance = null;
	private int idTransazione = 0;
	Dao<LoanDAO, String> loanDao;


	//private Map<Utente, ArrayList<LoanDAO>> storicoPrestiti = null;

	private GestorePrestiti() {
		//storicoPrestiti = new HashMap<Utente, ArrayList<LoanDAO>>();
	}

	public static GestorePrestiti getInstance() {
		if (_instance == null)
			_instance = new GestorePrestiti();
		return _instance;
	}

	public int getIdTransazione() {
		return idTransazione;
	}

	public void registraTransazione(Utente utente, LoanDAO p) {
		
		synchronized (this) {
		
//			idTransazione++;
//			p.setIdTransazione(idTransazione); //FIXME
//			ArrayList<LoanDAO> prestiti = storicoPrestiti.get(utente);
//			prestiti.add(p);
//			storicoPrestiti.replace(utente, prestiti);
			
			//RENDI PERSISTENTE L'OPERAZIONE TODO
		}
	}
	
	public void registraRestituzione(Utente utente, LoanDAO p) {
		
		synchronized (this) {
			
//			ArrayList<LoanDAO> prestiti = storicoPrestiti.get(utente);
//			for (Iterator<LoanDAO> iterator = prestiti.iterator(); iterator.hasNext();) {
//				LoanDAO prestito = (LoanDAO) iterator.next();
//				if(prestito.equals(p))
//					prestiti.remove(prestito); //probabile exception FIXME
//			}
//			storicoPrestiti.replace(utente, prestiti);
			
			//RENDI PERSISTENTE L'OPERAZIONE TODO
		}
	}

	/**
	 * @return the loanDao
	 */
	public Dao<LoanDAO, String> getLoanDao() {
		return loanDao;
	}

	/**
	 * @param loanDao the loanDao to set
	 */
	public void setLoanDao(Dao<LoanDAO, String> loanDao) {
		this.loanDao = loanDao;
	}

}
