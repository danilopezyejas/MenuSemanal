package com.tip.MenuSemanal;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterDatosIngredientes extends RecyclerView.Adapter<AdapterDatosIngredientes.ViewHolderDatos> {

    ArrayList<String> listaIngredientes;

    public AdapterDatosIngredientes(ArrayList<String>listaIngredientes){
        this.listaIngredientes = listaIngredientes;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderDatos holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView nombreIngrediente;
        
        public ViewHolderDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            nombreIngrediente = (TextView) itemView.findViewById(R.id.textView3);
            nombreIngrediente.setText("Algo");
        }
    }
}
