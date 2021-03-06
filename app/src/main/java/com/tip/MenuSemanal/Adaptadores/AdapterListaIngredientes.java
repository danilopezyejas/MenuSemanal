package com.tip.MenuSemanal.Adaptadores;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.MainActivity;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;

import Clases.Ingrediente;
import Enumeracion.unidades;

import static com.tip.MenuSemanal.R.color.botones;
import static com.tip.MenuSemanal.R.color.purple_200;



public class AdapterListaIngredientes extends RecyclerView.Adapter<AdapterListaIngredientes.ViewHoldersDatos> {
    ImageButton btnMas;
    ImageButton btnRes;
    ArrayList<Ingrediente> listaIngrediente;
    ArrayAdapter<String> listaAdapter;
    ArrayList<String> lista = new ArrayList<String>();
    boolean multiselect = false;



    public AdapterListaIngredientes(@NonNull ArrayList<Ingrediente> lingrediente, ImageButton btnMas,ImageButton btnRes) {
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
        this.btnMas = btnMas;
        this.btnRes = btnRes;
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

                if(multiselect){
                    ing.setSel(!ing.getSel());
                     notifyItemChanged(position);
                }
            }
        });

        holder.item.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                multiselect = !multiselect;
                if (!multiselect) {

                    for (Ingrediente ingr : listaIngrediente) {
                        ingr.setSel(false);

                    }
                }else {
                    ing.setSel(true);
                    btnMas.setImageResource(R.drawable.ic_baseline_delete_24);
                    btnMas.setTag(R.drawable.ic_baseline_delete_24);
                    btnRes.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
                return true;
            }
        });

        if(ing.getSel()){
                holder.item.setBackgroundColor(Color.GRAY)                                                                                              ;
        } else
            holder.item.setBackgroundColor(Color.TRANSPARENT);


    }

    @Override
    public int getItemCount() {
        return listaIngrediente.size();
    }

    public void cancelSelected(){
        multiselect=false;
        for(Ingrediente i : listaIngrediente){
            i.setSel(false);
        }

        notifyDataSetChanged();
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
        listaIngrediente.add (0,new Ingrediente("-1","",0f,0, unidades.GR.toString()));

        notifyDataSetChanged();
    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        EditText edCantidad;
        AutoCompleteTextView acNombre;
        Spinner spinUnidad ;
        CheckBox chk;
        View item;

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

        @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})

        public void asignardatos(@NotNull Ingrediente ingrediente, int position) {
            acNombre.setText(ingrediente.getNombre());
            edCantidad.setText(Integer.toString(ingrediente.getCantidad()));
            spinUnidad.setSelection(getSpinnerIndex(spinUnidad,ingrediente.getUnidad()));
            acNombre.setAdapter(new ArrayAdapter<String>( item.getContext(),android.R.layout.simple_dropdown_item_1line,lista));

            acNombre.setThreshold(2);

            //spinUnidad.setEnabled(ingrediente.getId().equals("-1"));

            if (ingrediente.getId().equals("-1")){
                ingrediente.setId("");
                acNombre.requestFocus();
            }

            acNombre.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getAction()== MotionEvent.ACTION_DOWN && multiselect ) {
                        return true;
                    }


                    if (motionEvent.getAction()== MotionEvent.ACTION_UP  ) {
                        item.performClick();
                    }

                    return false;
                }
            });

            acNombre.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    item.performLongClick();

                    return false;
                }
            });

            edCantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b) {
                         if(edCantidad.getText().equals("0")) edCantidad.setText("");
                    }else
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

        return 0;
    }


}