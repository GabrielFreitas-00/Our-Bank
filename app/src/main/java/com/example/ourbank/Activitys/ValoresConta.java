package com.example.ourbank.Activitys;


public class ValoresConta {

    double salario;
    double saldo;

    void transferir (ValoresConta conta, double valor){
        this.saldo = this.saldo - valor;
        conta.saldo = conta.saldo + valor;
    }
}
