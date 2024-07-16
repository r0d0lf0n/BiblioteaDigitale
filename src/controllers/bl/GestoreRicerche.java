package controllers.bl;

import java.util.ArrayList;

import models.bl.Libro;

public class GestoreRicerche {

	/**
	 * singleton
	 */
	private GestoreRicerche _instance = null;


	private GestoreRicerche() {
	}

	public GestoreRicerche getInstance() {
		if (_instance == null)
			_instance = new GestoreRicerche();
		return _instance;
	}
	
	public ArrayList<Libro> search(String...params){
		
		synchronized (this) {
			//REALIZZA RICERCA SU DB CON GESTIONE DELLA CONCORRENZA
			// E THREAD PER OPERAZIONE ASINCRONA
			// TODO 
			
			return new ArrayList<Libro>();
		}
	}
	
}
