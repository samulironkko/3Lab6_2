package com.example.a3lab6_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FetchJSON.MyInterface{

    String url;

    ListView listView;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        setUrl();

        TextView dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText(day + "." + month + "." + year);

        FetchJSON fetchJSON = new FetchJSON(this, url, this);
        fetchJSON.start();
    }



    @Override
    public void returnContent(final ArrayList<ArrayList<String>> arrayLists) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new ListAdapter(MainActivity.this, arrayLists));
            }
        });
    }

    public void setUrl() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        Log.d("tag", year + " " + month + " " + day);
        url = "https://www.amica.fi/api/restaurant/menu/day?date=" + year + "-" + month + "-" + day + "&language=en&restaurantPageId=66287";
    }






}
