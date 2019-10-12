package com.example.a3lab6_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FetchJSON.MyInterface, AdapterView.OnItemClickListener, View.OnClickListener {

    String url;

    ListView listView;
    TextView dateTextView;
    FloatingActionButton myFab;
    int yearDate;
    int monthDate;
    int dayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        myFab = findViewById(R.id.myFAB);
        myFab.setOnClickListener(this);

        setUrl();

        dateTextView = findViewById(R.id.dateTextView);


    }



    @Override
    public void returnContent(final ArrayList<ArrayList<Meal>> arrayLists) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new ListAdapter(MainActivity.this, arrayLists));
                dateTextView.setText(dayDate + "." + monthDate + "." + yearDate);
            }
        });
    }

    public void setUrl() {
        if (dayDate == 0) {
            Calendar calendar = Calendar.getInstance();
            yearDate = calendar.get(Calendar.YEAR);
            monthDate = calendar.get(Calendar.MONTH) + 1;
            dayDate = calendar.get(Calendar.DATE);
            Log.d("tag", yearDate + " " + monthDate + " " + dayDate);
        }
        Log.d("tag", yearDate + " " + monthDate + " " + dayDate);
        url = "https://www.amica.fi/api/restaurant/menu/day?date=" + yearDate + "-" + monthDate + "-" + dayDate+ "&language=en&restaurantPageId=66287";
        fetchJSON();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("click", "clicked" + position);

    }

    @Override
    public void onClick(View v) {

        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog date = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yearDate = year;
                monthDate = month + 1;
                dayDate = dayOfMonth;

                setUrl();
            }
        }, yearDate, monthDate - 1, dayDate);
        date.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
        date.show();

    }

    public void fetchJSON() {

        FetchJSON fetchJSON = new FetchJSON(this, url, this);
        fetchJSON.start();

    }

}
