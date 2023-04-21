package magazzino.model;

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

	@OneToMany(mappedBy = "materiale")
	private List<RichiestaAiutoMat> richieste = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "magazzino_id")
	private Magazzino magazzino;

	public Materiale() { }

	public Materiale(String nome) {
		this.nome = nome;
	}

	public boolean inUso() {
		if (richieste.isEmpty())
			return false;
		else
			return true;
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
	public String toString() {
		return "Materiale{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", richieste=" + richieste +
				", magazzino=" + magazzino +
				'}';
	}
}
