package com.tip.MenuSemanal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import Enumeracion.unidades;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarIngredientes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarIngredientes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner unidadSelecionada;

    public AgregarIngredientes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarIngredientes.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarIngredientes newInstance(String param1, String param2) {
        AgregarIngredientes fragment = new AgregarIngredientes();
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

        View view =  inflater.inflate(R.layout.fragment_agregar_ingredientes, container, false);
        unidadSelecionada = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<unidades> u = new ArrayAdapter<unidades>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, unidades.values());
        unidadSelecionada.setAdapter(u);

        Button agregar = (Button) view.findViewById(R.id.btnAceptarIngredientes);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String nombre = getView().findViewById(R.id.nombreIngrediente).toString();
                    EditText etPrecio = getView().findViewById(R.id.precioIngrediente);
                    int precio = Integer.parseInt(etPrecio.getText().toString());
                    EditText etCantidad = getView().findViewById(R.id.etCantidad);
                    int cantidad = Integer.parseInt(etCantidad.getText().toString());
                    Spinner spUnidad = (Spinner) getView().findViewById(R.id.spinner);
                    String unidad = spUnidad.getSelectedItem().toString();

                SharedPreferences preferences = getActivity().getSharedPreferences("ingredienteGuardados", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("nombre",nombre);
                editor.putFloat("precio",precio);
                editor.putInt("cantidad",cantidad);
                editor.putString("unidad",unidad);

                editor.commit();

                Navigation.findNavController(view).navigate(R.id.ir_a_altaIngredientes);

            }
        });

        return view;
    }

}