package com.tip.MenuSemanal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Clases.Ingrediente;
import Enumeracion.unidades;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAgregarReceta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAgregarReceta extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txtUnidad;

    public FragmentAgregarReceta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAgregarReceta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAgregarReceta newInstance(String param1, String param2) {
        FragmentAgregarReceta fragment = new FragmentAgregarReceta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_agregar_receta, container, false);

        txtUnidad = (TextView) view.findViewById(R.id.txtUnidad);
        //ArrayAdapter<unidades> u = new ArrayAdapter<unidades>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, unidades.values());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvlistaIngredientes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new AdapterListaIngredientes(cargaingredientes()));
        recyclerView.getAdapter().notifyDataSetChanged();

        return view;
    }


    ArrayList<Ingrediente> cargaingredientes(){
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        ingredientes.add(new Ingrediente("1","papa",100,4,unidades.GR.toString()));
        ingredientes.add(new Ingrediente("2","choclo",100,3,unidades.GR.toString()));
        ingredientes.add(new Ingrediente("1","papa",100,100,unidades.GR.toString()));
        ingredientes.add(new Ingrediente("1","papa",100,100,unidades.GR.toString()));
        return ingredientes;
    }

}