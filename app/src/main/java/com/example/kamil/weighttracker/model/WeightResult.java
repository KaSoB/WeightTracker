package com.example.kamil.weighttracker.model;

import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Kamil on 11.11.2017.
 */

public class WeightResult {
    private int id;
    private int weightInGrams;
    private long dateLong;
    public WeightResult(int id,int weightInGrams, long dateLong){
        this.id = id;
        this.weightInGrams = weightInGrams;
        this.dateLong = dateLong;
    }
    public WeightResult(int weightInGrams, long dateLong){
        this(0,weightInGrams,dateLong);
    }
    public int getId(){ return id; }
    public int getWeightInGrams(){
        return  weightInGrams;
    }
    public long getDataLong(){
        return dateLong;
    }

    public void setWeightInGrams(int weightInGrams){
        this.weightInGrams = weightInGrams;
    }


    public static int getWeightStringToInt(String text){
        int weightInGrams = 0;
        try{
            weightInGrams = (int) (Float.valueOf(text)*1000);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return weightInGrams;
    }
    public static String getWeightIntToString(int weightInGrams){
        return String.format(Locale.UK, "%.1f",((float)weightInGrams)/1000f);
    }

}
