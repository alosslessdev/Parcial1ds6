package com.example.dailymotivation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pantallaPrincipal extends AppCompatActivity {

    String nombre;
    int progreso;
    String metaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_principal);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.ObtenerDatos();
        this.Inicializar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ObtenerDatos();
        Inicializar();
    }


    private void ObtenerDatos(){
        SharedPreferences verNombre = getSharedPreferences("estaLogueado", Context.MODE_PRIVATE);
        nombre = verNombre.getString("nombre", "Usuario");

        SharedPreferences metasPrefs = getSharedPreferences("Metas", Context.MODE_PRIVATE);

        String ultimaClave = metasPrefs.getString("ultimaMeta", null);
        if (ultimaClave != null) {
            metaActual = metasPrefs.getString(ultimaClave + "_texto", "No tienes metas registradas");
            progreso = metasPrefs.getInt(ultimaClave + "_progreso", 0);
        } else {
            metaActual = "No tienes metas registradas";
            progreso = 0;
        }
    }


    @SuppressLint("SetTextI18n")
    private void Inicializar(){
        TextView meta = findViewById(R.id.textMeta);
        meta.setText("Meta: " + metaActual + "\nProgreso: " + progreso + "%");

        TextView saludo = findViewById(R.id.textSaludo);
        saludo.setText("¡Hola " + nombre + ", listo para comenzar?!");
    }

    public void aumentarProgreso(View view){
        if (progreso < 100){
            progreso += 10;
            if(progreso > 100) progreso = 100;
            guardarProgreso();
            actualizarVista();
        }
    }

    public void reducirProgreso(View view){
        if (progreso > 0){
            progreso -= 10;
            if(progreso < 0) progreso = 0;
            guardarProgreso();
            actualizarVista();
        }
    }

    private void guardarProgreso(){
        SharedPreferences metasPrefs = getSharedPreferences("Metas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = metasPrefs.edit();

        String ultimaClave = metasPrefs.getString("ultimaMeta", null);
        if (ultimaClave != null) {
            editor.putInt(ultimaClave + "_progreso", progreso);
            editor.apply();
        }
    }

    @SuppressLint("SetTextI18n")
    private void actualizarVista(){
        TextView meta = findViewById(R.id.textMeta);
        meta.setText("Meta: " + metaActual + "\nProgreso: " + progreso + "%");

        TextView saludo = findViewById(R.id.textSaludo);
        saludo.setText("¡Hola " + nombre + ", listo para comenzar?!");
    }


    public void ObtenerNombre(){
        SharedPreferences verNombre = getSharedPreferences("estaLogueado", Context.MODE_PRIVATE);
        nombre = verNombre.getString("nombre", "Usuario");
    }



    public void abrirEntrenamiento(View view){
        startActivity(new Intent(this, registro.class));
    }

    public void abrirMetas(View view){
        startActivity(new Intent(this, metas.class));
    }

    public void abrirFrases(View view){
        startActivity(new Intent(this, frase.class));
    }

    public void abrirHistorial(View view){
        startActivity(new Intent(this, historial.class));
    }

}

