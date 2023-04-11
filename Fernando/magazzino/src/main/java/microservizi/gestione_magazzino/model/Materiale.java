package microservizi.gestione_magazzino.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materiale")
public class Materiale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "materiale")
    private List<RequestHelp> richieste = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazzino_id")
    private Magazzino magazzino;

    public Materiale() { }

    public Materiale(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    // public boolean verificaDisponibilità(String data) { return data.compareTo(this.data) == 0; }

    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
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
