package com.tip.MenuSemanal.Adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterPestanias extends FragmentStateAdapter {

    ArrayList<Fragment> semana = new ArrayList<>();

    public AdapterPestanias(@NonNull @NotNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return semana.get(position);
    }

    @Override
    public int getItemCount() {
        return semana.size();
    }

    public void agregarFragment(Fragment fragment){
        semana.add(fragment);
    }
}
