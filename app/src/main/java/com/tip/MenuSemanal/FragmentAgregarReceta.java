package com.tip.MenuSemanal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.Adaptadores.AdapterListaIngredientes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import Clases.Ingrediente;

import static androidx.navigation.Navigation.findNavController;

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
    DatabaseReference db;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txtUnidad;
    ImageButton btnagregar;
    ImageButton btnBorrar;
    ImageButton btnAceptar;
    RecyclerView recyclerView;
    EditText edDescripcion;
    ArrayList<Ingrediente> listaIngredientes = new ArrayList<Ingrediente>();


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
            Toast.makeText(getContext(),mParam1,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseDatabase.getInstance().getReference();


        View view = inflater.inflate(R.layout.fragment_agregar_receta, container, false);
        TextView txtnombrer =(TextView) view.findViewById(R.id.edNombreReceta);
        edDescripcion =(EditText) view.findViewById(R.id.edDescripcion);
        txtUnidad = (TextView) view.findViewById(R.id.txtUnidad);
        btnagregar= (ImageButton) view.findViewById(R.id.btnAgregaring);
        btnBorrar = (ImageButton) view.findViewById(R.id.btnBorraring);
        btnAceptar=(ImageButton) view.findViewById(R.id.btnAceptarIngredientes);
        txtnombrer.setText(mParam1);
        //ArrayAdapter<unidades> u = new ArrayAdapter<unidades>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, unidades.values());
        recyclerView = (RecyclerView) view.findViewById(R.id.rvlistaIngredientes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new AdapterListaIngredientes(listaIngredientes));
        cargaRecetas();

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AdapterListaIngredientes) Objects.requireNonNull(recyclerView.getAdapter())).nuevoingrediente();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Ingrediente ing : listaIngredientes){
                    if (ing.getSel())
                        db.child("Recetas").child(txtnombrer.getText().toString()).child(ing.getNombre().toString()).removeValue();

                }

                ((AdapterListaIngredientes) Objects.requireNonNull(recyclerView.getAdapter())).removeSelected();

            }
        });

            btnAceptar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String nombreReceta = txtnombrer.getText().toString();

                    if (!nombreReceta.equals("")) {

                        if(!mParam1.equals(nombreReceta)){
                            db.child("Recetas").child(mParam1).removeValue();
                        };

                        db.child("Recetas").child(nombreReceta).child("Descripcion").setValue(edDescripcion.getText().toString());

                        for (Ingrediente i : listaIngredientes) {
                            //Toast.makeText(getActivity(),i.getNombre(),Toast.LENGTH_SHORT).show();
                            if (!i.getNombre().equals("")) {
                                System.out.println(i.getNombre() + " " + i.getCantidad());

                                db.child("Recetas").child(nombreReceta).child(i.getNombre()).setValue(i).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task2) {
                                        //Compruebo si se agrego bien a la base
                                        if (task2.isComplete()) {

                                        } else {
                                            Toast.makeText(getActivity(), "Ocurrio un error!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }

                    } else
                    {
                        Toast.makeText(getActivity(),"Ingrese nombre de receta",Toast.LENGTH_LONG).show();
                    }
                }

        });
        return view;
    }

    private void cargaRecetas() {
        //Evaluo si trajo algo
        if (!mParam1.equals("")){
        db.child("Recetas").child(mParam1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                listaIngredientes.clear();

                if(snapshot.child("Descripcion").getValue()!=null){
                    edDescripcion.setText(snapshot.child("Descripcion").getValue().toString()); }
                //
                for(DataSnapshot ingredientesDataSnap : snapshot.getChildren()){
                    if (!ingredientesDataSnap.getKey().equals("Descripcion")){
                    Ingrediente ingrediente = ingredientesDataSnap.getValue(Ingrediente.class);
                    ingrediente.setSel(false);
                    listaIngredientes.add(ingrediente);
                }}
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }}


}