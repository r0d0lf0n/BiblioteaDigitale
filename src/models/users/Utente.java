/**
 * 
 */
package models.users;

/**
 * 
 */
public abstract class Utente {

	private String nome = "";
	private String cognome = "";
	private String codiceFiscale = "";
	private Roles ruolo;
	private int idTessera;

	public Utente(Roles ruolo) {
		this.ruolo = ruolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Roles getRuolo() {
		return ruolo;
	}

	public void setIdTessera(int idTessera) {
		this.idTessera = idTessera;
	}

	public int getIdTessera() {
		return idTessera;
	}

}
