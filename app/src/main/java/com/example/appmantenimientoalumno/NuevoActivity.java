package com.example.appmantenimientoalumno;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appmantenimientoalumno.db.Alumno;
import com.example.appmantenimientoalumno.db.alumnos;
import com.google.android.material.appbar.MaterialToolbar;

public class NuevoActivity extends AppCompatActivity {

    private EditText txtId, txtnombre, txtTelefono, txtCorreoElectronico;
    private Button btnGuarda, btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nuevo);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuevoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtId = findViewById(R.id.txtId);
        txtnombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreoElectronico = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnBuscar = findViewById(R.id.btnBuscar);

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idTexto = txtId.getText().toString().trim();
                String nombre = txtnombre.getText().toString().trim();
                String telefono = txtTelefono.getText().toString().trim();
                String correo = txtCorreoElectronico.getText().toString().trim();

                if (idTexto.isEmpty()) {
                    txtId.setError("Ingrese el ID");
                    txtId.requestFocus();
                    return;
                }
                if (nombre.isEmpty()) {
                    txtnombre.setError("Ingrese el nombre");
                    txtnombre.requestFocus();
                    return;
                }
                if (telefono.isEmpty()) {
                    txtTelefono.setError("Ingrese el teléfono");
                    txtTelefono.requestFocus();
                    return;
                }

                alumnos dbalumnos = new alumnos(NuevoActivity.this);
                long id = dbalumnos.insertarContactos(nombre, telefono, correo);

                if (id > 0) {
                    Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                    Limpiar();
                } else {
                    Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarAlumno();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void buscarAlumno() {
        String idTexto = txtId.getText().toString().trim();

        if (idTexto.isEmpty()) {
            txtId.setError("Ingrese un ID");
            txtId.requestFocus();
            return;
        }

        int id = Integer.parseInt(idTexto);
        alumnos dbalumnos = new alumnos(this);
        Alumno alumno = dbalumnos.buscarPorId(id);

        if (alumno != null) {
            txtnombre.setText(alumno.getNombre());
            txtTelefono.setText(alumno.getTelefono());
            txtCorreoElectronico.setText(alumno.getCorreoElectronico());
            Toast.makeText(this, "ALUMNO ENCONTRADO", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ALUMNO NO ENCONTRADO", Toast.LENGTH_SHORT).show();
        }
    }

    private void Limpiar() {
        txtId.setText("");
        txtnombre.setText("");
        txtTelefono.setText("");
        txtCorreoElectronico.setText("");
    }
}
