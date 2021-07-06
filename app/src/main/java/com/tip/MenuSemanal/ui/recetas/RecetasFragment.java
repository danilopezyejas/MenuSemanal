package com.tip.MenuSemanal.ui.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.Adaptadores.AdapterDatosIngredientes;
import com.tip.MenuSemanal.Adaptadores.AdapterListaReceta;
import com.tip.MenuSemanal.AgregarIngredientes;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;
import Clases.Recetas;

import static androidx.navigation.Navigation.findNavController;

public class RecetasFragment extends Fragment {

    private static final String PREVIO = "previo";
    private static final String COMIDA = "comida";
    private static final String DIA = "dia";

    private String previo ="";
    private String comida ="";
    private String dia ="";

    private RecetasViewModel recetasViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Recetas> recetas;
    private DatabaseReference db;

    private FloatingActionButton btnA;
    //private FloatingActionButton btnE;
    private MenuItem miBorrar ;

    public RecetasFragment(){ }

    public static RecetasFragment  newInstance (String previo, String comida, String dia){
        RecetasFragment fragment = new RecetasFragment();
        Bundle args = new Bundle();
        args.putString(PREVIO,previo);
        args.putString(COMIDA,comida);
        args.putString(DIA,dia);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);


        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            previo = getArguments().getString(PREVIO);
            comida = getArguments().getString(COMIDA);
            dia = getArguments().getString(DIA);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menurecetas,menu);
        if (previo.equals("menu")) menu.findItem(R.id.menuBorrar).setVisible(false);
        menu.findItem(R.id.menuAceptar).setVisible(false);
        final MenuItem searchItem = menu.findItem(R.id.menuBuscar);
        final SearchView searchView = (SearchView)searchItem.getActionView();//(SearchView) MenuItemCompat.getActionView(searchItem);
        //permite modificar el hint que el EditText muestra por defecto

        searchView.setQueryHint("busqueda");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //se oculta el EditText
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Recetas> listabusqueda = new ArrayList<Recetas>();
                for(Recetas i : recetas){
                    if (i.getNombre().toUpperCase().contains(newText.toUpperCase())) listabusqueda.add(i);
                }

                if (!newText.equals(""))
                    recyclerView.setAdapter(new AdapterListaReceta(listabusqueda, previo, comida, dia));
                else
                    recyclerView.setAdapter(new AdapterListaReceta(recetas, previo, comida, dia));
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuBorrar:
                for(Recetas res: recetas){
                    if (res.getSel()) {
                        db.child(res.getNombre()).removeValue();
                    }
                }
                ((AdapterListaReceta)recyclerView.getAdapter()).removeSelected();


            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        recetas = new ArrayList<Recetas>();

        recetasViewModel =
                new ViewModelProvider(this).get(RecetasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recetas, container, false);

        //final TextView textView = root.findViewById(//id.text_recetas);
        //Obtengo de la base la tabla Ingredientes
        db = FirebaseDatabase.getInstance().getReference("Recetas");

        //Evaluo si trajo algo
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                recetas.clear();

                for(DataSnapshot recetasDataSnap : snapshot.getChildren()){
                    String nomreceta = recetasDataSnap.getKey();

                    String descrip = "";
                    if(recetasDataSnap.child("Descripcion").getValue()!=null)
                        descrip = recetasDataSnap.child("Descripcion").getValue().toString();//recetas.add(receta);
                    recetas.add(new Recetas(nomreceta,descrip));

                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });

        recyclerView = (RecyclerView) root.findViewById(R.id.rvlistaRecetas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new AdapterListaReceta(recetas, previo, comida, dia));
        recyclerView.getAdapter().notifyDataSetChanged();


        recetasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        // .. textView.setText(s);
                    }});

            btnA = root.findViewById(R.id.btnAgregarReceta);
            btnA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putString("param1","");

                    findNavController(view).navigate(R.id.fragmentAgregarReceta,b);
                }

        });

            //btnE = root.findViewById(R.id.btnEliminarReceta);
//            btnE.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    for(Recetas res: recetas){
//                        if (res.getSel()) {
//                            db.child(res.getNombre()).removeValue();
//                        }
//                    }
//                    ((AdapterListaReceta)recyclerView.getAdapter()).removeSelected();
//
//
//
//                }
//            });

        if (previo.equals("menu")){
            btnA.setVisibility(root.INVISIBLE);
           // btnE.setVisibility(root.INVISIBLE);
        }


        return root;
    }



}