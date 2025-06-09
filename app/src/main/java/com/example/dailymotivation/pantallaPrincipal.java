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

        this.ObtenerNombre();     // primero obtenemos el nombre
        this.Inicializar();       // luego lo usamos
    }

    @SuppressLint("SetTextI18n")
    private void Inicializar(){
        TextView meta = findViewById(R.id.textMeta);
        meta.setText("Progreso: " + progreso + "%");

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

