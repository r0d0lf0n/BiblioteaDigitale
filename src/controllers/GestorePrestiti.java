/**
 * 
 */
package controllers;

import java.util.HashSet;

import models.Prestito;

/**
 * 
 */
public class GestorePrestiti {

	/**
	 * singleton
	 */
	private GestorePrestiti _instance = null;
	private int idTransazione = 0;

	private HashSet<Prestito> storicoPrestiti = null;

	private GestorePrestiti() {
		storicoPrestiti = new HashSet<Prestito>();
	}

	public GestorePrestiti getInstance() {
		if (_instance == null)
			_instance = new GestorePrestiti();
		return _instance;
	}

	public int getIdTransazione() {
		return idTransazione;
	}

	public void registraTransazione(Prestito p) {
		idTransazione++;
		p.setIdTransazione(idTransazione);
		storicoPrestiti.add(p);
	}

}
