package com.example.dailymotivation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Clase que representa la pantalla de registro de entrenamientos
public class registro extends AppCompatActivity {

    // Nombre del archivo CSV donde se almacenarán los entrenamientos
    private static final String FILE_NAME = "training_log.csv";

    // Botón de guardar entrenamiento
    private ImageButton buttonSaveTraining;

    // Cabecera para el archivo CSV (solo se escribe si el archivo está vacío)
    private static final String CSV_HEADER = "Date,Distance (km),Time (min),Type\n";

    // Campos de entrada del usuario
    private EditText type, distance, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro); // Carga el diseño XML de la pantalla

        // Configura el diseño para que respete las barras del sistema (estado y navegación)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Asigna el botón y define su acción al hacer clic
        buttonSaveTraining = findViewById(R.id.btnGuardar);
        buttonSaveTraining.setOnClickListener(this::saveTrainingData);
    }

    public void btnVolver(View view){
        startActivity(new Intent(this, pantallaPrincipal.class));
    }

    // Método que se ejecuta al presionar el botón para guardar los datos
    public void saveTrainingData(View view) {

        // Vincula los campos de texto a las variables
        distance = findViewById(R.id.editDistancia);
        time = findViewById(R.id.editTiempo);
        type = findViewById(R.id.editTipo);

        // Obtiene los valores ingresados como texto
        String distanciaTexto = this.distance.getText().toString();
        String timeDato = this.time.getText().toString();
        String typeTexto = this.type.getText().toString();

        // Genera la fecha actual en formato yyyy-MM-dd
        String dateDato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Crea la línea que se agregará al CSV con los datos
        String csvLine = String.format("%s, %s, %s, %s", dateDato, distanciaTexto, timeDato, typeTexto);

        // Intenta guardar la información en el archivo interno
        try {
            // Obtiene referencia al archivo de almacenamiento interno
            File file = getBaseContext().getFileStreamPath(FILE_NAME);

            // Abre el archivo en modo append (agregar al final)
            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput(FILE_NAME, Context.MODE_APPEND));

            // Si el archivo está vacío, escribe la cabecera
            if (file.length() == 0) {
                writer.append(CSV_HEADER);
            }

            // Escribe la nueva línea con los datos del entrenamiento
            writer.append(csvLine);
            writer.append("\n");

            // Cierra el archivo
            writer.close();

            // Muestra mensaje de éxito
            Toast.makeText(this, "Training data saved", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            // Si el archivo no se encuentra, muestra mensaje de error
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            // Si hay un error de escritura, muestra mensaje de error
            Toast.makeText(this, "Error saving training data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
