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

    // EditText donde el usuario ingresará su nombre
    EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa diseño Edge-to-Edge (pantalla completa moderna)
        EdgeToEdge.enable(this);

        // Establece el layout principal para esta actividad
        setContentView(R.layout.activity_main);

        // Ajusta el padding para respetar las barras del sistema (status bar, navegación, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa componentes de la interfaz
        this.Inicializar();

        // Verifica si el usuario ya está logueado
        this.LogueadoActualmente();
    }

    // Método para vincular el EditText del nombre con su vista
    private void Inicializar(){
        nombre = findViewById(R.id.editTextNombre);
    }

    // Revisa si el usuario ya ha iniciado sesión anteriormente
    private void LogueadoActualmente(){
        SharedPreferences estaLogueado = getSharedPreferences("estaLogueado", Context.MODE_PRIVATE);
        int estadoLogueado = estaLogueado.getInt("logueado", 0); // valor por defecto es 0 (no logueado)

        // Si está logueado, redirige automáticamente a la pantalla principal
        if (estadoLogueado == 1){
            startActivity(new Intent(this, pantallaPrincipal.class));
        }
    }

    // Método que se ejecuta cuando el usuario presiona el botón de login
    public void Login(View v){
        // Obtiene el nombre ingresado en el campo de texto
        String nombreLocal = nombre.getText().toString();

        // Guarda el nombre en SharedPreferences llamado "Credenciales"
        SharedPreferences credenciales = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorCred = credenciales.edit();
        editorCred.putString("nombre", nombreLocal); // almacena el nombre ingresado
        editorCred.apply(); // aplica los cambios

        // Marca al usuario como logueado en "estaLogueado"
        SharedPreferences estaLogueado = getSharedPreferences("estaLogueado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = estaLogueado.edit();
        editor.putInt("logueado", 1); // 1 = logueado
        editor.putString("nombre", nombreLocal); // guarda el nombre también aquí
        editor.apply(); // guarda los cambios

        // Inicia la actividad pantallaPrincipal
        startActivity(new Intent(this, pantallaPrincipal.class));
    }

}
