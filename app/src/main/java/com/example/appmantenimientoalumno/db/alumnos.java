package com.example.appmantenimientoalumno.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class alumnos {

    private final Dbhelper dbHelper;

    public alumnos(Context context) {
        this.dbHelper = new Dbhelper(context);
    }

    public long insertarContactos(String nombre, String telefono, String correo_electronico) {
        long id = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("correo_electronico", correo_electronico);

            id = db.insert(Dbhelper.TABLE_CONTACTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        } finally {
            db.close();
        }
        return id;
    }

    public List<Alumno> obtenerTodos() {
        List<Alumno> listaAlumnos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.query(Dbhelper.TABLE_CONTACTOS, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Alumno alumno = new Alumno();
                    alumno.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    alumno.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    alumno.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow("telefono")));
                    alumno.setCorreoElectronico(cursor.getString(cursor.getColumnIndexOrThrow("correo_electronico")));
                    listaAlumnos.add(alumno);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception ex) {
            ex.toString();
        } finally {
            db.close();
        }
        return listaAlumnos;
    }

    public int obtenerSiguienteId() {
        int siguienteId = 1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT MAX(id) FROM " + Dbhelper.TABLE_CONTACTOS, null);
            if (cursor.moveToFirst()) {
                int maxId = cursor.getInt(0);
                if (maxId > 0) {
                    siguienteId = maxId + 1;
                }
            }
            cursor.close();
        } catch (Exception ex) {
            ex.toString();
        } finally {
            db.close();
        }
        return siguienteId;
    }

    public Alumno buscarPorId(int id) {
        Alumno alumno = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.query(
                Dbhelper.TABLE_CONTACTOS,
                null,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
            );
            if (cursor.moveToFirst()) {
                alumno = new Alumno();
                alumno.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                alumno.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                alumno.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow("telefono")));
                alumno.setCorreoElectronico(cursor.getString(cursor.getColumnIndexOrThrow("correo_electronico")));
            }
            cursor.close();
        } catch (Exception ex) {
            ex.toString();
        } finally {
            db.close();
        }
        return alumno;
    }

    public int importarCSV(List<Alumno> alumnos) {
        int registrosImportados = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (Alumno alumno : alumnos) {
                ContentValues values = new ContentValues();
                values.put("id", alumno.getId());
                values.put("nombre", alumno.getNombre());
                values.put("telefono", alumno.getTelefono());
                values.put("correo_electronico", alumno.getCorreoElectronico());

                long id = db.insert(Dbhelper.TABLE_CONTACTOS, null, values);
                if (id > 0) {
                    registrosImportados++;
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.toString();
        } finally {
            db.endTransaction();
            db.close();
        }
        return registrosImportados;
    }
}
