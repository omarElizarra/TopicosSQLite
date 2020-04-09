package com.example.topicossqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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

    public int eliminarPersona(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLA,"ID_PER=?",new String[]{Integer.toString(ID)});
        db.close();
        return res;
    }

    public int modificarPersona(Persona p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ID_PER", p.getID());
        cv.put("NOM_PER", p.getNombre());
        cv.put("APE_PER", p.getApellido());
        cv.put("EDA_PER", p.getEdad());
        int res = db.update(TABLA,cv,"ID_PER=?", new String[]{Integer.toString(p.getID())});
        db.close();
        return res;
    }

    public  ArrayList<Persona> obtenerPersonal(){
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cambio por Writable
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM " +TABLA +" ORDER BY ID_PER ASC",null);
        ArrayList<Persona> lista = new ArrayList<Persona>();
        while (datos.moveToNext()){
            Persona p = new Persona();
            p.setID(datos.getInt(0));
            p.setNombre( datos.getString(1));
            p.setApellido(datos.getString(2));
            p.setEdad(datos.getInt(3));
            lista.add(p);
        }
        db.close();
        return lista;




    }

}
