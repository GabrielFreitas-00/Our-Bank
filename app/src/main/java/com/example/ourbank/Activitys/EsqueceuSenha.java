package com.example.ourbank.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ourbank.Firebase.ConfiguraçãoFirebase;
import com.example.ourbank.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EsqueceuSenha extends AppCompatActivity {

    private EditText editTextForgot;
    private Button btnEnviar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);

        auth = ConfiguraçãoFirebase.getFirebaseAuth();

        inicializarElementos();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextForgot.getText().toString();

                if ( email.equals("") ){
                    alertToast("Por favor informe seu e-mail !");
                } else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if ( task.isSuccessful() ){
                                alertToast("Verifique sua caixa de e-mail !");
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                alertToast("Erro ao cadastrar e-mail, verifique seu cadastro no App");
                            }
                        }
                    });
                }
            }
        });
    }

    private void inicializarElementos () {
        editTextForgot = findViewById(R.id.editTextForgot);
        btnEnviar = findViewById(R.id.btnEnviar);
    }
    private void alertToast (String alertToast){
        Toast.makeText(this, alertToast, Toast.LENGTH_SHORT).show();
    }
}