package com.tip.MenuSemanal;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import static androidx.navigation.Navigation.findNavController;
import static com.tip.MenuSemanal.R.color.*;
import static com.tip.MenuSemanal.R.drawable.fondo_botones;
import static com.tip.MenuSemanal.R.id.textView;


public class AdapterListaReceta extends RecyclerView.Adapter<AdapterListaReceta.ViewHoldersDatos> {

    ArrayList<Recetas> listaRecetas;
    View viewant = null;
    int posant =-1;
    boolean multiselect = false;
    public AdapterListaReceta(@NonNull ArrayList<Recetas> lRecetas) {
        listaRecetas = lRecetas;
    }

    @NonNull
    @NotNull
    @Override


    public AdapterListaReceta.ViewHoldersDatos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_recetas, parent, false);
        return new ViewHoldersDatos(view);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterListaReceta.ViewHoldersDatos holder, int position) {
        //holder.asignardatos(listaRecetas.get(position).getNombre(),listaRecetas.get(position).getDescripcion());
        Recetas receta ;
        holder.asignardatos(listaRecetas.get(position));

        receta = listaRecetas.get(position);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(multiselect==true)
                    receta.setSel(!receta.getSel());
                else{
                    if (posant >-1){
                        listaRecetas.get(posant).setSel(false);
                        notifyItemChanged(posant);
                    }
                    receta.setSel(true);
                    posant=position;

                }
                notifyItemChanged(position);
                Bundle arg = new Bundle();

                if (!multiselect) {
                    arg.putString("param1", receta.getNombre());
                    findNavController(holder.itemView).navigate(R.id.fragmentAgregarReceta, arg);
                }
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (multiselect == true) {
                    for (Recetas rec : listaRecetas) {
                        rec.setSel(false);

                    }
                }
                receta.setSel(true);
                posant=position;
                notifyDataSetChanged();
                multiselect = !multiselect;
                return true;
            }
        });

        if(receta.getSel()==true){
            if (multiselect == true)
                holder.item.setBackgroundColor(Color.GRAY)                                                                                              ;
            else
                holder.item.setBackgroundColor(purple_200);
        } else
            holder.item.setBackgroundColor(Color.TRANSPARENT);




    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Recetas receta) {
        listaRecetas.add(position, receta);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Recetas receta) {
        int position = listaRecetas.indexOf(receta);
        if(position>=0) {
            listaRecetas.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void removeSelected (){
        ArrayList<Recetas> elim =new ArrayList<Recetas>();
        for(Recetas ing: listaRecetas){
            if(ing.getSel()==true){
                elim.add(ing);
            }
        }
        if(elim.size() > 0) {
            listaRecetas.removeAll(elim);
            notifyDataSetChanged();
        }
    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        TextView txtnombre;
        TextView txtdescripcion;
        public View item;


        public ViewHoldersDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            this.item= itemView;
            txtnombre=itemView.findViewById(R.id.txtNombreReceta);
            txtdescripcion=itemView.findViewById(R.id.txtDescripcionReceta);


        }

//        public void asignardatos(String nombre, String descripcion) {
//            txtnombre.setText(nombre);
//            txtdescripcion.setText(descripcion);
//        }
        public void asignardatos(Recetas receta) {
            txtnombre.setText(receta.getNombre());
            txtdescripcion.setText(receta.getDescripcion());
        }
    }
}
