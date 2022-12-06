package com.example.gerenciadordeatividadescomplementares.Database;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.gerenciadordeatividadescomplementares.AtividadeComplementar.AtividadeComplementar;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabaseConnection extends DatabaseConection {
    private SQLiteDatabase db;

    public LocalDatabaseConnection(Context context) { // use with getContext()
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public boolean cadastrarAtividade(AtividadeComplementar atividadeComplementar) {
        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.FeedAtividades.COLUMN_NAME_ATIVIDADE, atividadeComplementar.getNome());
        values.put(FeedReaderContract.FeedAtividades.COLUMN_NAME_PERIODO , atividadeComplementar.getPeriodo());
        values.put(FeedReaderContract.FeedAtividades.COLUMN_NAME_CARGA_HORARIA, atividadeComplementar.getCargaHoraria());

        atividadeComplementar.setId(Long.toString(db.insert(FeedReaderContract.FeedAtividades.TABLE_NAME, null, values)));

        return true;
    }

    public boolean editarAtividade(String id, AtividadeComplementar atividadeComplementar) {
        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.FeedAtividades.COLUMN_NAME_ATIVIDADE, atividadeComplementar.getNome());
        values.put(FeedReaderContract.FeedAtividades.COLUMN_NAME_PERIODO , atividadeComplementar.getPeriodo());
        values.put(FeedReaderContract.FeedAtividades.COLUMN_NAME_CARGA_HORARIA, atividadeComplementar.getCargaHoraria());

        final String WHERE_CLAUSE = FeedReaderContract.FeedAtividades._ID + " = ?";

        db.update(FeedReaderContract.FeedAtividades.TABLE_NAME, values, WHERE_CLAUSE, new String[] {id});
        return true;
    }

    public boolean excluirAtividade(String id) {
        final String WHERE_CLAUSE = FeedReaderContract.FeedAtividades._ID + " = ?";
        db.delete(FeedReaderContract.FeedAtividades.TABLE_NAME, WHERE_CLAUSE, new String[] {id});
        return true;
    }

    public boolean inserirDadosDoAluno(String id) {
        return false;
    }

    public List<AtividadeComplementar> getAllAtividades() {
        String sortOrder = FeedReaderContract.FeedAtividades.COLUMN_NAME_ATIVIDADE + " ASC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedAtividades.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List atividadeComplementarList = new ArrayList<AtividadeComplementar>();
        while(cursor.moveToNext()) {
            AtividadeComplementar atividadeComplementar = new AtividadeComplementar();

            atividadeComplementar.setId(cursor.getString(cursor.getColumnIndexOrThrow(
                    FeedReaderContract.FeedAtividades._ID)));
            atividadeComplementar.setNome(cursor.getString(cursor.getColumnIndexOrThrow(
                    FeedReaderContract.FeedAtividades.COLUMN_NAME_ATIVIDADE)));
            atividadeComplementar.setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow(
                    FeedReaderContract.FeedAtividades.COLUMN_NAME_PERIODO)));
            atividadeComplementar.setCargaHoraria(cursor.getFloat(cursor.getColumnIndexOrThrow(
                    FeedReaderContract.FeedAtividades.COLUMN_NAME_CARGA_HORARIA)));

            atividadeComplementarList.add(atividadeComplementar);
        }
        cursor.close();

        return atividadeComplementarList;
    }
}

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GAC.db";
    public static final int DATABASE_VERSION = 1;


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedAtividades.TABLE_NAME + " (" +
                    FeedReaderContract.FeedAtividades._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedAtividades.COLUMN_NAME_ATIVIDADE + " TEXT," +
                    FeedReaderContract.FeedAtividades.COLUMN_NAME_PERIODO + " TEXT," +
                    FeedReaderContract.FeedAtividades.COLUMN_NAME_CARGA_HORARIA + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedAtividades.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}

class FeedReaderContract {
    private FeedReaderContract() {}

    public static class FeedAtividades implements BaseColumns {
        public static final String TABLE_NAME = "atividades";
        public static final String COLUMN_NAME_ATIVIDADE = "atividade";
        public static final String COLUMN_NAME_PERIODO = "periodo";
        public static final String COLUMN_NAME_CARGA_HORARIA = "carga_horaria";
    }

    public static class FeedAluno implements BaseColumns {
        public static final String TABLE_NAME = "atividades";
        public static final String COLUMN_NAME_ATIVIDADE = "atividade";
        public static final String COLUMN_NAME_PERIODO = "periodo";
        public static final String COLUMN_NAME_CARGA_HORARIA = "carga_horaria";
    }
}
