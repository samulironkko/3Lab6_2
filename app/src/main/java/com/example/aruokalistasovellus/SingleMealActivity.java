package com.example.aruokalistasovellus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SingleMealActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_meal);

        ArrayList<Meal> mealArrayList = new ArrayList<>();

        Intent intent = getIntent();
        mealArrayList = (ArrayList<Meal>) intent.getSerializableExtra("MealList");

        ListView listView = findViewById(R.id.singleListView);
        listView.setAdapter(new SingleListAdapter(this, mealArrayList));


    }
}
