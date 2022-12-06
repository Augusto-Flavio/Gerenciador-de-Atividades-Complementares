package com.example.gerenciadordeatividadescomplementares.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerenciadordeatividadescomplementares.Fragments.MapsFragment;
import com.example.gerenciadordeatividadescomplementares.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private Button botaoCadastrar;
    private Button botaoLocalizacao;
    private Button botaoCamera;
    private ImageView perfilImageView;
    private TextView cerroTextView;
    private MapsFragment mapsFragment;
    private FrameLayout frameMaps;

    private double latitude;
    private double longitude;
    private FirebaseAuth firebaseAuth;

    private void cadastrar(){
        boolean campoVazio = false;

        EditText cnomeTextInput = findViewById(R.id.cnomeTextInput);
        EditText cemailTextInput = findViewById(R.id.cemailTextInput);
        EditText csenhaTextInput = findViewById(R.id.csenhaTextInput);
        EditText confsenhaTextInput = findViewById(R.id.confsenhaTextInput);

        String nome = cnomeTextInput.getText().toString();
        String email = cemailTextInput.getText().toString();
        String senha = csenhaTextInput.getText().toString();
        String csenha = confsenhaTextInput.getText().toString();
        double[] coordenadas = {latitude, longitude};
        cerroTextView.setText("");
        cerroTextView.setVisibility(View.GONE);

        if(nome.equals("")){
            cnomeTextInput.setError("Você precisa inserir um nome");
            campoVazio = true;
        }

        if(email.equals("")){
            cemailTextInput.setError("Você precisa inserir um endereço de e-mail");
            campoVazio = true;
        }

        if(senha.equals("")){
            csenhaTextInput.setError("Você precisa inserir uma senha");
            campoVazio = true;
        }

        if(csenha.equals("")){
            confsenhaTextInput.setError("Este campo não pode ficar vazio");
            campoVazio = true;
        }

        if(campoVazio){
            return;
        }

        if(senha.equals(csenha)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle("Atenção");
            dialog.setMessage("Você deseja confirmar os dados?");

            dialog.setIcon(R.drawable.ic_baseline_warning_24);

            dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    firebaseAuth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        if (user != null) {
                                            FirebaseAuth.getInstance().signOut();
                                        }
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Usuário cadastrado com sucesso.",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            });
            dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            dialog.create();
            dialog.show();
        } else {
            cerroTextView.setVisibility(View.VISIBLE);
            cerroTextView.setText("As senhas não coincidem.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Ocultando ActionBar
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        botaoCadastrar = findViewById(R.id.botaoCadastrar);
        botaoLocalizacao = findViewById(R.id.botaoLocalizacao);
        botaoCamera = findViewById(R.id.botaoCamera);
        perfilImageView = findViewById(R.id.perfilImageView);
        cerroTextView = findViewById(R.id.cerroTextView);
        frameMaps = findViewById(R.id.frameMaps);

        botaoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tirarFoto();
            }
        });

        botaoLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mapsFragment = new MapsFragment();
                buscarInformacoesGPS(view);
                mapsFragment.setLatitude(latitude);
                mapsFragment.setLongitude(longitude);

                if(latitude != 0.0 && longitude != 0.0) {
                    if (frameMaps.getVisibility() == View.GONE) {
                        frameMaps.setVisibility(View.VISIBLE);
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameMaps, mapsFragment);
                    transaction.commit();
                }
            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

    }

    private void tirarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            perfilImageView.setImageBitmap(imagem);
            perfilImageView.setVisibility(View.VISIBLE);
            botaoCamera.setVisibility(View.GONE);
        }
    }

    public void buscarInformacoesGPS(View v) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)   != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(RegistrationActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(RegistrationActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(RegistrationActivity.this, new String[] {Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            return;
        }

        LocationManager mLocManager  = (LocationManager) getSystemService(RegistrationActivity.this.LOCATION_SERVICE);
        LocationListener mLocListener = new MinhaLocalizacaoListener();

        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);

        if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            String texto = "Latitude.: " + MinhaLocalizacaoListener.latitude + "\n" +
                    "Longitude: " + MinhaLocalizacaoListener.longitude + "\n";
            Toast.makeText(RegistrationActivity.this, texto, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(RegistrationActivity.this, "GPS DESABILITADO.", Toast.LENGTH_LONG).show();
        }

        this.latitude = MinhaLocalizacaoListener.latitude;
        this.longitude = MinhaLocalizacaoListener.longitude;
    }
}