package com.tip.MenuSemanal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Utilities;
import com.tip.MenuSemanal.Adaptadores.AdapterListaIngredientes;
import com.tip.MenuSemanal.Adaptadores.AdapterListaReceta;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import Clases.Ingrediente;
import Clases.Recetas;
import Enumeracion.unidades;

import static androidx.navigation.Navigation.findNavController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAgregarReceta#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentAgregarReceta extends Fragment {

    boolean aborta = false;
    boolean exist = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatabaseReference db;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner spinUnidad;
    ImageButton btnagregar;
    ImageButton btnBorrar;
    ImageButton btnAceptar;
    RecyclerView recyclerView;
    EditText edDescripcion;
    TextView txtnombrer;
    ArrayList<Ingrediente> listaIngredientes = new ArrayList<Ingrediente>();
    View view;

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
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menurecetas,menu);

        menu.findItem(R.id.menuBuscar).setVisible(false);
        menu.findItem(R.id.menuBorrar).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuBorrar:

            case R.id.menuAceptar:
                txtnombrer.requestFocus();
                String nombreReceta = txtnombrer.getText().toString();
                if (!nombreReceta.equals("")) {
                    if(!mParam1.equals(nombreReceta))
                        existeReceta(nombreReceta);
                    else {
                        guardaReceta(nombreReceta);
                        findNavController(view).navigateUp();
                    }
                } else {
                    Toast.makeText(getActivity(), "Ingrese nombre de receta", Toast.LENGTH_LONG).show();
                }



        default:
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseDatabase.getInstance().getReference();


        view = inflater.inflate(R.layout.fragment_agregar_receta, container, false);
        txtnombrer =(TextView) view.findViewById(R.id.edNombreReceta);
        edDescripcion =(EditText) view.findViewById(R.id.edDescripcion);
        btnagregar= (ImageButton) view.findViewById(R.id.btnAgregaring);
        btnBorrar = (ImageButton) view.findViewById(R.id.btnBorraring);
        //btnAceptar=(ImageButton) view.findViewById(R.id.btnAceptarIngredientes);
        txtnombrer.setText(mParam1);
        btnBorrar.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvlistaIngredientes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new AdapterListaIngredientes(listaIngredientes,btnagregar,btnBorrar));
        cargaRecetas();

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer resource = (Integer)btnagregar.getTag();
                if (resource != null && resource != R.drawable.ic_baseline_add_24) {
                    ((AdapterListaIngredientes) Objects.requireNonNull(recyclerView.getAdapter())).removeSelected();
                    btnagregar.setImageResource(R.drawable.ic_baseline_add_24);
                    btnagregar.setTag(R.drawable.ic_baseline_add_24);
                    btnBorrar.setVisibility(View.INVISIBLE);
                } else {
                    ((AdapterListaIngredientes) Objects.requireNonNull(recyclerView.getAdapter())).nuevoingrediente();
                    recyclerView.scrollToPosition(0);
                }
            }

        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ((AdapterListaIngredientes) Objects.requireNonNull(recyclerView.getAdapter())).cancelSelected();
                    btnagregar.setImageResource(R.drawable.ic_baseline_add_24);
                    btnagregar.setTag(R.drawable.ic_baseline_add_24);
                    btnBorrar.setVisibility(View.INVISIBLE);

            }
        });

//            btnAceptar.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    txtnombrer.requestFocus();
//                    String nombreReceta = txtnombrer.getText().toString();
//                    if (!nombreReceta.equals("")) {
//                       if(!mParam1.equals(nombreReceta))
//                           existeReceta(nombreReceta);
//                       else {
//                           guardaReceta(nombreReceta);
//                           findNavController(view).navigateUp();
//                       }
//                    } else {
//                        Toast.makeText(getActivity(), "Ingrese nombre de receta", Toast.LENGTH_LONG).show();
//                    }
//                }
//
//        });
        return view;
    }

   private void guardaReceta(String nombreReceta){
        DatabaseReference db2;

        //si no es una receta nueva la eliminamos. para luego reescribirla
        if (!mParam1.equals("")) db.child("Recetas").child(mParam1).removeValue();

       //descripcion de receta
        db.child("Recetas").child(nombreReceta).child("Descripcion").setValue(edDescripcion.getText().toString());

       //guardamos todos los ingredientes
        for (Ingrediente i : listaIngredientes) {
           if (!i.getNombre().equals("")) {
               db2 = FirebaseDatabase.getInstance().getReference("Ingredientes");

               DatabaseReference finalDb = db2;
               //obtenemos la lista de ingredientes
               db2.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                       boolean noIngresado = true;

                       for(DataSnapshot ingredientesDataSnap : snapshot.getChildren()){
                           Ingrediente ingrediente = ingredientesDataSnap.getValue(Ingrediente.class);
                           if(ingrediente.getNombre().equalsIgnoreCase(i.getNombre())) {
                               i.setId(ingrediente.getId());
                               db.child("Recetas").child(nombreReceta).child(ingrediente.getNombre()).setValue(i);
                               noIngresado = false;
                               break;
                           }
                       }
                       if (noIngresado){
                            //si no existe el ingrediente lo ingresamos
                            String nuevoid = db.push().getKey();
                            i.setId(nuevoid);
                            finalDb.child(nuevoid).setValue(i);

                            db.child("Recetas").child(nombreReceta).child(i.getNombre()).setValue(i);
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull @NotNull DatabaseError error) {

                   }


               });




           }
       }
    }


    private void nuevoIngrediente(Ingrediente i){

    }


    private void existeReceta (String nombreReceta) {
        db.child("Recetas").orderByKey().equalTo(nombreReceta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    guardaReceta(nombreReceta);
                    findNavController(view).navigateUp();
                } else
                Toast.makeText(getActivity(), "Ya existe receta con nombre: " + nombreReceta, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



    private void cargaRecetas() {

        if (!mParam1.equals("")){
            ArrayList<Ingrediente> li = new ArrayList<Ingrediente>();

            db.child("Ingredientes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for( DataSnapshot ing : snapshot.getChildren()){
                        li.add (ing.getValue(Ingrediente.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

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
                        for (Ingrediente i : li){
                            if(i.getId().equalsIgnoreCase(ingrediente.getId())){
                                ingrediente.setNombre(i.getNombre());
                            }
                        }
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

    @NonNull
    @NotNull

    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onStop() {
        super.onStop();
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//                builder.setMessage("hola")
//                        .setPositiveButton("hola", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Toast.makeText(getContext(), "hola", Toast.LENGTH_SHORT).show();// FIRE ZE MISSILES!
//
//                                notifyAll();
//
//                            }
//                        })
//                        .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // User cancelled the dialog
//                            notifyAll();
//                            }
//                        });
//                // Create the AlertDialog object and return it
//                builder.create().show();
//
//
//
//
    }

}