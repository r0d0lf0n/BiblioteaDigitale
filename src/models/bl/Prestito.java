/**
 * 
 */
package models.bl;

import java.util.Date;

/**
 * 
 */
public class Prestito {

	private int idTransazione;
	private Date dataPresito;
	private Date dataConsegna;
	private Libro libroConsultato;

	public Prestito() {
		// default constructor
	}

	public Date getDataPresito() {
		return dataPresito;
	}

	public void setDataPresito(Date dataPresito) {
		this.dataPresito = dataPresito;
	}

	public Date getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(Date dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	public Libro getLibroConsultato() {
		return libroConsultato;
	}

	public void setLibroConsultato(Libro libroConsultato) {
		this.libroConsultato = libroConsultato;
	}

	public int getIdTransazione() {
		return idTransazione;
	}

	public void setIdTransazione(int idTransazione) {
		this.idTransazione = idTransazione;
	}

}
