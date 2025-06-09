package com.example.dailymotivation;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
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
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class registro extends AppCompatActivity {
    private static final String FILE_NAME = "training_log.csv";

    private ImageButton buttonSaveTraining;
    private static final String CSV_HEADER = "Date,Distance (km),Time (min),Type\n";

    private EditText type, distance, time;


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

    public void saveTrainingData(View view) {

        distance = findViewById(R.id.editDistancia);
        time = findViewById(R.id.editTiempo);
        type = findViewById(R.id.editTipo);

        String distanciaTexto = this.distance.getText().toString();
        String timeDato = this.time.getText().toString();
        String typeTexto = this.type.getText().toString();


        String dateDato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
       String csvLine = String.format("%s, %s, %s, %s", dateDato, distanciaTexto, timeDato, typeTexto);

//       File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
//       if (!storageDir.exists()) {
//           storageDir.mkdirs();
//       }

            try {
                File file = getBaseContext().getFileStreamPath(FILE_NAME);
                OutputStreamWriter writer = new OutputStreamWriter(openFileOutput(FILE_NAME, Context.MODE_APPEND));

                // Check if the file is empty to write the header
                if (file.length() == 0) {
                    writer.append(CSV_HEADER);
                }
                    writer.append(csvLine);
                    writer.append("\n");
                    writer.close();
                    Toast.makeText(this, "Training data saved", Toast.LENGTH_SHORT).show();
            }catch (FileNotFoundException e){
                Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            catch (IOException e) {
            Toast.makeText(this, "Error saving training data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            }
    }
}