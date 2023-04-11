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

    // @OneToMany
    @Column(name = "materiali")
    private String materiali = "";
    // private List<Materiale> materiali = new ArrayList<>();

    public Magazzino() { }

    public Magazzino(String luogo, String materiali) {
        this.luogo = luogo;
        this.materiali = materiali;
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

    // public List<Materiale> getMateriali() { return materiali; }

    // public void setMateriali(Materiale materiale) { this.materiali.add(materiale); }

    public String getMateriali() { return materiali; }

    public void setMateriali(String materiali) { this.materiali += materiali + ";"; }

    /*public List<Materiale> getMateriali() { return materiali; }

    public void setMateriali(Materiale materiale) {
        materiali.add(materiale);
        // System.out.println("Materiale " + materiale.toString() + " aggiunto con successo!");
    }*/

    /*public Materiale getMateriale(Materiale materiale) {
        int pos = materiali.indexOf(new Materiale(materiale.getNome(), materiale.getData(), materiale.getDisponibilità()));
        if(pos < 0) {
            System.out.println("Materiale non prensente in magazzino!");
            return null;
        }
        return materiali.get(pos);
    }*/

    /*public void removeMateriale(String nome) {
        int pos = materiali.indexOf(new Materiale(nome));
        if(pos < 0)  System.out.println("Materiale non prensente in magazzino!");
        materiali.remove(pos);
        System.out.println("Materiale " + nome + " rimosso con successo!");
    }*/

    /*@Override
    public String toString() {
        return "Magazzino{" +
                "id=" + id +
                ", luogo='" + luogo + '\'' +
                ", materiali=" + materiali.toString() +
                '}';
    }*/
}
