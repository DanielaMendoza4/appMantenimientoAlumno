package com.example.appmantenimientoalumno.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {
    // definimos variables para controlar los cambios en la base de datos
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "senati.db";
    public static final String TABLE_CONCTACTOS = "alumnos";

    // Constructor DbHelper
    public Dbhelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    // Evento que se ejecuta para crear la base de datos
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CONCTACTOS + "(" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nombre TEXT NOT NULL," +
                " telefono TEXT NOT NULL," +
                " correo_electronico TEXT)");
    }

    // Evento que se ejecuta cuando cambia la version de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // primero eliminamos la tabla que tenemos y luego agregamos una nueva tabla
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CONCTACTOS);
        onCreate(sqLiteDatabase);
    }
}