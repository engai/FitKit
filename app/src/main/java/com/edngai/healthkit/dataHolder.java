package com.edngai.healthkit;


/**
 * Created by VilanPC on 11/2/2015.
 */
public class dataHolder {
    private static dataHolder instance;
    private static double weightInput;
    private static double heightInput;
    private static double resultInput;
    private static double ageInput;

    private dataHolder(){}

    public void setWeightInput(double t){
        dataHolder.weightInput = t;
    }

    public double getWeightInput(){ return dataHolder.weightInput; }

    public void setHeightInput(double t){
        dataHolder.heightInput = t;
    }

    public double getHeightInput(){
        return dataHolder.heightInput;
    }

    public void setResultInput(double t){
        dataHolder.resultInput = t;
    }

    public double getResultInput(){
        return dataHolder.resultInput;
    }

    public void setAgeInput(double a) { dataHolder.ageInput = a; }

    public double getAgeInput() { return dataHolder.ageInput; }

    public static synchronized dataHolder getInstance(){
        if (instance == null){
            instance = new dataHolder();
        }
        return instance;
    }

}
