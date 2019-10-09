package com.example.a3lab6_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ArrayList<String>> arrayLists;

    public ListAdapter(@NonNull Context context, ArrayList<ArrayList<String>> arrayList) {
        super(context, R.layout.single_list_item, arrayList);
        this.context = context;
        this.arrayLists = arrayList;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
        }

        ArrayList<String> meals = arrayLists.get(position);
        TextView textView = convertView.findViewById(R.id.listItem);

        for (int i = 0; i < meals.size(); i++) {
            if (i == 0) {
                textView.setText(meals.get(i));
            }else {
                textView.append("\n" + meals.get(i));
            }
        }

        return convertView;
    }
}
