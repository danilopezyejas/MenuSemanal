package com.tip.MenuSemanal.Adaptadores;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.MainActivity;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;
import Enumeracion.unidades;

import static com.tip.MenuSemanal.R.color.purple_200;



public class AdapterListaIngredientes extends RecyclerView.Adapter<AdapterListaIngredientes.ViewHoldersDatos> {

    ArrayList<Ingrediente> listaIngrediente;
    ArrayAdapter<String> listaAdapter;
    ArrayList<String> lista = new ArrayList<String>();
    View viewant = null;
    boolean multiselect = false;
    int posant =-1;


    public AdapterListaIngredientes(@NonNull ArrayList<Ingrediente> lingrediente) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Ingredientes");

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ingredientesDataSnap : snapshot.getChildren())
                    lista.add(ingredientesDataSnap.child("nombre").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

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

                if(multiselect==true){
                    ing.setSel(!ing.getSel());
//                else{
//                    if (posant >-1){
//                        listaIngrediente.get(posant).setSel(false);
//                        notifyItemChanged(posant);
//                    }
//                    ing.setSel(true);
//                    posant=position;
//
//                }
                notifyItemChanged(position);
            }}
        });



        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                multiselect = !multiselect;
                if (!multiselect) {
                    for (Ingrediente ingr : listaIngrediente) {
                        ingr.setSel(false);

                    }
                }else
                  ing.setSel(true);
//                posant=position;
               notifyDataSetChanged();
                return true;
            }
        });

        if(ing.getSel()==true){
           // if (multiselect == true)
                holder.item.setBackgroundColor(Color.GRAY)                                                                                              ;
//            else
//                holder.item.setBackgroundColor(purple_200);
        } else
            holder.item.setBackgroundColor(Color.TRANSPARENT);


    }

    @Override
    public int getItemCount() {
        return listaIngrediente.size();
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
        multiselect= false;
    }

    public void nuevoingrediente(){
        multiselect = false;
        listaIngrediente.add (new Ingrediente("","",0f,0, unidades.GR.toString()));
        notifyDataSetChanged();
    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        EditText edCantidad;
        AutoCompleteTextView acNombre;
        Spinner spinUnidad ;
        CheckBox chk;
        View item;
        int posicion;

        public ViewHoldersDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            this.item = itemView;
            acNombre = itemView.findViewById(R.id.ACnombre);
            edCantidad = itemView.findViewById(R.id.EdCantidad);
            spinUnidad = itemView.findViewById(R.id.spinUnidad);
            spinUnidad = (Spinner) itemView.findViewById(R.id.spinUnidad);
            ArrayAdapter<unidades> u = new ArrayAdapter<unidades>(itemView.getContext(), android.R.layout.simple_spinner_item, unidades.values());
            spinUnidad.setAdapter(u);


        }

        @SuppressLint("ResourceAsColor")

        public void asignardatos(@NotNull Ingrediente ingrediente, int position) {
            acNombre.setText(ingrediente.getNombre());
            edCantidad.setText(Integer.toString(ingrediente.getCantidad()));
            spinUnidad.setSelection(getSpinnerIndex(spinUnidad,ingrediente.getUnidad()));
            posicion = position;
            acNombre.setAdapter(new ArrayAdapter<String>( item.getContext(),android.R.layout.simple_dropdown_item_1line,lista));

            acNombre.setThreshold(2);


            acNombre.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction()== MotionEvent.ACTION_UP && multiselect ) {
                        item.performClick();

                    }
                    return false;
                }
            });

            acNombre.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                        Toast.makeText(item.getContext(),"lc",Toast.LENGTH_SHORT).show();
                        item.performLongClick();
                    //multiselect= true;
                    return false;
                }
            });

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
                    if (!b) {
                        ingrediente.setNombre(((AutoCompleteTextView) view).getText().toString());
                    }
                }
            });


            spinUnidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ingrediente.setUnidad(spinUnidad.getSelectedItem().toString());

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


    }
    private int getSpinnerIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return -1;
    }

}