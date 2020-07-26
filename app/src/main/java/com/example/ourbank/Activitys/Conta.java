package com.example.ourbank.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourbank.R;

import java.util.Random;

public class Conta extends AppCompatActivity {

    TextView textNomeConta, textTelefoneConta, textAgenciaConta, textSaldoConta;
    EditText editTextTransfere;
    Button btnTransferir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        inicializarElementos();

        Bundle dados = getIntent().getExtras();
        String nome = dados.getString("nome");
        String telefone = dados.getString("telefone");
        int agencia = dados.getInt("agencia");
        int conta = dados.getInt("conta");
        int digito = dados.getInt("digito");


        textNomeConta.setText("Olá " + nome);
        textTelefoneConta.setText("Telefone de contato: " + telefone);
        textAgenciaConta.setText("Agência: " + agencia + " Conta: " + conta + "- " + digito);

        final ValoresConta c1 = new ValoresConta();
        final ValoresConta c2 = new ValoresConta();

        c1.salario = 2500;
        c1.saldo = c1.salario;


        String saldo = String.valueOf(c1.salario);
        textSaldoConta.setText("Saldo R$:" + saldo);


        btnTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorTransfere = editTextTransfere.getText().toString();
                double valor = Double.parseDouble(valorTransfere);

                if ( !valorTransfere.isEmpty() ){
                    c1.transferir(c2, valor);

                    String novoSaldo = String.valueOf(c1.saldo);
                    textSaldoConta.setText("Novo saldo: R$" + novoSaldo);
                } else {
                    Toast.makeText(getApplicationContext(),"Preencha o valor", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void inicializarElementos () {
        textNomeConta = findViewById(R.id.textNomeConta);
        textTelefoneConta = findViewById(R.id.textTelefoneConta);
        textAgenciaConta = findViewById(R.id.textAgenciaConta);
        textSaldoConta = findViewById(R.id.textSaldoConta);
        editTextTransfere = findViewById(R.id.editTextTransfere);
        btnTransferir = findViewById(R.id.btnTransferir);
    }

}