package com.example.a3lab6_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

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
    TextView titleTextView;
    FloatingActionButton myFab;
    ArrayList<ArrayList<Meal>> mealArrayLists;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    int yearDate;
    int monthDate;
    int dayDate;
    int restaurantId = 66287;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        titleTextView = findViewById(R.id.titleTextView);

        myFab = findViewById(R.id.myFAB);
        myFab.setOnClickListener(this);

        setUrl();

        dateTextView = findViewById(R.id.dateTextView);

        drawerLayout = findViewById(R.id.activity_main);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView)findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.kotkanpoika:
                        //Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();
                        restaurantId = 66287;
                        titleTextView.setText("Ravintola Kotkanpoika");
                        break;
                    case R.id.wallu:
                        //Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                        restaurantId = 66275;
                        titleTextView.setText("Ravintola Wallu");
                        break;
                    case R.id.alwari:
                        //Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                        restaurantId = 66264;
                        titleTextView.setText("Ravintola Alwari");
                        break;
                    case R.id.fasaani:
                        //Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                        restaurantId = 66302;
                        titleTextView.setText("Ravintola Fasaani");
                        break;
                    default:
                        return true;
                }
                setUrl();
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;

            }
        });

    }



    @Override
    public void returnContent(final ArrayList<ArrayList<Meal>> arrayLists) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mealArrayLists = arrayLists;
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
        url = "https://www.amica.fi/api/restaurant/menu/day?date=" + yearDate + "-" + monthDate + "-" + dayDate + "&language=fi&restaurantPageId=" + restaurantId;
        fetchJSON();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("click", "clicked" + position);
        Intent intent = new Intent(this, SingleMealActivity.class);
        intent.putExtra("MealList", mealArrayLists.get(position));
        startActivity(intent);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

}
