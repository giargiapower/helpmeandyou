package requests.model;

import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "richiesta_aiuto")
public class RichiestaAiuto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "descrizione")
	private String descrizione;

	// TODO: vedere se mi servirà anche l'ora (mattina/pome) o meno
	@Column(name = "giorno")
	private LocalDate giorno;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "indirizzo_id")
	private Indirizzo indirizzo;

	@Column(name = "stato")
	private String stato;       // published, accepted, completed

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "richieste_materiali", joinColumns = @JoinColumn(name = "richiesta_id"))
	@Column(name = "nome_materiale")
	private Set<String> materiali;

	/*
		L'attributo "fetch" specifica il modo in cui il framework Spring recupera l'entità correlata.
		In questo caso, l'attributo "LAZY" indica che l'entità User viene caricata solo quando effettivamente richiesta.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "acc_account_id")
	private Account accAccount;					// chi accetta la richiesta

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pub_account_id")
	private Account pubAccount;					// chi pubblica la richiesta

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_tipo")
	private Categoria categoria;

	public RichiestaAiuto(String descrizione, LocalDate giorno, Account pubAccount, Categoria categoria) {
		this.descrizione = descrizione;
		this.giorno = giorno;
		this.stato = "pubblicata";
		this.accAccount = null;
		this.pubAccount = pubAccount;
		this.materiali = new HashSet<>();
		this.categoria = categoria;
	}

	public RichiestaAiuto() {

	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String description) {
		this.descrizione = description;
	}

	public LocalDate getGiorno() {
		return giorno;
	}

	public void setGiorno(LocalDate day) {
		this.giorno = day;
	}

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(Indirizzo place) {
		this.indirizzo = place;
	}

	public Set<String> getMaterials() {
		return materiali;
	}

	public void setMaterials(Set<String> materials) {
		this.materiali = materials;
	}

	public Account getAccAccount() {
		return accAccount;
	}

	public void setAccAccount(Account accAccount) {
		this.accAccount = accAccount;
		this.stato = "accettata";
	}

	public Account getPubAccount() {
		return pubAccount;
	}

	public void setPubAccount(Account pubAccount) {
		this.pubAccount = pubAccount;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String state) {
		this.stato = state;
	}

	@Override
	public String toString() {
		return "RichiestaAiuto{" +
				"id=" + id +
				", descrizione='" + descrizione + '\'' +
				", giorno=" + giorno +
				", indirizzo=" + indirizzo +
				", stato='" + stato + '\'' +
				", materiali=" + materiali +
				", accAccount=" + accAccount +
				", pubAccount=" + pubAccount +
				", categoria=" + categoria +
				'}';
	}
}
