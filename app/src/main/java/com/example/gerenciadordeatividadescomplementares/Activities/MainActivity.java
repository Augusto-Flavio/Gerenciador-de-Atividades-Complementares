package com.example.gerenciadordeatividadescomplementares.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerenciadordeatividadescomplementares.AtividadeComplementar.AtividadeComplementar;
import com.example.gerenciadordeatividadescomplementares.AtividadeComplementar.AtividadeComplementar.Tipo;
import com.example.gerenciadordeatividadescomplementares.Database.CloudDatabaseConnection;
import com.example.gerenciadordeatividadescomplementares.Database.DatabaseConection;
import com.example.gerenciadordeatividadescomplementares.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button botaoEntrar;
    private Button botaoCadastrese;
    private FirebaseAuth firebaseAuth;

    private void logar(){
        boolean campoVazio = false;

        EditText emailTextInput = findViewById(R.id.emailTextInput);
        EditText senhaTextInput = findViewById(R.id.senhaTextInput);
        TextView erroTextView = findViewById(R.id.erroTextView);

        String email = emailTextInput.getText().toString();
        String senha = senhaTextInput.getText().toString();
        erroTextView.setText("");
        erroTextView.setVisibility(View.GONE);

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

        firebaseAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MyActivitiesActivity.class);
                            startActivity(intent);
                            Log.d(TAG, "signInWithEmail:success");
                        } else {
                            Toast.makeText(getApplicationContext(), "Senha incorreta para esse usuário. Tente \"minhasenha123\".",
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "signInUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remover actionbar
        getSupportActionBar().hide();

        botaoEntrar = findViewById(R.id.botaoEntrar);
        botaoCadastrese = findViewById(R.id.botaoCadastrese);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

        botaoCadastrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        // Teste de conexão com a API de banco de dados
        DatabaseConection cloudDatabaseConnection = new CloudDatabaseConnection();

        AtividadeComplementar atividadeComplementar = new AtividadeComplementar("Diprominha", 10.0f, "201901", Tipo.ENSINO, "diprominha.pdf");
        cloudDatabaseConnection.cadastrarAtividade(atividadeComplementar);

        Log.d(this.getClass().toString(), Integer.toString(cloudDatabaseConnection.getAllAtividades().size()));

        atividadeComplementar.setNome("Dipromão");
        atividadeComplementar.setArquivo("dipromao.pdf");
        cloudDatabaseConnection.editarAtividade(atividadeComplementar.getId(), atividadeComplementar);
        cloudDatabaseConnection.excluirAtividade(atividadeComplementar.getId());
        Log.d(this.getClass().toString(), Integer.toString(cloudDatabaseConnection.getAllAtividades().size()));
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MyActivitiesActivity.class);
            startActivity(intent);
        }
    }
}