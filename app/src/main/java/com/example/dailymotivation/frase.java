package com.example.dailymotivation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class frase extends AppCompatActivity {

    String frases[] = {
            "No te rindas, aún no has perdido.",
            "El camino hacia el éxito está lleno de obstáculos, pero cada uno te hace más fuerte.",
            "Cree en ti mismo y en todo lo que eres, pues hay algo dentro de ti que es más grande que cualquier obstáculo.",
            "El fracaso no es el final, es una oportunidad para aprender y crecer.",
            "La vida es un viaje, disfruta de cada momento y aprende de cada experiencia."
    };
    //test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}
