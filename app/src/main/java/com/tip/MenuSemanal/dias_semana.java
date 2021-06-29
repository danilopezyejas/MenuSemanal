package com.tip.MenuSemanal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.MenuSemanal.Adaptadores.AdapterDias;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Clases.Menu;

import static androidx.navigation.Navigation.findNavController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dias_semana#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dias_semana extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private AdapterDias adapterDias;
    private DatabaseReference db;
//    private ArrayList<Menu> listaMenu;
    private FloatingActionButton addDesayuno;
    private FloatingActionButton addAlmuerzo;
    private FloatingActionButton addMerienda;
    private FloatingActionButton addCena;

    Bundle paraRecetas = new Bundle();

    private int dia;


    public dias_semana() {
    }


    public dias_semana(int dia) {
        this.dia = dia;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dias_semana.
     */
    // TODO: Rename and change types and number of parameters
    public static dias_semana newInstance(String param1, String param2) {
        dias_semana fragment = new dias_semana();
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
        View view = inflater.inflate(R.layout.fragment_dias_semana, container, false);

        db = FirebaseDatabase.getInstance().getReference("Menu-Semana");

        //Evaluo si trajo algo
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for(DataSnapshot menuDataSnap : snapshot.getChildren()){
                    Menu menu = menuDataSnap.getValue(Menu.class);

                    switch (menu.getComida()){
                        case "desayuno":
                            completarDatos(R.id.nomRecetaDesayuno,menu, view);
                            break;
                        case "almuerzo":
                            completarDatos(R.id.nomRecetaAlmuerzo,menu, view);
                            break;
                        case "merienda":
                            completarDatos(R.id.nomRecetaMerienda,menu, view);
                            break;
                        case "cena":
                            completarDatos(R.id.nomRecetaCena,menu, view);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        addDesayuno = view.findViewById(R.id.addDesayuno);
        addDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paraRecetas.putString("previo","menu");
                paraRecetas.putString("comida","desayuno");
                findNavController(view).navigate(R.id.navigation_recetas, paraRecetas);
            }
        });

        addDesayuno = view.findViewById(R.id.addDesayuno);
        addDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paraRecetas.putString("previo","menu");
                paraRecetas.putString("comida","desayuno");
                findNavController(view).navigate(R.id.navigation_recetas, paraRecetas);
            }
        });
        addAlmuerzo = view.findViewById(R.id.addAlmuerzo);
        addAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paraRecetas.putString("previo","menu");
                paraRecetas.putString("comida","almuerzo");
                findNavController(view).navigate(R.id.navigation_recetas, paraRecetas);
            }
        });
        addMerienda = view.findViewById(R.id.addMerienda);
        addMerienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paraRecetas.putString("previo","menu");
                paraRecetas.putString("comida","merienda");
                findNavController(view).navigate(R.id.navigation_recetas, paraRecetas);
            }
        });
        addCena = view.findViewById(R.id.addCena);
        addCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paraRecetas.putString("previo","menu");
                paraRecetas.putString("comida","cena");
                findNavController(view).navigate(R.id.navigation_recetas, paraRecetas);
            }
        });

        return view;
    }

    private void completarDatos(int id, Menu menu, View view){

        TextView textView;

        Date currentDate = new Date (menu.getFecha());
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//Si el dia de la semana coincide con el dia de la receta lo agrego
        if (dayOfWeek == this.dia){
            textView = view.findViewById(id);
            textView.setText(menu.getReceta());
        }

    }
}