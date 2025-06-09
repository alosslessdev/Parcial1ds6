package com.example.dailymotivation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.Inicializar();
        this.LogueadoActualmente();
    }

    private void Inicializar(){
        nombre = findViewById(R.id.editTextNombre);
    }

    private void LogueadoActualmente(){
        SharedPreferences estaLogueado = getSharedPreferences("estaLogueado", Context.MODE_PRIVATE);
        int estadoLogueado = estaLogueado.getInt("logueado", 0); // leer como int
        if (estadoLogueado == 1){
            startActivity(new Intent(this, pantallaPrincipal.class));
        }
    }


    public void Login(View v){
        String nombreLocal = nombre.getText().toString();

        // Guarda el nombre en Credenciales (por si no está aún)
        SharedPreferences credenciales = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorCred = credenciales.edit();
        editorCred.putString("nombre", nombreLocal);
        editorCred.apply();

        // Luego continúa con el login
        SharedPreferences estaLogueado = getSharedPreferences("estaLogueado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = estaLogueado.edit();
        editor.putInt("logueado", 1);
        editor.putString("nombre", nombreLocal);
        editor.apply();

        startActivity(new Intent(this, pantallaPrincipal.class));
    }

}