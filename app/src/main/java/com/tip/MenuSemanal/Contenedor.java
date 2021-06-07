package com.tip.MenuSemanal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Contenedor extends Fragment {

    private String mParam1;
    private String mParam2;

    public Contenedor() {
        // Required empty public constructor
    }

    public static Contenedor newInstance(String param1, String param2) {
        Contenedor fragment = new Contenedor();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootWiew = (ViewGroup) inflater.inflate(R.layout.fragment_contenedor, container, false);
        return rootWiew;
    }
}