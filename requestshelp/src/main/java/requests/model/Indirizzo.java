package requests.model;

import javax.persistence.*;

@Entity
@Table(name = "indirizzo")
public class Indirizzo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "regione")
	private String regione;

	@Column(name = "provincia")
	private String provincia;

	@Column(name = "città")
	private String citta;


	@Column(name = "indirizzo")
	private String indirizzo;

	public Indirizzo(String regione, String provincia, String citta, String indirizzo) {
		this.regione = regione;
		this.provincia = provincia;
		this.citta = citta;
		this.indirizzo = indirizzo;
	}

	public Indirizzo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	@Override
	public String toString() {
		return "Indirizzo{" +
				"id=" + id +
				", regione='" + regione + '\'' +
				", provincia='" + provincia + '\'' +
				", città='" + citta + '\'' +
				", indirizzo='" + indirizzo + '\'' +
				'}';
	}
}
