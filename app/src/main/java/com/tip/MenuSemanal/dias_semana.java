package com.tip.MenuSemanal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

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

    private DatabaseReference db;
    private FloatingActionButton addDesayuno;
    private FloatingActionButton addAlmuerzo;
    private FloatingActionButton addMerienda;
    private FloatingActionButton addCena;
    private ArrayList<Menu> listaMenu = new ArrayList<>();

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

        //Obtengo lo que hay en la base de datos
        db = FirebaseDatabase.getInstance().getReference("Menu-Semana");

        //Evaluo si trajo algo
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listaMenu.clear();

                for (DataSnapshot menuDataSnap : snapshot.getChildren()) {
                    Menu menu = menuDataSnap.getValue(Menu.class);
                    listaMenu.add(menu);

                    switch (menu.getComida()) {
                        case "desayuno":
                            completarDatos(R.id.nomRecetaDesayuno, menu, view);
                            break;
                        case "almuerzo":
                            completarDatos(R.id.nomRecetaAlmuerzo, menu, view);
                            break;
                        case "merienda":
                            completarDatos(R.id.nomRecetaMerienda, menu, view);
                            break;
                        case "cena":
                            completarDatos(R.id.nomRecetaCena, menu, view);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        paraRecetas.putString("previo", "menu");
        paraRecetas.putString("dia", Integer.toString(dia));

        addDesayuno = view.findViewById(R.id.addDesayuno);
        addDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        Pregunto que icono tiene el boton si es - entra y lo saca del menu, si es + va al else lo agrega al menu

                Integer resource = (Integer) addDesayuno.getTag();
                if (resource != null && resource != R.drawable.ic_add) {
                    sacar("desayuno");
                } else {
                    paraRecetas.putString("comida", "desayuno");
                    findNavController(view).navigate(R.id.navigation_recetas, paraRecetas);
                }
            }
        });

        addAlmuerzo = view.findViewById(R.id.addAlmuerzo);
        addAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        Pregunto que icono tiene el boton si es - entra y lo saca del menu, si es + va al else lo agrega al menu

                Integer resource = (Integer) addAlmuerzo.getTag();
                if (resource != null && resource != R.drawable.ic_add) {
                    sacar("almuerzo");
                } else {
                    paraRecetas.putString("comida", "almuerzo");
                    findNavController(view).navigate(R.id.navigation_recetas, paraRecetas);

                }
            }
        });
        addMerienda = view.findViewById(R.id.addMerienda);
        addMerienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        Pregunto que icono tiene el boton si es - entra y lo saca del menu, si es + va al else lo agrega al menu

                Integer resource = (Integer) addMerienda.getTag();
                if (resource != null && resource != R.drawable.ic_add) {
                    sacar("merienda");
                } else {
                    paraRecetas.putString("comida", "merienda");
                    findNavController(view).navigate(R.id.navigation_recetas, paraRecetas);
                }
            }
        });
        addCena = view.findViewById(R.id.addCena);
        addCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//        Pregunto que icono tiene el boton si es - entra y lo saca del menu, si es + va al else lo agrega al menu
                Integer resource = (Integer) addCena.getTag();
                if (resource != null && resource != R.drawable.ic_add) {
                    sacar("cena");
                } else {
                    paraRecetas.putString("comida", "cena");
                    findNavController(view).navigate(R.id.navigation_recetas, paraRecetas);
                }
            }
        });

        return view;
    }

    private void completarDatos(int id, Menu menu, View view) {
        TextView textView;

        if (pasoSemana(menu)) {
            db = FirebaseDatabase.getInstance().getReference();
            //Para guardar un nuevo menu en Menu-Historico
            db.child("Menu-Historico").child(menu.getId()).setValue(menu);
            //Lo borro de Menu-Semana
            db = FirebaseDatabase.getInstance().getReference("Menu-Semana");
            db.child(menu.getId()).setValue(null);
        } else {
            //Si el dia de la semana coincide con el dia de la receta lo muestro
            if (dayOfWeek(menu) == this.dia) {
                textView = view.findViewById(id);
                textView.setText(menu.getReceta());
                switch (menu.getComida()) {
                    case "desayuno":
                        addDesayuno.setImageResource(R.drawable.ic_menos);
                        addDesayuno.setTag(R.drawable.ic_menos);
                        completarComida(R.id.imgDesayuno, menu, R.id.nomRecetaDesayuno, view);
                        break;
                    case "almuerzo":
                        addAlmuerzo.setImageResource(R.drawable.ic_menos);
                        addAlmuerzo.setTag(R.drawable.ic_menos);
                        completarComida(R.id.imgAlmuerzo, menu, R.id.nomRecetaAlmuerzo, view);
                        break;
                    case "merienda":
                        addMerienda.setImageResource(R.drawable.ic_menos);
                        addMerienda.setTag(R.drawable.ic_menos);
                        completarComida(R.id.imgMerienda, menu, R.id.nomRecetaMerienda, view);
                        break;
                    case "cena":
                        addCena.setImageResource(R.drawable.ic_menos);
                        addCena.setTag(R.drawable.ic_menos);
                        completarComida(R.id.imgCena, menu, R.id.nomRecetaCena, view);
                        break;
                }
            }
        }
    }

    //Devuelve el dia de la semana en que estamos 0 corresponde a domingo
    public int dayOfWeek(Menu menu) {
        Date currentDate = new Date(menu.getFecha());
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    //La funcion compara la fecha guardada con la fecha actual en milisegundos
    //Si la diferecncia es mayor a 604800000 (que corresponde a una semana) se devuelve true
    public boolean pasoSemana(Menu menu) {
        long millisHoy = System.currentTimeMillis();
        long millesGuardado = menu.getFecha();

        return millisHoy - millesGuardado > 604800000;
    }

    public void completarComida(int idComida, Menu menu, int idImg, View view) {
        TextView nomComida;
        ImageView imv;
        imv = view.findViewById(idComida);
        imv.setVisibility(View.VISIBLE);
        imv.getLayoutParams().height = 250;
        imv.getLayoutParams().width = 250;
        nomComida = view.findViewById(idImg);
        nomComida.setText(menu.getReceta());
    }

    private void sacar(String comida) {
        Bundle paraHome = new Bundle();
        for (Menu m : listaMenu) {
            if (dayOfWeek(m) == dia && m.getComida().equalsIgnoreCase(comida)) {
                paraHome.putString("diaGuardado", "" + dia);
                db = FirebaseDatabase.getInstance().getReference("Menu-Semana");
                db.child(m.getId()).setValue(null);
                findNavController(getView()).navigate(R.id.navigation_home, paraHome);
                //findNavController(getView()).navigateUp();
                //                ;
            }
        }
    }
}