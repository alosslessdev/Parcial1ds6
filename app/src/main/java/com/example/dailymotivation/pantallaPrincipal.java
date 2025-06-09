package com.example.dailymotivation;

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
        this.Inicializar();
        this.ObtenerNombre();
    }

    private void Inicializar(){
        TextView meta = (TextView)findViewById(R.id.textMeta);
        meta.setText("Progreso: " + progreso + "%");
    }
    public void ObtenerNombre(){
        SharedPreferences verNombre = getSharedPreferences("estaLogueado", Context.MODE_PRIVATE);
        nombre = verNombre.getString(nombre, "");
//test
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

