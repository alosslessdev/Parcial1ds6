package com.example.dailymotivation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dailymotivation.Adapters.MyCustomAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class metas extends AppCompatActivity {

    ListView lista;
    String metas[];

    private ImageButton btnGuardarMeta;
    EditText editMeta;


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
        editMeta = findViewById(R.id.editMeta);

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
            if (entry.getValue() instanceof String) {
                metasParaMostrar.add((String) entry.getValue());
            }
            // If you store other types, you might want to handle them too
            // or ensure you only store strings for this list.
        }

        // Use a built-in Android layout for simple list items,
        // or create your own custom layout for list items.
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                metasParaMostrar
//        );
//        lista.setAdapter(arrayAdapter);

        MyCustomAdapter adapter = new MyCustomAdapter(this, R.layout.black_text, metasParaMostrar);
        lista.setAdapter(adapter);
    }


    public void GuardarMeta(View view){
        String meta = editMeta.getText().toString();
        if (meta.isEmpty()) {
            Toast.makeText(this, "Ingresa una meta antes de guardar", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Metas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Guardamos la meta como una entrada con timestamp para historial
        String keyMetaHistorial = "meta_" + System.currentTimeMillis();
        editor.putString(keyMetaHistorial, meta);

        // Guardamos esta meta como la meta actual
        editor.putString("metaActual", meta);

        // Guardamos el progreso actual antes de iniciar esta meta nueva (0%)
        editor.putInt("progresoActual", 0);

        editor.apply();
        Toast.makeText(this, "Meta guardada", Toast.LENGTH_SHORT).show();
    }
}