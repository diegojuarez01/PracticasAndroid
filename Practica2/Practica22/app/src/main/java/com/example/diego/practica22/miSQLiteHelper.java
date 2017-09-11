package com.example.diego.practica22;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class miSQLiteHelper extends SQLiteOpenHelper {
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate = "CREATE TABLE Logins (_id integer primary key autoincrement,alias text not null, loginType integer not null," +
                        "nameOfApp text not null, urlApp text, imageURL text, user text not null," +
                        "password text not null, firstdate long)";
    public miSQLiteHelper(Context contexto, String nombre, CursorFactory
            factory, int version) {
        super(contexto, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionOld, int versionNew){
//Se elimina la versión anterior de la tabla
  //      db.execSQL("DROP TABLE IF EXISTS Logins");
//Se crea la nueva versión de la tabla
      //  db.execSQL(sqlCreate);
    }
}