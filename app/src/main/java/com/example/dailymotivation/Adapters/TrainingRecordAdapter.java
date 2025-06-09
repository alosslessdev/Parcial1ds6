package com.example.dailymotivation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dailymotivation.Entidades.ElementoHistorial;
import com.example.dailymotivation.R;

import java.util.ArrayList;
import java.util.List;

public class TrainingRecordAdapter extends ArrayAdapter<ElementoHistorial> {

    List<ElementoHistorial> trainingRecords;

    public TrainingRecordAdapter(@NonNull Context context,  @NonNull List<ElementoHistorial> objects) {
        super(context, R.layout.listview_template);
        trainingRecords = objects;

        if (this.trainingRecords == null) {
            android.util.Log.e("ADAPTER_DEBUG", "Constructor: Passed 'objects' list is NULL");
            this.trainingRecords = new ArrayList<>(); // Initialize to empty to avoid later NullPointerExceptions
        } else {
            android.util.Log.d("ADAPTER_DEBUG", "Constructor: Passed list size: " + this.trainingRecords.size());
        }

    }

    @Override
    public int getCount() {
        if (trainingRecords == null) {
            android.util.Log.e("ADAPTER_DEBUG", "getCount: trainingRecords list is NULL");
            return 0;
        }
        int count = trainingRecords.size();
        android.util.Log.d("ADAPTER_DEBUG", "getCount() returning: " + count);
        return count;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View item = convertView;

        // Get the training record information
        android.util.Log.d("ADAPTER_DEBUG", "getView for position: " + position + ", Data: " + trainingRecords.get(position).getFecha());
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View item = inflater.inflate(R.layout.listview_template, parent, false);

        item = LayoutInflater.from(getContext()).inflate(R.layout.listview_template, parent, false);


        // Build a string to display the ElementoHistorial information
            // You can customize this based on how you want to display the dat

            TextView textview1 = item.findViewById(R.id.textView1);
            textview1.setText(trainingRecords.get(position).getFecha());

            TextView textview2 = item.findViewById(R.id.textView2);
            textview2.setText(trainingRecords.get(position).getDistancia());

            TextView textview3 = item.findViewById(R.id.textView3);
            textview3.setText(trainingRecords.get(position).getTiempo());

            TextView textview4 = item.findViewById(R.id.textView4);
            textview4.setText(trainingRecords.get(position).getTipo());

            // If you have a custom layout with multiple TextViews, you would set them individually here.
            // For example, if you create a custom layout file (e.g., custom_list_item.xml) with multiple TextViews
            // for date, distance, time, and type.

        return item;
    }
}