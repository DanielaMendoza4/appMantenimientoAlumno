package com.example.appmantenimientoalumno.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

public class alumnos extends Dbhelper {
    Context context;

    // creamos el constructor
    public alumnos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    // creamos un metodo
    public long insertarContactos(String nombre, String telefono, String correo_electronico){
        long id = 0;
        try {
            Dbhelper dbHelper = new Dbhelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // agregar la funcion insertar los registros
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("correo_electronico", correo_electronico);

            // nos va regresar el id insertado
            id = db.insert(TABLE_CONCTACTOS, null, values);

        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }
}