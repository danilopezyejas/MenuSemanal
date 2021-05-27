package com.tip.MenuSemanal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class AuthActivity extends AppCompatActivity {

    private Button singUpButton;
    private EditText email;
    private EditText pasw;

    FirebaseAuth mAuth;
    DatabaseReference aDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        singUpButton = (Button) findViewById(R.id.singUpButton);
        email = (EditText) findViewById(R.id.emailEditText);
        pasw = (EditText) findViewById(R.id.passwordEditText);

        //Instancia de usuario de firebase
        mAuth = FirebaseAuth.getInstance();
        aDatabase = FirebaseDatabase.getInstance().getReference();

        setup();
    }

    private void setup(){
        String title = "Autenticación";

        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().isEmpty() && !pasw.getText().toString().isEmpty()){
                    if(pasw.getText().toString().length() >= 6) {
                        login();
                    } else {
                    Toast.makeText(AuthActivity.this, "El password debe tener al menos 6 caracteres.",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AuthActivity.this, "Debe completar los campos.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void login(){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),pasw.getText().toString()).addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("Nombre",email.getText().toString());
                    map.put("Password",pasw.getText().toString());

                    String id = mAuth.getCurrentUser().getUid();
                    aDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task2) {
                            if(task2.isComplete()){
                                //Guardo el id del usuario en las sharedPreferences
                                SharedPreferences preferences = getApplicationContext().getSharedPreferences("usuario", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("idUsuario",id);
                                editor.commit();

                                startActivity(new Intent(AuthActivity.this,MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(AuthActivity.this, "Ocurrio un error.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else{
                    Toast.makeText(AuthActivity.this, "No se pudo registrar este usuario.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





}