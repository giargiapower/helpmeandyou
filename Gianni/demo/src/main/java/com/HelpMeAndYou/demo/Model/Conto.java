package com.HelpMeAndYou.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banca")
public class Conto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long conto_corrente;

    @Column(name = "numero_di_conto")
    private int numero_di_conto;


    @Column(name = "saldo")
    private float saldo;

    public Conto() {
    }
    public Conto(int numero_di_conto, float saldo) {
        this.numero_di_conto = numero_di_conto;
        this.saldo = saldo;
    }

    public long getId() {
        return conto_corrente;
    }

    public void setConto(int numero_di_conto) {
        this.numero_di_conto = numero_di_conto;
    }

    public int getConto() {
        return this.numero_di_conto;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getSaldo() {
        return saldo;
    }
    public void addSaldo(float saldo) {
        this.saldo += saldo;
    }

    @Override
    public String toString() {
        return "com.example.Spring2.Customer [id=" + conto_corrente + ", numero di conto=" + numero_di_conto + ", saldo=" + saldo + "]";
    }
}
