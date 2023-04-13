package com.example.Gestione_utenti.Model;

import javax.persistence.*;

@Entity
@Table(name = "segnalazione")
public class Segnalazioni {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "titolo")
    private String titolo;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "tipologia")
    private String tipologia;

    @Column(name = "utente_segnalante")
    private String creatore;

    @Column(name = "utente_segnalato")
    private String segnalato;


    public Segnalazioni() {
    }

    public Segnalazioni(String titolo, String descrizione,  String tipologia,  String segnalante, String segnalato) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tipologia = tipologia;
        this.creatore = segnalante;
        this.segnalato = segnalato;
    }

    public long getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getTipologia() {
        return tipologia;
    }

    public String getCreatore() {
        return creatore;
    }

    public String getSegnalato() {
        return segnalato;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
