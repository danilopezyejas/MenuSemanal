package com.tip.MenuSemanal.Adaptadores;

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

public class AdapterDatosIngredientes extends RecyclerView.Adapter<AdapterDatosIngredientes.ViewHolderDatos> {

    ArrayList<Ingrediente> listaIngredientes;

    public AdapterDatosIngredientes(ArrayList<Ingrediente> listaIngredientes){
        this.listaIngredientes = listaIngredientes;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_ingredientes ,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderDatos holder, int position) {
        String nombre = listaIngredientes.get(position).getNombre();
        float precio = listaIngredientes.get(position).getPrecio();
        String unidad = listaIngredientes.get(position).getUnidad();
        int cantidad = listaIngredientes.get(position).getCantidad();
        holder.nombre.setText(nombre);
        holder.precio.setText(Float.toString(precio));
        holder.unidad.setText(unidad);
        holder.cantidad.setText(Integer.toString(cantidad));
    }

    @Override
    public int getItemCount() { return listaIngredientes.size(); }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView precio;
        TextView unidad;
        TextView cantidad;
        
        public ViewHolderDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.idNomIngr);
            precio = (TextView) itemView.findViewById(R.id.idPrecio);
            unidad = (TextView) itemView.findViewById(R.id.idUnidad);
            cantidad = (TextView) itemView.findViewById(R.id.idCantidad);

        }
    }
}
