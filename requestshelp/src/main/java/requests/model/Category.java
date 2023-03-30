package requests.model;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    private String type;
}
