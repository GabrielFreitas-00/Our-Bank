package com.example.ourbank.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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


public class MainActivity extends AppCompatActivity {

    EditText editSenha, editEmail;
    Button btnEntrar;
    TextView eSenha, registre;

     private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = ConfiguraçãoFirebase.getFirebaseAuth();

        inicializarElementos();
        chamarRegistrar();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String senha = eSenha.getText().toString();

                if ( !email.isEmpty() ){
                    if ( !senha.isEmpty() ){

                        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if ( task.isSuccessful() ){
                                    Intent intent = new Intent(getApplicationContext(), Conta.class);
                                    startActivity(intent);
                                } else {
                                    alertToast("Verifique o e-mail e senha cadastrado !");
                                }
                            }
                        });
                    } else {
                        alertToast("Preencha a senha !");
                    }
                } else {
                    alertToast("Preencha o e-mail !");
                }
            }

        });

    }

    private void chamarRegistrar (){
        registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), registrar.class);
                startActivity(intent);
            }
        });
        eSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EsqueceuSenha.class);
                startActivity(intent);
            }
        });

    }

    private void inicializarElementos (){
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        eSenha = findViewById(R.id.textViewForgot);
        registre = findViewById(R.id.textViewRegister);
    }

    public void alertToast (String alertToast){
        Toast.makeText(this, alertToast, Toast.LENGTH_SHORT).show();
    }
}