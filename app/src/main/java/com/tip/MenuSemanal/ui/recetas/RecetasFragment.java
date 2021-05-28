package com.tip.MenuSemanal.ui.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tip.MenuSemanal.AdapterListaReceta;
import com.tip.MenuSemanal.R;

import java.util.ArrayList;

import Clases.Recetas;

import static androidx.navigation.Navigation.findNavController;

public class RecetasFragment extends Fragment {


    private RecetasViewModel recetasViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Recetas> recetas;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        recetas = new ArrayList<Recetas>();
        recetas.add(new Recetas("salsa de damasco","nada"));
        recetas.add(new Recetas("arroz con leche","nada"));
        recetas.add(new Recetas("pancito al guiso","nada"));


        recetasViewModel =
                new ViewModelProvider(this).get(RecetasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recetas, container, false);
        //final TextView textView = root.findViewById(//id.text_recetas);

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

                    findNavController(view).navigate(R.id.fragmentAgregarReceta);
                }

        });
        return root;
    }
}