package requests.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "categoria")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria {
	@Id
	private String tipo;

	public Categoria(String tipo) {
		this.tipo = tipo;
	}

	public Categoria() {

	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Categoria{" +
				"tipo='" + tipo + '\'' +
				'}';
	}
}
