package com.example.appmantenimientoalumno;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmantenimientoalumno.db.Alumno;

import java.util.List;

// AGREGADO: adapter completo del RecyclerView, con soporte para Editar y Eliminar por fila
public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {

    private List<Alumno> listaAlumnos;
    private final OnAlumnoClickListener listener;

    // AGREGADO: interfaz para comunicar los clics de editar/eliminar hacia quien use el adapter (MainActivity)
    public interface OnAlumnoClickListener {
        void onEditar(Alumno alumno);
        void onEliminar(Alumno alumno);
    }

    public AlumnoAdapter(List<Alumno> listaAlumnos, OnAlumnoClickListener listener) {
        this.listaAlumnos = listaAlumnos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // AGREGADO: infla el nuevo layout item_alumno.xml para cada fila
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alumno, parent, false);
        return new AlumnoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        Alumno alumno = listaAlumnos.get(position);
        holder.tvNombre.setText(alumno.getNombre());
        holder.tvTelefono.setText(alumno.getTelefono());
        holder.tvCorreo.setText(alumno.getCorreoElectronico());

        // AGREGADO: al tocar los botones, se notifica a quien implementó el listener (MainActivity)
        holder.btnEditar.setOnClickListener(v -> listener.onEditar(alumno));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(alumno));
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    // AGREGADO: método para refrescar la lista después de insertar, editar o eliminar un alumno
    public void actualizarLista(List<Alumno> nuevaLista) {
        this.listaAlumnos = nuevaLista;
        notifyDataSetChanged();
    }

    static class AlumnoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTelefono, tvCorreo;
        ImageButton btnEditar, btnEliminar;

        public AlumnoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvCorreo = itemView.findViewById(R.id.tvCorreo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}