package com.example.dailymotivation;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class registro extends AppCompatActivity {
    private static final String FILE_NAME = "training_log.txt";

    private ImageButton buttonSaveTraining;
    private static final String CSV_HEADER = "Date,Distance (km),Time (min),Type";

    private String type, distance, time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Edge-to-edge UI setup
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonSaveTraining = findViewById(R.id.btnGuardar);
        buttonSaveTraining.setOnClickListener(this::saveTrainingData);
    }

    public void saveTrainingData(View view)  {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
       String csvLine = String.format("%s, %s, %s, %s", date, distance, time, type);

       File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
       if (!storageDir.exists()) {
           storageDir.mkdirs();
       }

       File csvfile = new File(storageDir, FILE_NAME);

        try (FileWriter writer =new FileWriter(FILE_NAME, true)) {
            if (getFileStreamPath(FILE_NAME).length() == 0){
            writer.append("Date, Distance (km), Time (min), Type\n");
        }
            writer.append(csvLine);
            writer.close();
            Toast.makeText(this, "Training data saved", Toast.LENGTH_SHORT).show();
    } catch (IOException e) {
        Toast.makeText(this, "Error saving training data", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
        }
    }
}