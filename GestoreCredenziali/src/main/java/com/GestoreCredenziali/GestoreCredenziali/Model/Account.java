package com.GestoreCredenziali.GestoreCredenziali.Model;


import javax.persistence.*;

@Entity
@Table(name = "account")
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
    @JoinColumn(name = "category_type")
    private Category category;

    //vanno inseriti documento e curriculum... da capire come salvare

    public Account() {
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Account(String email, String nome, String cognome) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Account(String email, String password, String nome, String cognome, String telefono, String indirizzo, String stato) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.stato = stato;
        this.category = category;
    }

    public Account(String email, String password, String nome, String cognome, String telefono, String indirizzo, String stato, String cv, String id, Category category) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.stato = stato;
        this.path_curriculum = cv;
        this.path_documento = id;
        this.category = category;
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

    public Category getCategory() {
        return category;
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

    public void setCategory(Category category) {
        this.category = category;
    }
}
