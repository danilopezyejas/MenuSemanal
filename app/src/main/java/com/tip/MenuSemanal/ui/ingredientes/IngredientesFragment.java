package com.tip.MenuSemanal.ui.ingredientes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.AdapterDatosIngredientes;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Clases.Ingrediente;

import static androidx.navigation.Navigation.findNavController;

public class IngredientesFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterDatosIngredientes adapterDatosIngredientes;
    private  ArrayList<Ingrediente> listaIngredientes;
    private DatabaseReference db;

    private  TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ingredientes, container, false);

        recyclerView = root.findViewById(R.id.recycleIngredientes);
        listaIngredientes = new ArrayList<>();

        //Obtengo de la base la tabla Ingredientes
        db = FirebaseDatabase.getInstance().getReference("Ingredientes");

        //Evaluo si trajo algo
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                listaIngredientes.clear();
                for(DataSnapshot ingredientesDataSnap : snapshot.getChildren()){
                    Ingrediente ingrediente = ingredientesDataSnap.getValue(Ingrediente.class);
                    listaIngredientes.add(ingrediente);
                }
                mostrar();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        FloatingActionButton btnAgregarIngrediente = root.findViewById(R.id.btnAgregarIngredientes);
        btnAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.agregarIngredientes);
            }
        });

        return root;
    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences("ingredienteGuardados", Context.MODE_PRIVATE);
    }

//    Mando la lista que descargue de la base al Adapter para que lo muestre en el recycler
    private  void mostrar(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterDatosIngredientes = new AdapterDatosIngredientes(listaIngredientes);
        recyclerView.setAdapter(adapterDatosIngredientes);
    }

}