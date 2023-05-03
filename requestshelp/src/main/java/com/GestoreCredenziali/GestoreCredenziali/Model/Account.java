package com.GestoreCredenziali.GestoreCredenziali.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import requests.model.Categoria;

import javax.persistence.*;

@Entity
@Table(name = "account")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "indirizzo")
    private String indirizzo;

    @Column(name = "stato")
    private String stato;

    @Column(name = "cv")
    private String path_curriculum;

    @Column(name = "documento_identita")
    private String path_documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_tipo")
    private Categoria categoria;

    public Account() {
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Account(String email, String password, String nome, String cognome, String telefono, String indirizzo, String stato) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.stato = stato;
    }

    public Account(String email, String password, String nome, String cognome, String telefono, String indirizzo, String stato, String cv, String id, Categoria categoria) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.stato = stato;
        this.path_curriculum = cv;
        this.path_documento = id;
        this.categoria = categoria;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getStato() {
        return stato;
    }

    public String getPath_curriculum() {
        return path_curriculum;
    }

    public String getPath_documento() {
        return path_documento;
    }

    public Categoria getCategory() {
        return categoria;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setPath_curriculum(String path_curriculum) {
        this.path_curriculum = path_curriculum;
    }

    public void setPath_documento(String path_documento) {
        this.path_documento = path_documento;
    }

    public void setCategory(Categoria categoria) {
        this.categoria = categoria;
    }
}
