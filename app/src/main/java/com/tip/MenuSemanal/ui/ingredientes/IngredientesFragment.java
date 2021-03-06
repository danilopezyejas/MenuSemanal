package com.tip.MenuSemanal.ui.ingredientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.Adaptadores.AdapterDatosIngredientes;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;

import static androidx.navigation.Navigation.findNavController;

public class IngredientesFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterDatosIngredientes adapterDatosIngredientes;
    private  ArrayList<Ingrediente> listaIngredientes;
    private  ArrayList<Ingrediente> aBorrar;
    private DatabaseReference db;
    FloatingActionButton btnAgregarIngrediente;
    private ProgressBar espera;

    private  TextView textView;

    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menurecetas,menu);

        menu.findItem(R.id.menuBorrar).setVisible(false);
        menu.findItem(R.id.menuAceptar).setVisible(false);
        final MenuItem searchItem = menu.findItem(R.id.menuBuscar);
        final SearchView searchView = (SearchView)searchItem.getActionView();//(SearchView) MenuItemCompat.getActionView(searchItem);
        //permite modificar el hint que el EditText muestra por defecto

        searchView.setQueryHint("busqueda");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //se oculta el EditText
//                searchView.setQuery("", false);
  //              searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Ingrediente> listabusqueda = new ArrayList<Ingrediente>();
                for(Ingrediente i : listaIngredientes){
                    if (i.getNombre().toUpperCase().contains(newText.toUpperCase())) listabusqueda.add(i);
                }
                adapterDatosIngredientes = new AdapterDatosIngredientes(listabusqueda, btnAgregarIngrediente, getContext());
                recyclerView.setAdapter(adapterDatosIngredientes);
                if (newText.equals("")) mostrar();
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
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ingredientes, container, false);

        //Mostrar spinner
        espera = root.findViewById(R.id.espera);
        espera.setVisibility(View.VISIBLE);

        recyclerView = root.findViewById(R.id.recycleIngredientes);
        listaIngredientes = new ArrayList<>();
        aBorrar = new ArrayList<>();


        //Obtengo de la base la tabla Ingredientes
        db = FirebaseDatabase.getInstance().getReference("Ingredientes");

        //Evaluo si trajo algo

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                listaIngredientes.clear();
                for(DataSnapshot ingredientesDataSnap : snapshot.getChildren()){
                    Ingrediente ingrediente = ingredientesDataSnap.getValue(Ingrediente.class);
                    listaIngredientes.add(ingrediente);
                }
                mostrar();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btnAgregarIngrediente = root.findViewById(R.id.btnAgregarIngredientes);
        btnAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        Pregunto que icono tiene el boton si es borrar entra si tiene el carrito se va al else
                Integer resource = (Integer)btnAgregarIngrediente.getTag();
                if (resource != null && resource != R.drawable.ic_agregar){
                    aBorrar = adapterDatosIngredientes.getIngreBorrar();
                    for (Ingrediente ingre : aBorrar) {
                        db.child(ingre.getId()).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>(){
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
//          Si se borro bien cambio el icono del boton
                                if(task.isComplete()){
                                    btnAgregarIngrediente.setImageResource(R.drawable.ic_agregar);
                                    btnAgregarIngrediente.setTag(R.drawable.ic_agregar);
                                    Toast.makeText(getActivity(),"Se borro "+ingre.getNombre(),Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(),"Ocurrio un error!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    findNavController(view).navigate(R.id.agregarIngredientes);
                }
            }
        });

        return root;
    }

//    Mando la lista que descargue de la base al Adapter para que lo muestre en el recycler
    private  void mostrar(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterDatosIngredientes = new AdapterDatosIngredientes(listaIngredientes, btnAgregarIngrediente, getContext());
        recyclerView.setAdapter(adapterDatosIngredientes);
//        ocultar spinner
        espera.setVisibility(View.INVISIBLE);
    }

}