package com.example.dailymotivation;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class historial extends AppCompatActivity {

ListView listHistorial;
    List<String> trainingRecords;
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

        readTrainingData1();
    }

    private void readTrainingData1() {
            trainingRecords = new ArrayList<>();
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File csvFile = new File(storageDir, "training_history.csv");

            if (!csvFile.exists()) {
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {

                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] data = line.split(",");
                    if (data.length <= 4) {
                        String date = data[0];
                        String distance = data[1];
                        String time = data[2];
                        String type = data[3];

                        trainingRecords.add(date + " - " + distance + " km - " + time + " min - " + type);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, trainingRecords);
                listHistorial.setAdapter(adapter);

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                Toast.makeText(this, "Read error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    }

}