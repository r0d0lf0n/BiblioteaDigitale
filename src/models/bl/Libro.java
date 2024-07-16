/**
 * 
 */
package models.bl;

/**
 * 
 */
public class Libro implements LibroIF {
	
	private String titolo;
	private String autore;
	private String casaEditrice;
	private String annoPubblicazione;
	private String ISBN;
	private String genere;
	
	public Libro() {
		//empty ADD PARAMETERS TODO
	}
	
	
	/**
	 * @return the titolo
	 */
	public String getTitolo() {
		return titolo;
	}
	/**
	 * @param titolo the titolo to set
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	/**
	 * @return the autore
	 */
	public String getAutore() {
		return autore;
	}
	/**
	 * @param autore the autore to set
	 */
	public void setAutore(String autore) {
		this.autore = autore;
	}
	/**
	 * @return the casaEditrice
	 */
	public String getCasaEditrice() {
		return casaEditrice;
	}
	/**
	 * @param casaEditrice the casaEditrice to set
	 */
	public void setCasaEditrice(String casaEditrice) {
		this.casaEditrice = casaEditrice;
	}
	/**
	 * @return the annoPubblicazione
	 */
	public String getAnnoPubblicazione() {
		return annoPubblicazione;
	}
	/**
	 * @param annoPubblicazione the annoPubblicazione to set
	 */
	public void setAnnoPubblicazione(String annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}
	/**
	 * @return the iSBN
	 */
	public String getISBN() {
		return ISBN;
	}
	/**
	 * @param iSBN the iSBN to set
	 */
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	/**
	 * @return the genere
	 */
	public String getGenere() {
		return genere;
	}
	/**
	 * @param genere the genere to set
	 */
	public void setGenere(String genere) {
		this.genere = genere;
	}



	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}


}
