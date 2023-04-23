package magazzino.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "magazzino")
public class Magazzino {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "regione")
	private String regione;

	@Column(name = "provincia")
	private String provincia;

	@Column(name = "citta")
	private String citta;

	@Column(name = "indirizzo")
	private String indirizzo;

	@OneToMany(mappedBy = "magazzino", fetch = FetchType.EAGER)
	private List<Materiale> materiali;

	public Magazzino() { }

	public Magazzino(String regione, String provincia, String citta, String indirizzo) {
		this.regione = regione;
		this.provincia = provincia;
		this.citta = citta;
		this.indirizzo = indirizzo;
		this.materiali = new ArrayList<>();
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

	public List<Materiale> getMateriali() {
		return materiali;
	}

	public void setMateriali(List<Materiale> materiali) {
		this.materiali = materiali;
	}

	// metodo per aggiungere un materiale al magazzino
	public void aggiungiMateriale(Materiale materiale) {
		materiali.add(materiale);
	}

	// metodo per rimuovere un materiale dal magazzino
	public void rimuoviMateriale(Materiale materiale) {
		materiali.remove(materiale);
	}

	@Override
	public String toString() {
		return "Magazzino{" +
				"id=" + id +
				", regione='" + regione + '\'' +
				", provincia='" + provincia + '\'' +
				", citta='" + citta + '\'' +
				", indirizzo='" + indirizzo + '\'' +
				", materiali=" + materiali.size() + //Evita di serializzare l'oggetto materiali
				'}';
	}
}
