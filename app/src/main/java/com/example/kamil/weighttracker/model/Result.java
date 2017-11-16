package com.example.kamil.weighttracker.model;


/**
 * Created by Kamil on 11.11.2017.
 */

public class Result {
    private int id;
    private long dateLong;
    private Weight weight;

    public Result(int id, int weightInGrams, long dateLong){
        this.id = id;
        this.weight = new Weight(weightInGrams,Weight.Unit.g);
        this.dateLong = dateLong;
    }
    public Result(long dateLong, Weight weight){
        this.id = 0;
        this.weight = weight;
        this.dateLong = dateLong;
    }
    public int getId(){ return id; }
    public int getWeightInGrams(){
        return weight.getWeight(Weight.Unit.g);
    }
    public Weight getWeight(){
        return weight;
    }
    public long getDataLong(){
        return dateLong;
    }
    public void setWeight(int weight, Weight.Unit unit){
        this.weight.setValue(weight,unit);
    }
}
