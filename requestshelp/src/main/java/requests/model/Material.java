package requests.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "materials")
    private Set<RequestHelp> requests = new HashSet<>();


    public Material(String name) {
        this.name = name;
    }

    public Material() {

    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", requests=" + requests +
                '}';
    }
}
