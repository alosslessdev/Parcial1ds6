package com.example.dailymotivation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

// Clase que representa la pantalla de frases motivadoras
public class frase extends AppCompatActivity {

    // Botón que al presionar muestra una frase motivadora
    Button btnMotivame;

    // Arreglo de frases motivadoras que se mostrarán aleatoriamente
    String frases[] = {
            "No te rindas, aún no has perdido.",
            "El camino hacia el éxito está lleno de obstáculos, pero cada uno te hace más fuerte.",
            "Cree en ti mismo y en todo lo que eres, pues hay algo dentro de ti que es más grande que cualquier obstáculo.",
            "El fracaso no es el final, es una oportunidad para aprender y crecer.",
            "La vida es un viaje, disfruta de cada momento y aprende de cada experiencia."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa la compatibilidad con pantallas sin bordes
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frase); // Carga el diseño de la pantalla

        // Ajusta los márgenes de la vista principal según las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

        // Enlaza el botón desde el layout XML
        btnMotivame = findViewById(R.id.btnMotivame);

        // Establece el evento de clic al botón para mostrar la frase
        btnMotivame.setOnClickListener(this::MostrarFrase);
    }

    // Método que muestra una frase aleatoria en un cuadro de diálogo
    public void MostrarFrase(View view) {
        // Genera un número aleatorio dentro del rango de frases
        Random random = new Random();
        int index = random.nextInt(frases.length); // Índice aleatorio para seleccionar frase

        // Crea y muestra un cuadro de diálogo con la frase seleccionada
        new AlertDialog.Builder(this)
                .setTitle("Frase") // Título del diálogo
                .setMessage(frases[index]) // Mensaje con la frase aleatoria
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss()) // Botón para cerrar
                .show(); // Muestra el diálogo
    }
}
