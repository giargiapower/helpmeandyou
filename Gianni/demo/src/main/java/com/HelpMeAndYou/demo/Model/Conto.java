package com.HelpMeAndYou.demo.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "contocorrente")
public class Conto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public Conto() {
    }

}
