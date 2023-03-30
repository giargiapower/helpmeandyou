package com.Segnalazioni.Segnalazioni.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "segnalazioni")
public class Segnalazione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

        @Column(name = "titolo")
        private String titolo;

        @Column(name = "descrizione")
        private String descrizione;

        @Column(name = "data")
        private String data;

        @Column(name = "utente")
        private String utente;

        public Segnalazione() {
        }

        public Segnalazione(String titolo, String descrizione,  String data,  String utente) {
            this.titolo = titolo;
            this.descrizione = descrizione;
            this.data = data;
            this.utente = utente;
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

        public String getData() {
            return data;
        }

        public String getUtente() {
            return utente;
        }

        public void setTitolo(String titolo) {
            this.titolo = titolo;
        }

        public void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }
}
