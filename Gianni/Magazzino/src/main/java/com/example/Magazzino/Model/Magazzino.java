package com.example.Magazzino.Model;
import javax.persistence.*;
@Entity
@Table(name = "magazzino")
public class Magazzino {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "materiale")
    private String materiale;

    @Column(name = "quantita")
    private int quantita;

}
