package microservizi.gestione_magazzino.model;

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

    @Column(name = "luogo")
    private String luogo;

    @OneToMany(mappedBy = "magazzino")
    private List<Materiale> materiali;

    public Magazzino() { }

    public Magazzino(String luogo) {
        this.luogo = luogo;
        this.materiali = new ArrayList<>();
    }

    /*public Magazzino(String luogo, List<Materiale> materiali) {
        this.luogo = luogo;
        this.materiali = materiali;
    }*/

    public Long getId() {
        return id;
    }

    public String getLuogo() { return luogo; }

    public void setLuogo(String luogo) { this.luogo = luogo; }

    public List<Materiale> getMateriali() { return materiali; }

    public void setMateriali(List<Materiale> materiali) { this.materiali = materiali; }


    // metodo per aggiungere un materiale al magazzino
    public void addMateriale(Materiale materiale) {
        materiali.add(materiale);
    }

    // metodo per rimuovere un materiale dal magazzino
    public void removeMateriale(Materiale materiale) {
        materiali.remove(materiale);
    }

    @Override
    public String toString() {
        return "Magazzino{" +
                "id=" + id +
                ", luogo='" + luogo + '\'' +
                ", materiali=" + materiali +
                '}';
    }
}
