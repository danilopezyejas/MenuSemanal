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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tip.MenuSemanal.R;

import static androidx.navigation.Navigation.findNavController;

public class IngredientesFragment extends Fragment {

    private IngredientesViewModel ingredientesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ingredientesViewModel =
                new ViewModelProvider(this).get(IngredientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ingredientes, container, false);
        
        cargarPreferencias();


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

        String nombre = preferences.getString("nombre","");
        float precio = preferences.getFloat("precio", 0);
        Integer cantidad = preferences.getInt("cantidad", 0);
        String unidad = preferences.getString("unidad","");

    }

}