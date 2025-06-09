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

    // Componentes visuales
    ListView lista;
    String metas[];

    private ImageButton btnGuardarMeta;
    EditText editMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa el diseño EdgeToEdge (pantalla completa moderna)
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_metas);

        // Ajusta el padding para evitar solapamiento con la barra de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Cargar elementos iniciales
        this.Inicializar();

        // Configura botón de guardar meta
        btnGuardarMeta = findViewById(R.id.btnGuardarMeta);
        btnGuardarMeta.setOnClickListener(this::GuardarMeta);

        // Campo de texto para escribir la meta
        editMeta = findViewById(R.id.editMeta);
    }

    // Método para mostrar las metas existentes en el ListView
    private void Inicializar(){
        SharedPreferences sharedPreferences = getSharedPreferences("Metas", Context.MODE_PRIVATE);
        lista = findViewById(R.id.lista);

        ArrayList<String> metasParaMostrar = new ArrayList<>();

        // Recupera el orden de las metas usando claves separadas por comas
        String ordenClaves = sharedPreferences.getString("ordenMetas", "");
        if (!ordenClaves.isEmpty()) {
            String[] claves = ordenClaves.split(",");
            for (String clave : claves) {
                // Obtiene el texto de cada meta usando su clave
                String texto = sharedPreferences.getString(clave + "_texto", null);
                if (texto != null) {
                    metasParaMostrar.add(texto); // Agrega a la lista que se mostrará
                }
            }
        }

        // Adaptador personalizado para mostrar las metas en la lista
        MyCustomAdapter adapter = new MyCustomAdapter(this, R.layout.black_text, metasParaMostrar);
        lista.setAdapter(adapter); // Asigna el adaptador a la lista
    }

    // Método para guardar una nueva meta
    public void GuardarMeta(View view){
        String meta = editMeta.getText().toString();

        // Validación: no se permite guardar metas vacías
        if (meta.isEmpty()) {
            Toast.makeText(this, "Ingresa una meta antes de guardar", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Metas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Genera una clave única con timestamp
        String keyMeta = "meta_" + System.currentTimeMillis();

        // Guarda el texto de la meta y su progreso (inicia en 0)
        editor.putString(keyMeta + "_texto", meta);
        editor.putInt(keyMeta + "_progreso", 0);

        // Marca esta como la última meta registrada
        editor.putString("ultimaMeta", keyMeta);

        // Actualiza el orden de metas
        String ordenActual = sharedPreferences.getString("ordenMetas", "");
        ordenActual = ordenActual + keyMeta + ","; // Agrega nueva clave
        editor.putString("ordenMetas", ordenActual);

        // Guarda los cambios
        editor.apply();

        Toast.makeText(this, "Meta guardada", Toast.LENGTH_SHORT).show();

        // Refresca la lista para mostrar la nueva meta
        Inicializar();
    }
}
