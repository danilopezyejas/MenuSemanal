package com.tip.MenuSemanal;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
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

import static com.tip.MenuSemanal.R.color.*;
import static com.tip.MenuSemanal.R.id.listaRecetas;
import static com.tip.MenuSemanal.R.id.textView;


public class AdapterListaReceta extends RecyclerView.Adapter<AdapterListaReceta.ViewHoldersDatos> implements View.OnClickListener {

    ArrayList<Recetas> listaRecetas;
    View.OnClickListener onclickl;

    public AdapterListaReceta(@NonNull ArrayList<Recetas> lRecetas) {
        listaRecetas = lRecetas;
    }

    @NonNull
    @NotNull
    @Override


    public AdapterListaReceta.ViewHoldersDatos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_recetas, null, false);

        return new ViewHoldersDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterListaReceta.ViewHoldersDatos holder, int position) {
        holder.asignardatos(listaRecetas.get(position).getNombre(),listaRecetas.get(position).getDescripcion());
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }

    @Override
    public void onClick(View view) {


    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        TextView txtnombre;
        TextView txtdescripcion;
        View item;

        public ViewHoldersDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            this.item= itemView;
            txtnombre=itemView.findViewById(R.id.txtNombreReceta);
            txtdescripcion=itemView.findViewById(R.id.txtDescripcionReceta);

        }
        public void bindView(){

                item.setSelected(true);
            }

        public void asignardatos(String nombre, String descripcion) {
            txtnombre.setText(nombre);
            txtdescripcion.setText(descripcion);



        }
    }
}
