

package com.GestoreCredenziali.GestoreCredenziali.Model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
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
