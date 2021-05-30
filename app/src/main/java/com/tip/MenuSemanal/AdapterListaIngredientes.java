package com.tip.MenuSemanal;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;
import Clases.Recetas;

import static com.tip.MenuSemanal.R.color.purple_200;


public class AdapterListaIngredientes extends RecyclerView.Adapter<AdapterListaIngredientes.ViewHoldersDatos> {

    ArrayList<Ingrediente> listaIngrediente;
    View viewant = null;
    public AdapterListaIngredientes(@NonNull ArrayList<Ingrediente> lingrediente) {
        listaIngrediente=lingrediente;
    }

    @NonNull
    @NotNull
    @Override


    public AdapterListaIngredientes.ViewHoldersDatos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_lista_ingredientes, parent, false);
        return new ViewHoldersDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterListaIngredientes.ViewHoldersDatos holder, int position) {
        holder.asignardatos(listaIngrediente.get(position));

        holder.item.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                System.out.println("click");
                if (holder.item!=viewant){
                        System.out.println("set on");

                            holder.item.setBackgroundColor (purple_200);
                             //holder.itemView.setAlpha(0.5f);
                        if (viewant != null){

                            //viewant.setAlpha(1f);

                              viewant.setBackgroundColor(Color.TRANSPARENT);
                        }
                        viewant=holder.item;
                    }
                   notifyDataSetChanged();
                }



        });

    }

    @Override
    public int getItemCount() {
        return listaIngrediente.size();
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Ingrediente ingrediente) {
        listaIngrediente.add(position, ingrediente);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Ingrediente ingrediente) {
        int position = listaIngrediente.indexOf(ingrediente);
        if(position>=0) {
            listaIngrediente.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        EditText edCantidad;
        AutoCompleteTextView acNombre;
        Spinner spUnidad;
        View item;

        public ViewHoldersDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            this.item= itemView;
            acNombre=itemView.findViewById(R.id.ACnombre);
            edCantidad=itemView.findViewById(R.id.EdCantidad);
            spUnidad=itemView.findViewById(R.id.SpUnidad);
        }


        public void asignardatos(Ingrediente ingrediente) {
            acNombre.setText(ingrediente.getNombre());
            edCantidad.setText(Integer.toString(ingrediente.getCantidad()));


        }
    }
}
