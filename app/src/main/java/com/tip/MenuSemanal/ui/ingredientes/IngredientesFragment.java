package com.tip.MenuSemanal.ui.ingredientes;

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

import com.tip.MenuSemanal.R;

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
        return root;
    }
}