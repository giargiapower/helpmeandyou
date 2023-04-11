package microservizi.gestione_magazzino.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

@Entity
@Table(name = "materiale")
public class Materiale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data")
    private String data;

    @Column(name = "disponibilità")
    private boolean disponilibità;

    public Materiale() { }

    public Materiale(String nome, String data, boolean disponilibità) {
        this.nome = nome;
        this.data = data;
        this.disponilibità = disponilibità;
    }

    public Long getId() {
        return id;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public void setData(String data) {
        this.data = data;
        // Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
        // this.data = String.valueOf(calendar.getTime());
    }

    public String getData() {
        return this.data;
        // DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        // return dateFormat.format(this.data);
    }

    public void setDisponilibità(boolean disponibile) { this.disponilibità = disponibile; }

    public boolean getDisponibilità() { return this.disponilibità; }

    // public boolean verificaDisponibilità(String data) { return data.compareTo(this.data) == 0; }

    /*@Override
    public String toString() {
        return "materiale{" +
                ", nome='" + nome + '\'' +
                ", data=" + data +
                ", disponilibità=" + disponilibità +
                '}';
    }*/
}
