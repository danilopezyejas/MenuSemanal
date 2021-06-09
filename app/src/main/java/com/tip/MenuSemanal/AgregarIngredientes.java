package com.tip.MenuSemanal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Clases.Ingrediente;
import Enumeracion.unidades;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarIngredientes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarIngredientes extends Fragment {

    private static final String ARG_NOMBRE = "paramNom";
    private static final String ARG_PRECIO = "paramPre";
    private static final String ARG_CANTIDAD = "paramCant";
    private static final String ARG_UNIDAD = "paramUni";
    private static final String ARG_ID = "id";

    private String paramNom;
    private String paramPre;
    private String paramCant;
    private String paramUni;
    private String id;

    private Spinner unidadSelecionada;

    DatabaseReference db;

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    private  EditText etNombre;
    private EditText etPrecio;
    EditText etCantidad;
    Spinner spUnidad;
    Button agregar;

    public AgregarIngredientes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param paramNom Parameter 1.
     * @param paramPre Parameter 2.
     * @param paramCant Parameter 3.
     * @param paramUni Parameter 4.
     *  @param id Parameter 4.
     * @return A new instance of fragment AgregarIngredientes.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarIngredientes newInstance(String paramNom, String paramPre, String paramCant, String paramUni, String id) {
        AgregarIngredientes fragment = new AgregarIngredientes();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, paramNom);
        args.putString(ARG_PRECIO, paramPre);
        args.putString(ARG_CANTIDAD, paramCant);
        args.putString(ARG_UNIDAD, paramUni);
        args.putString(ARG_ID, id);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paramNom = getArguments().getString(ARG_NOMBRE);
            paramPre = getArguments().getString(ARG_PRECIO);
            paramCant = getArguments().getString(ARG_CANTIDAD);
            paramUni = getArguments().getString(ARG_UNIDAD);
            id = getArguments().getString(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseDatabase.getInstance().getReference();

        View view =  inflater.inflate(R.layout.fragment_agregar_ingredientes, container, false);
        unidadSelecionada = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<unidades> u = new ArrayAdapter<unidades>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, unidades.values());
        unidadSelecionada.setAdapter(u);

        etNombre = view.findViewById(R.id.nombreIngrediente);
        etPrecio = view.findViewById(R.id.precioIngrediente);
        etCantidad = view.findViewById(R.id.etCantidad);
        spUnidad = (Spinner) view.findViewById(R.id.spinner);

        etNombre.setText(paramNom);
        etPrecio.setText(paramPre);
        etCantidad.setText(paramCant);
        spUnidad.setSelection(getIndex(spUnidad, paramUni));


        TextView micro = (TextView) view.findViewById(R.id.idMicroIngre);
        micro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradaVoz();
            }
        });

        agregar = (Button) view.findViewById(R.id.btnAceptarIngredientes);
        if (getArguments() != null) {
            agregar.setText("Modificar");
        }
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    //Me aseguro de que el usuario alla completado todos los campos
                    if (!isEmpty(etNombre) && !isEmpty(etPrecio) && !isEmpty(etCantidad)) {
                        String nombre = etNombre.getText().toString();
                        float precio = Float.parseFloat(etPrecio.getText().toString());
                        int cantidad = Integer.parseInt(etCantidad.getText().toString());
                        String unidad = spUnidad.getSelectedItem().toString();

                        if (agregar.getText().equals("Agregar")) {
                            //Creo el id (me lo proporciona firebase)
                            String idIngrediente = db.push().getKey();

                            Ingrediente newIngrediente = new Ingrediente(idIngrediente, nombre, precio, cantidad, unidad);

                            //lo agrego a la base de datos

                            db.child("Ingredientes").child(idIngrediente).setValue(newIngrediente).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task2) {
                                    //Compruebo si se agrego bien a la base
                                    if (task2.isComplete()) {
                                        Navigation.findNavController(view).navigate(R.id.ir_a_altaIngredientes);
                                        closeKeyBoard(view);
                                    } else {
                                        Toast.makeText(getActivity(), "Ocurrio un error!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Ingrediente newIngrediente = new Ingrediente(id, nombre, precio, cantidad, unidad);
                            db.child(id).setValue(newIngrediente).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task3) {
                                    //Compruebo si se agrego bien a la base
                                    if (task3.isComplete()) {
                                        Navigation.findNavController(view).navigate(R.id.ir_a_altaIngredientes);
                                        closeKeyBoard(view);
                                        Toast.makeText(getActivity(), "Se modifico!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Ocurrio un error!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        });
                    }
                    } else {
                        Toast.makeText(getActivity(), "Debe completar todos los campos!", Toast.LENGTH_SHORT).show();
                    }

            }
        });

        return view;
    }

    //esto es para convertir la entrada de voz a texto
    private void iniciarEntradaVoz() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Escuchando ingrediente ....");

        try {
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK && data!=null){
                    ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etNombre.setText(resultado.get(0));
                }
            }
        }
    }

    //Para liberar el teclado
    private void closeKeyBoard(View view){
        view = getActivity().getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

//    Devuelve la posicion de la unidad seleccionada
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


}