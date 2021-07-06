package com.tip.MenuSemanal.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.Adaptadores.AdapterDatosNotificacion;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Clases.Ingrediente;
import Clases.Menu;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Ingrediente> listaIngredientes = new ArrayList<>();
    private DatabaseReference db;
    private AdapterDatosNotificacion adapterDatosNotificacion;
    private ArrayList<Ingrediente> allIngredientes = new ArrayList<>();
    private ArrayList<Ingrediente> ingredientesRecetasMenu = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.recycleNotificaciones);

        //Obtengo de la base la tabla Ingredientes
        db = FirebaseDatabase.getInstance().getReference("Ingredientes");
        ingredientesRecetasMenu.clear();

        //Evaluo si trajo algo
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for(DataSnapshot ingredientesDataSnap : snapshot.getChildren()){
                    Ingrediente ingrediente = ingredientesDataSnap.getValue(Ingrediente.class);
                    allIngredientes.add(ingrediente);
                }

                db = FirebaseDatabase.getInstance().getReference("Menu-Semana");

                //Evaluo si trajo algo
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        db = FirebaseDatabase.getInstance().getReference();
                        for(DataSnapshot menuSnapshot : snapshot.getChildren()){
                            Menu menu = menuSnapshot.getValue(Menu.class);

                            db.child("Recetas").child(menu.getReceta()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    //
                                    for(DataSnapshot ingredientesDataSnap : snapshot.getChildren()){
                                        if (!ingredientesDataSnap.getKey().equals("Descripcion")){
                                            Ingrediente ingrediente = ingredientesDataSnap.getValue(Ingrediente.class);
                                            for (Ingrediente i : allIngredientes){
                                                if(i.getId().equalsIgnoreCase(ingrediente.getId()) && i.getCantidad() == 0){
                                                    int cant = 0;
                                                    for (Ingrediente yaEsta : ingredientesRecetasMenu){
                                                        if (yaEsta.getNombre().equals(ingrediente.getNombre())){
                                                            ingredientesRecetasMenu.remove(cant);
                                                            ingrediente.setCantidad(yaEsta.getCantidad() + ingrediente.getCantidad());
                                                            break;
                                                        }
                                                        cant++;
                                                    }
                                                    ingredientesRecetasMenu.add(ingrediente);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    mostrar();
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        return root;
    }

    //    Mando la lista que descargue de la base al Adapter para que lo muestre en el recycler
    private  void mostrar(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterDatosNotificacion = new AdapterDatosNotificacion(ingredientesRecetasMenu, getContext());
        recyclerView.setAdapter(adapterDatosNotificacion);


//        ocultar spinner
//        espera.setVisibility(View.INVISIBLE);
    }

}