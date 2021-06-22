package com.tip.MenuSemanal.ui.ingredientes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.Adaptadores.AdapterDatosIngredientes;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;

import static androidx.navigation.Navigation.findNavController;

public class IngredientesFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterDatosIngredientes adapterDatosIngredientes;
    private  ArrayList<Ingrediente> listaIngredientes;
    private  ArrayList<Ingrediente> aBorrar;
    private DatabaseReference db;
    FloatingActionButton btnAgregarIngrediente;
    private ProgressBar espera;

    private  TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ingredientes, container, false);

        //Mostrar spinner
        espera = root.findViewById(R.id.espera);
        espera.setVisibility(View.VISIBLE);

        recyclerView = root.findViewById(R.id.recycleIngredientes);
        listaIngredientes = new ArrayList<>();
        aBorrar = new ArrayList<>();


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

        btnAgregarIngrediente = root.findViewById(R.id.btnAgregarIngredientes);
        btnAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        Pregunto que icono tiene el boton
                Integer resource = (Integer)btnAgregarIngrediente.getTag();
                if (resource != null && resource != R.drawable.ic_agregar){
                    aBorrar = adapterDatosIngredientes.getIngreBorrar();
                    for (Ingrediente ingre : aBorrar) {
                        db.child(ingre.getId()).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>(){
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
//          Si se borro bien cambio el icono del boton
                                if(task.isComplete()){
                                    btnAgregarIngrediente.setImageResource(R.drawable.ic_agregar);
                                    btnAgregarIngrediente.setTag(R.drawable.ic_agregar);
                                    Toast.makeText(getActivity(),"Se borro "+ingre.getNombre(),Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(),"Ocurrio un error!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    findNavController(view).navigate(R.id.agregarIngredientes);
                }
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
        adapterDatosIngredientes = new AdapterDatosIngredientes(listaIngredientes, btnAgregarIngrediente, getContext());
        recyclerView.setAdapter(adapterDatosIngredientes);
//        ocultar spinner
        espera.setVisibility(View.INVISIBLE);
    }

}