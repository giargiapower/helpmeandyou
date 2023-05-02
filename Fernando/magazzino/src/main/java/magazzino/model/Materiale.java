package magazzino.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materiale")
public class Materiale {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "nome")
	private String nome;

	@OneToMany(mappedBy = "materiale", fetch = FetchType.EAGER)
	private List<RichiestaAiutoMat> richieste;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "magazzino_id")
	@JsonIgnore
	private Magazzino magazzino;

	public Materiale() { }

	public Materiale(String nome, Magazzino magazzino) {
		this.nome = nome;
		this.richieste = new ArrayList<>();
		this.magazzino = magazzino;
	}

	public boolean inUso() {
		return !richieste.isEmpty();
	}

	public void aggiungiRichiesta(RichiestaAiutoMat richiesta) {
		richieste.add(richiesta);
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public List<RichiestaAiutoMat> getRichieste() {
		return richieste;
	}

	public Magazzino getMagazzino() {
		return magazzino;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setMagazzino(Magazzino magazzino) {
		this.magazzino = magazzino;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRichieste(List<RichiestaAiutoMat> richieste) {
		this.richieste = richieste;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Materiale && ((Materiale) obj).getId() == this.id;
	}

	@Override
	public String toString() {
		return "Materiale{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", richieste=" + richieste +
				", magazzino=" + magazzino.getProvincia() + //Evita di serializzare l'oggetto magazzino
				'}';
	}
}
