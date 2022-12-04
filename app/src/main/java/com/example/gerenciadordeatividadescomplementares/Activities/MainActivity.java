package com.example.gerenciadordeatividadescomplementares.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gerenciadordeatividadescomplementares.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button botaoEntrar;

    private void logar(View view){
        boolean campoVazio = false;

        EditText emailTextInput = findViewById(R.id.emailTextInput);
        EditText senhaTextInput = findViewById(R.id.senhaTextInput);
        TextView erroTextView = findViewById(R.id.erroTextView);

        String email = emailTextInput.getText().toString();
        String senha = senhaTextInput.getText().toString();
        erroTextView.setText("");

        if(email.equals("")){
            emailTextInput.setError("Você precisa inserir um endereço de e-mail");
            campoVazio = true;
        }

        if(senha.equals("")){
            senhaTextInput.setError("Você precisa inserir uma senha");
            campoVazio = true;
        }

        if(campoVazio){
            return;
        }

        if(email.equals("flaviosantos.ufra@gmail.com") && senha.equals("1234")) {
            Intent intent = new Intent(getApplicationContext(), MyActivitiesActivity.class);
            startActivity(intent);
        } else{
            erroTextView.setText("Endereço de e-mail ou senha incorretos.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remover actionbar
        getSupportActionBar().hide();

        botaoEntrar = findViewById(R.id.botaoEntrar);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar(view);
            }
        });

    }
}