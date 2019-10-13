package com.example.aruokalistasovellus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SingleListAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Meal> mealArrayList;

    public SingleListAdapter(@NonNull Context context, ArrayList<Meal> mealList) {
        super(context, 0, mealList);
        this.context = context;
        this.mealArrayList = mealList;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.single_meal_item, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.singleName);
        nameTextView.setText(mealArrayList.get(position).getName());

        ArrayList<String> diets = mealArrayList.get(position).diets;
        TextView dietsTextView = convertView.findViewById(R.id.singleDiets);

        for (int i = 0; i < diets.size(); i++) {
            if (i == 0) {
                dietsTextView.setText(diets.get(i));
            } else {
                dietsTextView.append(", " + diets.get(i));
            }
        }



        return convertView;
    }
}
