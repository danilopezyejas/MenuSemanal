package com.tip.MenuSemanal.Adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;

import static androidx.navigation.Navigation.findNavController;

public class AdapterDatosIngredientes extends RecyclerView.Adapter<AdapterDatosIngredientes.ViewHolderDatos> {

    FloatingActionButton btnAgregarIngrediente;
    ArrayList<Ingrediente> listaIngredientes;
    boolean multiselect = false;
    int posant =-1;
    ArrayList<Ingrediente> ingreBorrar;
    Context context;
    int borrar = 0;

    public AdapterDatosIngredientes(ArrayList<Ingrediente> listaIngredientes, FloatingActionButton btnAgregarIngrediente, Context context){
        this.btnAgregarIngrediente = btnAgregarIngrediente;
        this.listaIngredientes = listaIngredientes;
        this.context = context;
        ingreBorrar = new ArrayList<>();
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
        Ingrediente ingreSelec;
        ingreSelec = listaIngredientes.get(position);

        String nombre = listaIngredientes.get(position).getNombre();
        float precio = listaIngredientes.get(position).getPrecio();
        String unidad = listaIngredientes.get(position).getUnidad();
        int cantidad = listaIngredientes.get(position).getCantidad();
        holder.nombre.setText(nombre);
        holder.precio.setText(Float.toString(precio));
        holder.unidad.setText(unidad);
        holder.cantidad.setText(Integer.toString(cantidad));

        holder.asignardatos(listaIngredientes.get(position));

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(multiselect==true) {
                    ingreSelec.setSel(!ingreSelec.getSel());
       //Guardo los ingredientes que quiero borrar
                    ingreBorrar.add(ingreSelec);
                }
                else{
                    if (posant >-1){
                        listaIngredientes.get(posant).setSel(false);
                        notifyItemChanged(posant);
                    }
                    ingreSelec.setSel(true);
                    posant=position;

                }
                notifyItemChanged(position);
                Bundle arg = new Bundle();

                if (!multiselect) {
                    arg.putString("paramNom", listaIngredientes.get(position).getNombre());
                    arg.putString("paramPre", String.valueOf(listaIngredientes.get(position).getPrecio()));
                    arg.putString("paramUni", listaIngredientes.get(position).getUnidad());
                    arg.putString("paramCant", String.valueOf(listaIngredientes.get(position).getCantidad()));
                    findNavController(holder.itemView).navigate(R.id.agregarIngredientes, arg);
                }
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (multiselect == true) {
                    for (Ingrediente ingre : listaIngredientes) {
                        ingre.setSel(false);
                    }
                }
                ingreSelec.setSel(true);
                posant=position;
                notifyDataSetChanged();
                multiselect = !multiselect;

//Guardo los ingredientes que quiero borrar
                ingreBorrar.add(ingreSelec);

//Cambio el icono del boton para poder borrar
                btnAgregarIngrediente.setImageResource(R.drawable.ic_borrar);
                btnAgregarIngrediente.setTag(R.drawable.ic_borrar);
                return true;
            }
        });

        if(ingreSelec.getSel()==true){
            if (multiselect == true)
                holder.item.setBackgroundColor(Color.GRAY);
            else
                holder.item.setBackgroundColor(Color.rgb(187,134,252));
        } else
            holder.item.setBackgroundColor(Color.TRANSPARENT);


    }

    @Override
    public int getItemCount() { return listaIngredientes.size(); }

    public ArrayList<Ingrediente> getIngreBorrar() {
        return ingreBorrar;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        public View item;
        TextView nombre;
        TextView precio;
        TextView unidad;
        TextView cantidad;
        
        public ViewHolderDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            this.item = itemView;
            nombre = (TextView) itemView.findViewById(R.id.idNomIngr);
            precio = (TextView) itemView.findViewById(R.id.idPrecio);
            unidad = (TextView) itemView.findViewById(R.id.idUnidad);
            cantidad = (TextView) itemView.findViewById(R.id.idCantidad);

        }

        public void asignardatos(Ingrediente seleccion) {
            nombre.setText( seleccion.getNombre());
            precio.setText(String.valueOf(seleccion.getPrecio()));
            unidad.setText(seleccion.getUnidad());
            cantidad.setText(String.valueOf(seleccion.getCantidad()));
        }

    }
}
