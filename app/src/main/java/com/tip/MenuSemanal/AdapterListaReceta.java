package com.tip.MenuSemanal;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;
import Clases.Recetas;


public class AdapterListaReceta extends RecyclerView.Adapter<AdapterListaReceta.ViewHoldersDatos>  {

    ArrayList<Recetas> ListaRecetas;

    public AdapterListaReceta(@NonNull ArrayList<Recetas> listaRecetas) {
        ListaRecetas = listaRecetas;
    }

    @NonNull
    @NotNull
    @Override


    public AdapterListaReceta.ViewHoldersDatos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view= LayoutInflater.from(parent.getContext()).inflate(R.id.listaRecetas,null,false);

        return new ViewHoldersDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterListaReceta.ViewHoldersDatos holder, int position) {
        holder.asignardatos(ListaRecetas.get(position).getNombre(),ListaRecetas.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return ListaRecetas.size();
    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        TextView txtnombre;
        TextView txtdescripcion;

        public ViewHoldersDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            txtnombre=itemView.findViewById(R.id.txtNombreReceta);
            txtdescripcion=itemView.findViewById(R.id.txtDescripcionReceta);
        }

        public void asignardatos(String nombre, String descripcion) {
            txtnombre.setText(nombre);
            txtdescripcion.setText(descripcion);
        }
    }
}
