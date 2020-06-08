package com.example.adefault.Decoration;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PickResult {

    public ArrayList<JsonObject> getPick() {
        return pick;
    }

    public void setPick(ArrayList<JsonObject> pick) {
        this.pick = pick;
    }

    @SerializedName("pick")
    private ArrayList<JsonObject> pick;


}