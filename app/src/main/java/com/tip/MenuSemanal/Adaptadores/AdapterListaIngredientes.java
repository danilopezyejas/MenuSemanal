package com.tip.MenuSemanal.Adaptadores;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;
import Enumeracion.unidades;

import static com.tip.MenuSemanal.R.color.purple_200;



public class AdapterListaIngredientes extends RecyclerView.Adapter<AdapterListaIngredientes.ViewHoldersDatos> {

    ArrayList<Ingrediente> listaIngrediente;
    View viewant = null;
    boolean multiselect = false;
    int posant =-1;
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


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterListaIngredientes.ViewHoldersDatos holder, int position) {

        Ingrediente ing = listaIngrediente.get(position);

        holder.asignardatos(listaIngrediente.get(position),position);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(multiselect==true)
                    ing.setSel(!ing.getSel());
                else{
                    if (posant >-1){
                        listaIngrediente.get(posant).setSel(false);
                        notifyItemChanged(posant);
                    }
                    ing.setSel(true);
                    posant=position;

                }
                notifyItemChanged(position);
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (multiselect == true) {
                    for (Ingrediente ingr : listaIngrediente) {
                        ingr.setSel(false);

                    }
                }
                ing.setSel(true);
                posant=position;
                notifyDataSetChanged();
                multiselect = !multiselect;
                return true;
            }
        });

        if(ing.getSel()==true){
            if (multiselect == true)
                holder.item.setBackgroundColor(Color.GRAY)                                                                                              ;
            else
                holder.item.setBackgroundColor(purple_200);
        } else
            holder.item.setBackgroundColor(Color.TRANSPARENT);


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

    public void removeSelected (){
        ArrayList<Ingrediente> elim =new ArrayList<Ingrediente>();
        for(Ingrediente ing: listaIngrediente){
            if(ing.getSel()==true){
                elim.add(ing);
            }
        }
        if(elim.size() > 0) {
            listaIngrediente.removeAll(elim);
            notifyDataSetChanged();
        }
    }

    public void nuevoingrediente(){
        listaIngrediente.add (new Ingrediente("","",0f,0, unidades.GR.toString()));
        notifyDataSetChanged();
    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        EditText edCantidad;
        AutoCompleteTextView acNombre;
        TextView txtUnidad;
        CheckBox chk;
        View item;
        int posicion;

        public ViewHoldersDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            this.item = itemView;
            acNombre = itemView.findViewById(R.id.ACnombre);
            edCantidad = itemView.findViewById(R.id.EdCantidad);
            txtUnidad = itemView.findViewById(R.id.txtUnidad);


        }

        @SuppressLint("ResourceAsColor")

        public void asignardatos(@NotNull Ingrediente ingrediente, int position) {
            acNombre.setText(ingrediente.getNombre());
            edCantidad.setText(Integer.toString(ingrediente.getCantidad()));
            txtUnidad.setText(ingrediente.getUnidad());
            posicion = position;

            edCantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(!b)
                    try {
                        ingrediente.setCantidad(Integer.parseInt(((EditText) view).getText().toString()));
                    } catch (NumberFormatException e) {
                        ingrediente.setCantidad(0);
                    }
                }
            });


            acNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b)
                        //if (!listaIngrediente.get(position).getNombre().equals(acNombre.getText().toString())) {
                        ingrediente.setNombre( ((AutoCompleteTextView)view).getText().toString());
                    //}
                    //notifyItemChanged(position);

                }
            });
        }

    }
}