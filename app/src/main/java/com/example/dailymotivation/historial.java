package com.example.dailymotivation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dailymotivation.Adapters.TrainingRecordAdapter;
import com.example.dailymotivation.Entidades.ElementoHistorial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Clase que representa la pantalla de historial de entrenamientos
public class historial extends AppCompatActivity {

    ListView listHistorial; // Componente gráfico para mostrar los entrenamientos
    ArrayList<ElementoHistorial> trainingRecordsList; // Lista de objetos de historial de entrenamiento

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa el modo Edge-to-Edge para una interfaz más moderna
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historial); // Carga el layout de la actividad

        // Ajusta los márgenes automáticamente para que se adapten a la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtiene la referencia del ListView desde el layout
        listHistorial = findViewById(R.id.listHistorial);

        // Verificación por si no se encuentra el ListView (nombre de ID incorrecto o layout equivocado)
        if (listHistorial == null) {
            android.util.Log.e("HISTORIAL_DEBUG", "ListView listHistorial is NULL after findViewById!");
            return;
        }

        // Llama al método para leer los datos desde el archivo CSV
        readTrainingData1();
    }

    // Método que lee el archivo CSV y llena la lista de entrenamientos
    private void readTrainingData1() {
        trainingRecordsList = new ArrayList<>(); // Inicializa la lista

        // Obtiene la ruta al archivo llamado training_log.csv dentro del almacenamiento interno
        File csvFile = new File(getFilesDir(), "training_log.csv");

        // Verifica si el archivo no existe
        if (!csvFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean firstLine = true;

            // Lee línea por línea el contenido del archivo
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Ignora la primera línea (encabezados)
                    continue;
                }

                // Separa la línea en columnas usando coma como delimitador
                List<String> data = new ArrayList<>(List.of(line.split(",")));

                // Asegura que haya al menos 4 columnas (Date, Distance, Time, Type)
                if (data.size() >= 4) {
                    String date = data.get(0);       // Fecha del entrenamiento
                    String distance = data.get(1);   // Distancia recorrida
                    String time = data.get(2);       // Tiempo tomado
                    String type = data.get(3);       // Tipo de ejercicio

                    // Crea un objeto de tipo ElementoHistorial con los datos y lo agrega a la lista
                    trainingRecordsList.add(new ElementoHistorial(
                            date,
                            distance + " km",
                            time + " min",
                            type
                    ));
                }
            }

            // Crea el adaptador personalizado y lo asigna al ListView
            TrainingRecordAdapter adapter = new TrainingRecordAdapter(this, trainingRecordsList);
            listHistorial.setAdapter(adapter);

        } catch (FileNotFoundException e) {
            // Muestra un mensaje si el archivo no se encuentra
            Toast.makeText(this, "Archivo no encontrado: " + csvFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            throw new RuntimeException(e); // Puedes quitar esto si prefieres no cerrar la app
        } catch (IOException e) {
            // Muestra un mensaje si hay un error al leer el archivo
            Toast.makeText(this, "Error al leer el archivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void btnVolver(View view){
        startActivity(new Intent(this, pantallaPrincipal.class));
    }

    /*
    // Método alternativo con datos ficticios para pruebas (comentado)
    private void readTrainingData1() {
        trainingRecordsList = new ArrayList<>();

        // Si el archivo falla o no existe, se pueden usar datos ficticios para mostrar
        if (trainingRecordsList.isEmpty()) {
            trainingRecordsList.add(new ElementoHistorial("Test Date 1", "10 km", "60 min", "Test Type 1"));
            trainingRecordsList.add(new ElementoHistorial("Test Date 2", "5 km", "30 min", "Test Type 2"));
            android.util.Log.d("CSV_DEBUG", "Usando datos de prueba porque la lista estaba vacía.");
        }

        TrainingRecordAdapter adapter = new TrainingRecordAdapter(this, trainingRecordsList);
        listHistorial.setAdapter(adapter);

        if (trainingRecordsList.isEmpty()) {
            Toast.makeText(this, "No hay datos de entrenamiento para mostrar.", Toast.LENGTH_LONG).show();
        } else {
            android.util.Log.d("CSV_DEBUG", "Adaptador configurado con " + trainingRecordsList.size() + " elementos.");
        }
    }
    */
}
