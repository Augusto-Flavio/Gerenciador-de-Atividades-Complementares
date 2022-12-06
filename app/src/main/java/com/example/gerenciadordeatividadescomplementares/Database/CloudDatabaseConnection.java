package com.example.gerenciadordeatividadescomplementares.Database;


import android.util.Log;

import com.example.gerenciadordeatividadescomplementares.AtividadeComplementar.AtividadeComplementar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CloudDatabaseConnection extends DatabaseConection {
    private DatabaseReference rootReference;
    private DatabaseReference atividadesReference;
    private List<AtividadeComplementar> atividadeComplementarList;

    public CloudDatabaseConnection() {
        rootReference = FirebaseDatabase.getInstance().getReference();
        atividadesReference = rootReference.child("atividades");

        atividadeComplementarList = new ArrayList<>();
        atividadesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                atividadeComplementarList.clear();
                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()){
                    AtividadeComplementar atividadeComplementar = categorySnapshot.getValue(AtividadeComplementar.class);
                    atividadeComplementarList.add(atividadeComplementar);
                    Log.d(this.getClass().toString(), "Nome da atividade complementar: " + atividadeComplementar.getNome());
                    Log.d(this.getClass().toString(), "Id da atividade complementar: " + atividadeComplementar.getId());
                }
                Log.d(this.getClass().toString(), "List size: " + atividadeComplementarList.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(this.getClass().toString(), databaseError.getMessage());
            }
        });
    }

    public boolean cadastrarAtividade(AtividadeComplementar atividadeComplementar) {
        Log.d(this.getClass().toString(), "Atividade cadastrada");
        String key = atividadesReference.push().getKey();
        atividadeComplementar.setId(key);
        atividadesReference.child(key).setValue(atividadeComplementar);
        return true;
    }

    public boolean editarAtividade(String id, AtividadeComplementar atividadeComplementar) {
        Log.d(this.getClass().toString(), "Atividade editada");
        String key = atividadesReference.child(id).getKey();
        atividadesReference.child(key).setValue(atividadeComplementar);
        return true;
    }

    public boolean excluirAtividade(String id) {
        Log.d(this.getClass().toString(), "Atividade excluída");
        atividadesReference.child(id).removeValue();
        return true;
    }

    public boolean inserirDadosDoAluno(String id) {
        return false;
    }

    public List<AtividadeComplementar> getAllAtividades() {
        return atividadeComplementarList;
    }

}
