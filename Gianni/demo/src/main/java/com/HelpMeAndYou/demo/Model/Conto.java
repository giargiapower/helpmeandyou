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
    private long id;


    @Column(name = "saldo")
    private float saldo;

    public Conto() {
    }
    public Conto(float saldo) {
        this.saldo = saldo;
    }

    public long getId() {
        return id;
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
        return "com.example.Spring2.Customer [numero di conto=" + id + ", saldo=" + saldo + "]";
    }
}
