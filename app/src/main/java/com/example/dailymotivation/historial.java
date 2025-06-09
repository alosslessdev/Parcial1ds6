

package com.example.dailymotivation;

import android.os.Bundle;
import android.os.Environment;
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

public class historial extends AppCompatActivity {

    ListView listHistorial;
    ArrayList<ElementoHistorial> trainingRecordsList; // Changed to ArrayList<ElementoHistorial>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historial);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listHistorial = findViewById(R.id.listHistorial); // Initialize listHistorial
        if (listHistorial == null) {
            android.util.Log.e("HISTORIAL_DEBUG", "ListView listHistorial is NULL after findViewById!");
            // If this happens, your ListView ID in XML is wrong or not in activity_historial.xml
            return;
        }
        readTrainingData1();
    }

    private void readTrainingData1() { // Renamed from readTrainingData
            trainingRecordsList = new ArrayList<>(); // Initialize trainingRecordsList
            // Use training_data.csv instead of training_log.csv
            File csvFile = new File(getFilesDir(), "training_log.csv");

            if (!csvFile.exists()) {
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) { // Corrected FileReader

                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    List<String> data = new ArrayList<>(List.of(line.split(","))); // Use ArrayList
                    // Adjust data parsing based on the structure of training_data.csv
                    // Assuming training_data.csv has: Date,Distance,Time,Type
                    if (data.size() >= 3) { // Ensure at least 4 columns
                        String date = data.get(0);
                        String distance = data.get(1);
                        String time = data.get(2);
                        String type = data.get(3);

                        trainingRecordsList.add(new ElementoHistorial(date, distance + " km", time + " min", type)); // Create ElementoHistorial object
                    }
                }

                TrainingRecordAdapter adapter = new TrainingRecordAdapter(this, trainingRecordsList);
                listHistorial.setAdapter(adapter);

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "File not found: " + csvFile.getAbsolutePath(), Toast.LENGTH_LONG).show(); // More informative message
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                Toast.makeText(this, "Read error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
                // Consider more graceful error handling than throwing RuntimeException
            }
    }

//    private void readTrainingData1() {
//        trainingRecordsList = new ArrayList<>();
//        // trainingRecordsList.add(new ElementoHistorial("2023-01-01", "5 km", "30 min", "Run"));
//        // trainingRecordsList.add(new ElementoHistorial("2023-01-02", "3 km", "20 min", "Jog"));
//        // Remove this for actual CSV reading later
//        // /*
//        File csvFile = new File(getFilesDir(), "training_data.csv");
//        // ... rest of your CSV reading logic ...
//        // */
//
//        // TEMP: Add dummy data
//        if (trainingRecordsList.isEmpty()) { // Only add if CSV loading failed or is commented out
//            trainingRecordsList.add(new ElementoHistorial("Test Date 1", "10 km", "60 min", "Test Type 1"));
//            trainingRecordsList.add(new ElementoHistorial("Test Date 2", "5 km", "30 min", "Test Type 2"));
//            android.util.Log.d("CSV_DEBUG", "Using dummy data as trainingRecordsList was empty.");
//        }


//        TrainingRecordAdapter adapter = new TrainingRecordAdapter(this, trainingRecordsList);
//        listHistorial.setAdapter(adapter);
//        if (trainingRecordsList.isEmpty()) {
//            Toast.makeText(this, "No training data to display.", Toast.LENGTH_LONG).show();
//            android.util.Log.w("CSV_DEBUG", "Training records list is empty after attempting to load/add dummy data.");
//        } else {
//            android.util.Log.d("CSV_DEBUG", "Adapter set with " + trainingRecordsList.size() + " items.");
//        }
//    }

}