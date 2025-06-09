package com.example.dailymotivation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Map;

public class metas extends AppCompatActivity {

    ListView lista;
    String metas[];

    private ImageButton btnGuardarMeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_metas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.Inicializar();
        btnGuardarMeta = findViewById(R.id.btnGuardarMeta);
        btnGuardarMeta.setOnClickListener(this::GuardarMeta);

    }

    private void Inicializar(){
        SharedPreferences sharedPreferences = getSharedPreferences("Metas", Context.MODE_PRIVATE);
        lista = findViewById(R.id.lista);

        // Retrieve all entries from SharedPreferences
        Map<String, ?> allEntries = sharedPreferences.getAll();
        ArrayList<String> metasParaMostrar = new ArrayList<>();

        // Iterate over the map and add string values to your list
        // You might want to filter or sort these based on your needs
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey() instanceof String) {
                metasParaMostrar.add((String) entry.getKey());
            }
            // If you store other types, you might want to handle them too
            // or ensure you only store strings for this list.
        }

        // Use a built-in Android layout for simple list items,
        // or create your own custom layout for list items.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Standard Android layout for a single text item
                metasParaMostrar
        );
        lista.setAdapter(arrayAdapter);
    }


    public void GuardarMeta(View view){
    SharedPreferences sharedPreferences = getSharedPreferences("Metas", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("meta", "correr 50km");
        editor.apply();

    }
}