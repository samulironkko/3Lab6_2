package com.example.aruokalistasovellus;

import java.io.Serializable;
import java.util.ArrayList;

public class Meal implements Serializable {

    String titleName;
    String name;
    ArrayList<String> diets;

    public Meal(String titleName, String name, ArrayList<String> diets) {
        this.titleName = titleName;
        this.name = name;
        this.diets = diets;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDiets() {
        return diets;
    }

    public void setDiets(ArrayList<String> diets) {
        this.diets = diets;
    }
}
