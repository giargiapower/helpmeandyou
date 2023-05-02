package magazzino.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "richiesta_aiuto_mat")
public class RichiestaAiutoMat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "giorno")
	private LocalDate giorno;

	@Column(name = "provincia")
	private String provincia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materiale_id")
	@JsonIgnore
	private Materiale materiale;

	public RichiestaAiutoMat(LocalDate giorno, String provincia, Materiale materiale) {
		this.giorno = giorno;
		this.provincia = provincia;
		this.materiale = materiale;
	}

	public RichiestaAiutoMat() {

	}

	public long getId() {
		return id;
	}

	public LocalDate getGiorno() {
		return giorno;
	}

	public String getProvincia() {
		return provincia;
	}

	public Materiale getMateriale() {
		return materiale;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setGiorno(LocalDate giorno) {
		this.giorno = giorno;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public void setMateriale(Materiale materiale) {
		this.materiale = materiale;
	}

	@Override
	public String toString() {
		return "RichiestaAiutoMat{" +
				"id=" + id +
				", giorno=" + giorno +
				", provincia='" + provincia + '\'' +
				'}';
	}
}
