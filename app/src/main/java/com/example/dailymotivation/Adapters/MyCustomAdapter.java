package com.example.dailymotivation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.dailymotivation.R;

import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mData;
    private int mResource; // Store your custom layout resource ID

    public MyCustomAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource; // e.g., R.layout.my_custom_list_item
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        String currentItem = mData.get(position);

        TextView textView = listItemView.findViewById(R.id.customTextView); // ID from your custom layout
        if (textView != null) {
            textView.setText(currentItem);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            // Or: textView.setTextColor(Color.parseColor("#00FF00")); // Green
        }

        return listItemView;
    }
}