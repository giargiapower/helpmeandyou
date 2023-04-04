package requests.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "request_help")
public class RequestHelp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "description")
    private String description;

    // TODO: vedere se mi servirà anche l'ora (mattina/pome) o meno
    @Column(name = "day")
    private LocalDate day;

    // TODO: risistemare il place come classe con indirizzo etc
    @Column(name = "place")
    private String place;

    @Column(name = "state")
    private String state;       // published, accepted, completed

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "requests_materials", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "material_name")
    private Set<String> materials;

    /*
        L'attributo "fetch" specifica il modo in cui il framework Spring recupera l'entità correlata.
        In questo caso, l'attributo "LAZY" indica che l'entità User viene caricata solo quando effettivamente richiesta.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_user_id")
    private User acceptedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pub_user_id")
    private User publishedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_type")
    private Category category;

    public RequestHelp(String description, LocalDate day, String place) {
        this.description = description;
        this.day = day;
        this.place = place;
        this.state = "published";
        this.acceptedUser = null;
        this.materials = new HashSet<>();
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Set<String> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<String> materials) {
        this.materials = materials;
    }

    public User getAcceptedUser() {
        return acceptedUser;
    }

    public void setAcceptedUser(User acceptedUser) {
        this.acceptedUser = acceptedUser;
        this.state = "accepted";
    }

    public User getPublishedUser() {
        return publishedUser;
    }

    public void setPublishedUser(User publishedUser) {
        this.publishedUser = publishedUser;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
                ", place='" + place + '\'' +
                ", state='" + state + '\'' +
                ", materials=" + materials +
                ", acceptedUser=" + acceptedUser +
                ", publishedUser=" + publishedUser +
                ", category=" + category +
                '}';
    }
}
