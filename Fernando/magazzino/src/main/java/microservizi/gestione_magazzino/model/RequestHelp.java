package microservizi.gestione_magazzino.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "request_help")
public class RequestHelp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "day")
    private LocalDate day;

    @Column(name = "state")
    private String state;       // published, accepted, completed

    //relazione many-to-one con la classe Materiale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materiale_id")
    private Materiale materiale;

    /*
        L'attributo "fetch" specifica il modo in cui il framework Spring recupera l'entità correlata.
        In questo caso, l'attributo "LAZY" indica che l'entità User viene caricata solo quando effettivamente richiesta.
     */

    public RequestHelp(String description, LocalDate day, String place, Materiale material) {
        this.description = description;
        this.day = day;
        this.state = "published";
        this.materiale = material;
    }

    public RequestHelp() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Materiale getMaterial() {
        return materiale;
    }

    public void setMaterial(Materiale material) {
        this.materiale = material;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "RequestHelp{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", day=" + day +
                ", state='" + state + '\'' +
                ", material=" + materiale +
                '}';
    }
}
