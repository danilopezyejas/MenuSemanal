package com.tip.MenuSemanal.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;

public class AdapterDatosNotificacion extends RecyclerView.Adapter<AdapterDatosNotificacion.ViewHolderDatos> {

    private ArrayList<Ingrediente> listaIngredientes;
    private Context context;

    public AdapterDatosNotificacion(ArrayList<Ingrediente> listaIngredientes,Context context) {
        this.listaIngredientes = listaIngredientes;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_notificaciones ,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderDatos holder, int position) {

        String nombre = listaIngredientes.get(position).getNombre();
        int cantidad = listaIngredientes.get(position).getCantidad();

        holder.nombre.setText(nombre);
        holder.cantidad.setText(Integer.toString(cantidad));

    }

    @Override
    public int getItemCount() {
        return listaIngredientes.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        private TextView nombre;
        private TextView cantidad;

        public ViewHolderDatos(@NonNull @NotNull View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.nomIgreNotificacion);
            cantidad = (TextView) itemView.findViewById(R.id.cantIngreNotificacion);
        }
    }
}
