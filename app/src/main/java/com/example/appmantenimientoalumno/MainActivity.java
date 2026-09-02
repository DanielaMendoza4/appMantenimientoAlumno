package com.example.appmantenimientoalumno;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmantenimientoalumno.db.Alumno;
import com.example.appmantenimientoalumno.db.alumnos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAlumnos;
    private AlumnoAdapter adapter;
    private TextView tvVacio;
    private List<Alumno> listaAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerViewAlumnos = findViewById(R.id.recyclerViewAlumnos);
        tvVacio = findViewById(R.id.tvVacio);
        FloatingActionButton fabAgregar = findViewById(R.id.fabAgregar);

        listaAlumnos = new ArrayList<>();

        // CAMBIADO: el adapter ahora recibe un listener para manejar Editar y Eliminar de cada fila
        adapter = new AlumnoAdapter(listaAlumnos, new AlumnoAdapter.OnAlumnoClickListener() {
            @Override
            public void onEditar(Alumno alumno) {
                // AGREGADO: abre NuevoActivity enviando el ID del alumno, para que cargue sus datos y permita editarlos
                Intent intent = new Intent(MainActivity.this, NuevoActivity.class);
                intent.putExtra("ALUMNO_ID", alumno.getId());
                startActivity(intent);
            }

            @Override
            public void onEliminar(Alumno alumno) {
                // AGREGADO: diálogo de confirmación antes de eliminar el registro
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Eliminar alumno")
                        .setMessage("¿Seguro que deseas eliminar a " + alumno.getNombre() + "?")
                        .setPositiveButton("Eliminar", (dialog, which) -> {
                            alumnos dbAlumnos = new alumnos(MainActivity.this);
                            dbAlumnos.eliminarAlumno(alumno.getId());
                            cargarLista();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        recyclerViewAlumnos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAlumnos.setAdapter(adapter);

        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NuevoActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
    }

    private void cargarLista() {
        alumnos dbAlumnos = new alumnos(this);
        listaAlumnos = dbAlumnos.obtenerTodos();
        adapter.actualizarLista(listaAlumnos);

        if (listaAlumnos.isEmpty()) {
            tvVacio.setVisibility(View.VISIBLE);
            recyclerViewAlumnos.setVisibility(View.GONE);
        } else {
            tvVacio.setVisibility(View.GONE);
            recyclerViewAlumnos.setVisibility(View.VISIBLE);
        }
    }
}