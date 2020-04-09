package com.example.topicossqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DAOHelper extends SQLiteOpenHelper {

    private static String BD = "BD_Test1";
    private static String TABLA = "PERSONAS";

    public DAOHelper(@Nullable Context context) {
        super(context, BD, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearTabla = "CREATE TABLE " + TABLA + "(ID_PER INTEGER PRIMARY KEY, NOM_PER TEXT, APE_PER TEXT, EDA_PER INTEGER)";
        db.execSQL(crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " +TABLA);
    }

    public Persona buscarPersona (int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM " +TABLA +" WHERE ID_PER=" +ID,null);
        Persona p =null;
        if(datos.moveToNext()){
            p = new Persona();
            p.setNombre(datos.getString(1));
            p.setApellido(datos.getString(2));
            p.setEdad(datos.getInt(3));
        }
        db.close();
        return p;
    }

    public long agregarPersona(Persona p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ID_PER", p.getID());
        cv.put("NOM_PER", p.getNombre());
        cv.put("APE_PER", p.getApellido());
        cv.put("EDA_PER", p.getEdad());
        long res = db.insert(TABLA,null,cv);
        db.close();
        return res;
    }

}
