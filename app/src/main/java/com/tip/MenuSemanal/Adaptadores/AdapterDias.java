package com.tip.MenuSemanal.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Menu;
import Clases.Recetas;

public class AdapterDias extends RecyclerView.Adapter<AdapterDias.ViewHolderDatos>{

    ArrayList<Menu> listaMenu;
    Context context;

    public AdapterDias(ArrayList<Menu> listaMenu, Context context) {
        this.listaMenu = listaMenu;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_dias ,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderDatos holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listaMenu.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        public ViewHolderDatos(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
