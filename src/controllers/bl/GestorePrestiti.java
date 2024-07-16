/**
 * 
 */
package controllers.bl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.bl.Prestito;
import models.users.Utente;

/**
 * 
 */
public class GestorePrestiti {

	/**
	 * singleton
	 */
	private GestorePrestiti _instance = null;
	private int idTransazione = 0;

	private Map<Utente, ArrayList<Prestito>> storicoPrestiti = null;

	private GestorePrestiti() {
		storicoPrestiti = new HashMap<Utente, ArrayList<Prestito>>();
	}

	public GestorePrestiti getInstance() {
		if (_instance == null)
			_instance = new GestorePrestiti();
		return _instance;
	}

	public int getIdTransazione() {
		return idTransazione;
	}

	public void registraTransazione(Utente utente, Prestito p) {
		
		synchronized (this) {
		
			idTransazione++;
			p.setIdTransazione(idTransazione);
			ArrayList<Prestito> prestiti = storicoPrestiti.get(utente);
			prestiti.add(p);
			storicoPrestiti.replace(utente, prestiti);
			
			//RENDI PERSISTENTE L'OPERAZIONE TODO
		}
	}
	
	public void registraRestituzione(Utente utente, Prestito p) {
		
		synchronized (this) {
			
			ArrayList<Prestito> prestiti = storicoPrestiti.get(utente);
			for (Iterator<Prestito> iterator = prestiti.iterator(); iterator.hasNext();) {
				Prestito prestito = (Prestito) iterator.next();
				if(prestito.equals(p))
					prestiti.remove(prestito); //probabile exception FIXME
			}
			storicoPrestiti.replace(utente, prestiti);
			
			//RENDI PERSISTENTE L'OPERAZIONE TODO
		}
	}

}
