package com.tip.MenuSemanal;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;
import Clases.Recetas;

import static androidx.core.content.ContextCompat.getCodeCacheDir;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.tip.MenuSemanal.R.color.purple_200;
import static com.tip.MenuSemanal.R.color.purple_700;


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
        for(Ingrediente ing: listaIngrediente){
            if(ing.getSel()==true){remove(ing);
            }

        }
    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        TextView edCantidad;
        TextView acNombre;
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
            //chk = itemView.findViewById(R.id.chkestado);

        }

        @SuppressLint("ResourceAsColor")

        public void asignardatos(@NotNull Ingrediente ingrediente, int position) {
            acNombre.setText(ingrediente.getNombre());
            edCantidad.setText(Integer.toString(ingrediente.getCantidad()));
            txtUnidad.setText(ingrediente.getUnidad());
            posicion = position;
//            chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                }
//            });


        }
    }
}
