package com.helpmeandyou.demo.Model;

public class Conto {
    private String conto_corrente;
    private float saldo;

    private String GetContoCorrente() {
        return this.conto_corrente;
    }

    private float GetSaldo() {
        return this.saldo;
    }

    private void SetContoCorrente(String conto_corrente) {
        this.conto_corrente = conto_corrente;
    }

    private void SetSaldo(float saldo) {
        this.saldo = saldo;
    }
    private void AggiungiSaldo(float saldo) {
        this.saldo += saldo;
    }
    private void SottraiSaldo(float saldo) {
        this.saldo -= saldo;
    }

}


