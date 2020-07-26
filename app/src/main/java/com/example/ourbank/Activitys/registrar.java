package com.example.ourbank.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourbank.Firebase.ConfiguraçãoFirebase;
import com.example.ourbank.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class registrar extends AppCompatActivity {

    public EditText editNome, editEmail, editSenha, editTelefone, editCep, editNumero, editComplemento;
    private Button btnCadastrar;
    private TextView textResultado, textBairro;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        auth = ConfiguraçãoFirebase.getFirebaseAuth(); // Firebase Autenticação

        inicializarElementos(); // Inicialização de elementos
        chamarTelas(); // Chamar tela Principal
    }

    public void chamarTelas (){

        class MyTask extends AsyncTask<String, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {

                String stringUrl = strings[0];
                InputStream inputStream = null;
                InputStreamReader inputStreamReader = null;
                StringBuffer buffer = null;

                try {
                    URL url = new URL(stringUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    inputStream = connection.getInputStream();

                    inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    buffer = new StringBuffer();
                    String linha = "";

                    while ( (linha = reader.readLine()) != null){
                        buffer.append( linha );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return buffer.toString();
            }

            @Override
            public void onPostExecute(String resultado) {
                super.onPostExecute(resultado);

                String logradouro = null;
                String bairro = null;

                try {
                    JSONObject jsonObject = new JSONObject(resultado);
                    logradouro = jsonObject.getString("logradouro");
                    bairro = jsonObject.getString("bairro");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                textResultado.setText( logradouro );
                textBairro.setText( "Bairro: " + bairro );

            }
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome = editNome.getText().toString();
                String email = editEmail.getText().toString();
                final String telefone = editTelefone.getText().toString();
                String senha = editSenha.getText().toString();
                final String cep = editCep.getText().toString();
                String cepNumero = editNumero.getText().toString();
                String complemento = editComplemento.getText().toString();

                String url = "https://viacep.com.br/ws/" + cep + "/json";
                MyTask task = new MyTask();
                task.execute(url);

                Random random = new Random();
                final int conta = random.nextInt(9999);
                final int agencia = random.nextInt(9999);
                final int digito = random.nextInt(9);

                if ( !nome.isEmpty() ){
                    if ( !email.isEmpty() ){
                        if ( !telefone.isEmpty() ){
                            if ( !senha.isEmpty() ) {
                                if (!cep.isEmpty()) {
                                    if ( !cepNumero.isEmpty() ){

                                    auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                alertaToast("Obrigado por se cadastrar !");
                                                //startActivity(new Intent(getApplicationContext(), Conta.class));

                                                Intent enviarDados = new Intent(getApplicationContext(), Conta.class);
                                                enviarDados.putExtra("nome", nome);
                                                enviarDados.putExtra("telefone", telefone);
                                                enviarDados.putExtra("agencia", agencia );
                                                enviarDados.putExtra("conta", conta);
                                                enviarDados.putExtra("digito", digito);

                                                startActivity(enviarDados);

                                            } else {
                                                String erro = "";
                                                try {
                                                    throw task.getException();
                                                } catch (FirebaseAuthWeakPasswordException e) {
                                                    erro = "Digite uma senha mais forte";
                                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                                    erro = "Digite um e-mail válido";
                                                } catch (FirebaseAuthUserCollisionException e) {
                                                    erro = "Conta já cadastrada";
                                                } catch (Exception e) {
                                                    erro = "ao cadastrar usuário: " + e.getMessage();
                                                    e.printStackTrace();
                                                }
                                                alertaToast("Erro: " + erro);
                                            }
                                        }
                                   });

                                    }
                                    else {
                                        alertaToast("Preencha o número !");
                                    }
                                }
                                else {
                                    alertaToast("Preencha o cep");
                                }
                            }
                            else {
                                alertaToast("Preencha a senha !");
                            }
                        } else {
                            alertaToast("Preencha o telefone !");
                        }
                    } else {
                        alertaToast("Preencha o e-mail !");
                    }
                } else {
                    alertaToast("Preencha o nome !");
                }

            }
        });

    }
    private void inicializarElementos (){
        editNome = findViewById(R.id.editTextNome);
        editEmail = findViewById(R.id.editTextEmail);
        editSenha = findViewById(R.id.editTextSenha);
        editTelefone = findViewById(R.id.editTextNumber);
        editCep = findViewById(R.id.editTextCep);
        editNumero = findViewById(R.id.editTextCepNumero);
        editComplemento = findViewById(R.id.editTextCepComplemento);
        textResultado = findViewById(R.id.textResultado);
        textBairro = findViewById(R.id.textBairro);
        btnCadastrar = findViewById(R.id.btnCadastrar);
    }

    private void alertaToast (String alertToast) {
        Toast.makeText(this, alertToast, Toast.LENGTH_SHORT).show();
    }
}