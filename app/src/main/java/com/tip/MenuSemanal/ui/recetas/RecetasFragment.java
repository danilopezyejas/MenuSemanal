package com.tip.MenuSemanal.ui.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.AdapterListaReceta;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;
import Clases.Recetas;

import static androidx.navigation.Navigation.findNavController;

public class RecetasFragment extends Fragment {


    private RecetasViewModel recetasViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Recetas> recetas;
    private DatabaseReference db;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        recetas = new ArrayList<Recetas>();

        recetasViewModel =
                new ViewModelProvider(this).get(RecetasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recetas, container, false);
        //final TextView textView = root.findViewById(//id.text_recetas);
        //Obtengo de la base la tabla Ingredientes
        db = FirebaseDatabase.getInstance().getReference("Recetas");

        //Evaluo si trajo algo
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

               // recetas.clear();

                for(DataSnapshot recetasDataSnap : snapshot.getChildren()){
                    String nomreceta = recetasDataSnap.getKey();

                    String descrip = "";
                    if(recetasDataSnap.child("Descripcion").getValue()!=null)
                        descrip = recetasDataSnap.child("Descripcion").getValue().toString();//recetas.add(receta);
                    recetas.add(new Recetas(nomreceta,descrip));

                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });

        recyclerView = (RecyclerView) root.findViewById(R.id.rvlistaRecetas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new AdapterListaReceta(recetas));
        recyclerView.getAdapter().notifyDataSetChanged();


        recetasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        // .. textView.setText(s);
                    }});
            FloatingActionButton btnA = root.findViewById(R.id.btnAgregarReceta);
            btnA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putString("param1","nuevo");

                    findNavController(view).navigate(R.id.fragmentAgregarReceta,b);
                }

        });


        return root;
    }



}