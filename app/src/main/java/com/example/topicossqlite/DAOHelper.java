package com.example.topicossqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DAOHelper extends SQLiteOpenHelper {

    private static String BD = "BD_Test1";
    private static String TABLA = "PERSONAS";

    public DAOHelper(@Nullable Context context,) {
        super(context, BD, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearTabla = "CREATE TABLE " + TABLA + "(ID_PER INTEGER PRIMARY KEY, NOM_PERSONA TEXT, APE_PER TEXT, EDA_PER INTEGER)";
        db.execSQL(crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " +TABLA);
    }
}
