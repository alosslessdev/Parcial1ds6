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

    // Variables para almacenar datos del usuario y progreso de metas
    String nombre;
    int progreso;
    String metaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa modo de diseño pantalla completa moderna
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_principal);

        // Ajusta padding de la vista para evitar solaparse con las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Carga datos desde SharedPreferences
        this.ObtenerDatos();

        // Actualiza la interfaz con los datos cargados
        this.Inicializar();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Al volver a la pantalla principal, actualiza los datos y la vista
        ObtenerDatos();
        Inicializar();
    }

    // Método para obtener datos guardados (nombre del usuario, meta actual y progreso)
    private void ObtenerDatos(){
        SharedPreferences verNombre = getSharedPreferences("estaLogueado", Context.MODE_PRIVATE);
        nombre = verNombre.getString("nombre", "Usuario"); // obtiene el nombre

        SharedPreferences metasPrefs = getSharedPreferences("Metas", Context.MODE_PRIVATE);
        String ultimaClave = metasPrefs.getString("ultimaMeta", null);

        if (ultimaClave != null) {
            // Si hay una meta guardada, recupera su texto y progreso
            metaActual = metasPrefs.getString(ultimaClave + "_texto", "No tienes metas registradas");
            progreso = metasPrefs.getInt(ultimaClave + "_progreso", 0);
        } else {
            // Si no hay metas guardadas
            metaActual = "No tienes metas registradas";
            progreso = 0;
        }
    }

    // Muestra el nombre del usuario y los datos de su meta actual
    @SuppressLint("SetTextI18n")
    private void Inicializar(){
        TextView meta = findViewById(R.id.textMeta);
        meta.setText("Meta: " + metaActual + "\nProgreso: " + progreso + "%");

        TextView saludo = findViewById(R.id.textSaludo);
        saludo.setText("¡Hola " + nombre + ", listo para comenzar?!");
    }

    // Método para aumentar el progreso en 10%, sin pasar de 100%
    public void aumentarProgreso(View view){
        if (progreso < 100){
            progreso += 10;
            if(progreso > 100) progreso = 100;

            guardarProgreso(); // guarda el nuevo valor
            actualizarVista(); // actualiza el texto en pantalla
        }
    }

    // Método para reducir el progreso en 10%, sin bajar de 0%
    public void reducirProgreso(View view){
        if (progreso > 0){
            progreso -= 10;
            if(progreso < 0) progreso = 0;

            guardarProgreso(); // guarda el nuevo valor
            actualizarVista(); // actualiza el texto en pantalla
        }
    }

    // Guarda el valor del progreso actual en SharedPreferences
    private void guardarProgreso(){
        SharedPreferences metasPrefs = getSharedPreferences("Metas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = metasPrefs.edit();

        String ultimaClave = metasPrefs.getString("ultimaMeta", null);
        if (ultimaClave != null) {
            editor.putInt(ultimaClave + "_progreso", progreso);
            editor.apply(); // guarda los cambios
        }
    }

    // Actualiza el texto de la interfaz según los datos actuales
    @SuppressLint("SetTextI18n")
    private void actualizarVista(){
        TextView meta = findViewById(R.id.textMeta);
        meta.setText("Meta: " + metaActual + "\nProgreso: " + progreso + "%, " + "Faltan: " + (100-progreso) + "%");

        TextView saludo = findViewById(R.id.textSaludo);
        saludo.setText("¡Hola " + nombre + ", listo para comenzar?!");
    }

    // Método redundante (no es usado actualmente)
    public void ObtenerNombre(){
        SharedPreferences verNombre = getSharedPreferences("estaLogueado", Context.MODE_PRIVATE);
        nombre = verNombre.getString("nombre", "Usuario");
    }

    // Abre la pantalla de registro de entrenamientos
    public void abrirEntrenamiento(View view){
        startActivity(new Intent(this, registro.class));
    }

    // Abre la pantalla de metas
    public void abrirMetas(View view){
        startActivity(new Intent(this, metas.class));
    }

    // Abre la pantalla de frases motivadoras
    public void abrirFrases(View view){
        startActivity(new Intent(this, frase.class));
    }

    // Abre la pantalla del historial
    public void abrirHistorial(View view){
        startActivity(new Intent(this, historial.class));
    }

    public void btnVolver(View view){
        startActivity(new Intent(this, MainActivity.class));
    }


}
