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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tip.MenuSemanal.R;

import static androidx.navigation.Navigation.findNavController;

public class RecetasFragment extends Fragment {

    private RecetasViewModel recetasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recetasViewModel =
                new ViewModelProvider(this).get(RecetasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recetas, container, false);
        //final TextView textView = root.findViewById(//id.text_recetas);
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