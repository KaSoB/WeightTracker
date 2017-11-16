package com.example.kamil.weighttracker.model;

import android.util.Log;

import java.util.Locale;

/**
 * Created by Kamil on 15.11.2017.
 */

public class Weight {

    public enum Unit { // kilogram | gram | pounds
        kg, g, lbs;
        @Override
        public String toString() {
            return "["+ super.toString() + "]";
        }
    }

    private final static float PoundToGramRatio = 0.0022046f;

    private int weightInGrams; // all data will be stored in grams

    public Weight(int weightValue, Unit unit){
        setValue(weightValue,unit);
    }
    public int getWeight(){
        return weightInGrams;
    }
    public int getWeight(Unit unit){
        switch (unit){
            case lbs:
                return (int)(weightInGrams / PoundToGramRatio);
            case g:
                return weightInGrams;
            case kg:
                return weightInGrams * 1000;
            default:
                Log.e("Unknown enum", "Unknown enum WeightUnit");
                return getWeight(Unit.g);
        }
    }
    public void setValue(int weightValue, Unit unit){
        switch (unit){
            case lbs:
                this.weightInGrams = (int)(weightValue / PoundToGramRatio);
                break;
            case g:
                this.weightInGrams = weightValue;
                break;
            case kg:
                this.weightInGrams = weightValue * 1000;
                break;
            default:
                Log.e("Unknown enum", "Unknown enum WeightUnit");
                setValue(weightValue, Unit.g);
                break;
        }
    }
    public String toString(Unit unit) {
        float value;
        switch (unit){
            case lbs:
                value = weightInGrams * PoundToGramRatio;
                break;
            case g:
                value = weightInGrams;
                break;
            case kg:
                value = weightInGrams / 1000f;
                break;
            default:
                Log.e("Unknown enum", "Unknown enum WeightUnit");
                return toString(Unit.g);
        }
        return String.format(Locale.UK, "%.1f", value);
    }
    /**
     @return weight in grams
     */
    public static int valueOf(String text, Unit unit){
        int weightInGrams = 0;
        try{
            switch (unit){
                case lbs:
                    weightInGrams = (int)(Float.valueOf(text) / PoundToGramRatio);
                    break;
                case g:
                    weightInGrams = Integer.valueOf(text);
                    break;
                case kg:
                    weightInGrams = (int) (Float.valueOf(text)*1000);
                    break;
                default:
                    Log.e("Unknown enum", "Unknown enum WeightUnit");
                    break;
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return weightInGrams;
    }

}