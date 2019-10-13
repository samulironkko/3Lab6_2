package com.example.aruokalistasovellus;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FetchJSON extends Thread {

  String url;
  Context context;


  public interface MyInterface {
    void returnContent(ArrayList<ArrayList<Meal>> arrayLists);
  }

  public FetchJSON(MyInterface myInterface, String u, Context c) {
    callBackInterface = myInterface;
    url = u;
    context = c;
  }

  MyInterface callBackInterface = null;

  public void run() {
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {

        try {

          ArrayList<ArrayList<Meal>> arrayLists = new ArrayList<>();
          ArrayList<Meal> mealArrayList = new ArrayList<>();
          String titleName;
          String name = "";
          ArrayList<String> dietsList = new ArrayList<>();

          JSONObject lunchMenu = response.getJSONObject("LunchMenu");
          JSONArray setMenus = lunchMenu.getJSONArray("SetMenus");
          for (int i = 0; i < setMenus.length(); i++) {
            mealArrayList = new ArrayList<>();
            JSONObject lunch = setMenus.getJSONObject(i);
            titleName = lunch.getString("Name");

            JSONArray meals = lunch.getJSONArray("Meals");
            for (int a = 0; a < meals.length(); a++) {
              JSONObject singleMeal = meals.getJSONObject(a);
              name = singleMeal.getString("Name");

              JSONArray diets = singleMeal.getJSONArray("Diets");
              dietsList = new ArrayList<>();
              for (int l = 0; l < diets.length(); l++) {
                dietsList.add(diets.getString(l));
              }
              Meal meal = new Meal(titleName, name, dietsList);
              mealArrayList.add(meal);
            }
            arrayLists.add(mealArrayList);
          }

          callBackInterface.returnContent(arrayLists);


/*
          JSONObject lunchMenu = response.getJSONObject("LunchMenu");
          JSONArray setMenus = lunchMenu.getJSONArray("SetMenus");
          ArrayList<ArrayList<String>> aArrayList = new ArrayList<>();
          for (int i = 0; i < setMenus.length(); i++) {
            JSONObject lunch = setMenus.getJSONObject(i);
            JSONArray meals = lunch.getJSONArray("Meals");
            ArrayList<String> singleMeals = new ArrayList<>();
            for (int a = 0; a < meals.length(); a++) {
              JSONObject singleMeal = meals.getJSONObject(a);
              singleMeals.add(singleMeal.getString("Name"));
            }
            aArrayList.add(singleMeals);
          }
          callBackInterface.returnContent(aArrayList);
*/
        } catch (JSONException e) {
          e.printStackTrace();
        }

      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

      }
  });
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(jsonObjectRequest);
  }

}
