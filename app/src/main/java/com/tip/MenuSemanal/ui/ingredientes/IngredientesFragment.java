package com.tip.MenuSemanal.ui.ingredientes;

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
        final TextView textView = root.findViewById(R.id.text_ingredientes);
        ingredientesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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

    public void agregarIngrediente(){
        EditText nombre = getView().findViewById(R.id.nombreIngrediente);
        EditText etPrecio = getView().findViewById(R.id.precioIngrediente);
        int precio = Integer.parseInt(etPrecio.getText().toString());
    }
}