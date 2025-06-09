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

        ArrayList<String> metasParaMostrar = new ArrayList<>();

        String ordenClaves = sharedPreferences.getString("ordenMetas", "");
        if (!ordenClaves.isEmpty()) {
            String[] claves = ordenClaves.split(",");
            for (String clave : claves) {
                String texto = sharedPreferences.getString(clave + "_texto", null);
                if (texto != null) {
                    metasParaMostrar.add(texto);
                }
            }
        }

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
        String keyMeta = "meta_" + System.currentTimeMillis();

        // Guardamos texto y progreso por separado
        editor.putString(keyMeta + "_texto", meta);
        editor.putInt(keyMeta + "_progreso", 0);

        // Guardamos esta como la última meta agregada
        editor.putString("ultimaMeta", keyMeta);

        // Guardar orden
        String ordenActual = sharedPreferences.getString("ordenMetas", "");
        ordenActual = ordenActual + keyMeta + ",";
        editor.putString("ordenMetas", ordenActual);


        editor.apply();
        Toast.makeText(this, "Meta guardada", Toast.LENGTH_SHORT).show();

        // Opcional: refrescar la lista si quieres
        Inicializar();
    }

}