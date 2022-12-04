package com.example.gerenciadordeatividadescomplementares.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.gerenciadordeatividadescomplementares.Fragments.EnsinoFragment;
import com.example.gerenciadordeatividadescomplementares.Fragments.ExtensaoFragment;
import com.example.gerenciadordeatividadescomplementares.Fragments.GestaoFragment;
import com.example.gerenciadordeatividadescomplementares.Fragments.PesquisaFragment;
import com.example.gerenciadordeatividadescomplementares.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MyActivitiesActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SmartTabLayout viewPagerTab;
    private FloatingActionButton fab;

    private String CHANNEL_ID = "CHANNEL ID";

    //Método que cria um canal de notificações necessário para criar uma notificação
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Canal_GAC", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Canal de notificações do Gerenciador de Atividades Complementares");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //Método que cria a notificação
    private void createNotification() {
        Intent intent = new Intent(this, DocumentsActivity.class);
        intent.putExtra("Dados", "Documento.pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_save_alt_24)
                .setContentTitle("Documento gerado com sucesso.")
                .setContentText("Toque aqui para abrir")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, builder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activities);

        createNotificationChannel();

        // Remover sombra da ActionBar
        getSupportActionBar().setElevation(0);

        // Mudar título da ActionBar
        getSupportActionBar().setTitle("Minhas Atividades");

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Pesquisa", PesquisaFragment.class)
                .add("Extensão", ExtensaoFragment.class)
                .add("Ensino", EnsinoFragment.class)
                .add("Gestão", GestaoFragment.class)
                .create());

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        viewPagerTab = findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
            }
        });
    }
}