package com.GestoreCredenziali.GestoreCredenziali.Model;


import javax.persistence.*;

@Entity
@Table(name = "amministratore")
public class Amministratore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public Amministratore() {
    }

    public Amministratore(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
