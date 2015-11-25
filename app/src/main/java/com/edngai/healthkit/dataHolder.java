package com.edngai.healthkit;


/**
 * Created by VilanPC on 11/2/2015.
 */
public class dataHolder {
    private static dataHolder instance;
    private static double resultInput;
    private static int weightInput;
    private static int heightInput;
    private static int ageInput;
    private static int goalInput;

    private dataHolder(){}

    public void setWeightInput(int t){dataHolder.weightInput = t;}

    public double getWeightInput(){ return dataHolder.weightInput; }

    public void setHeightInput(int t){
        dataHolder.heightInput = t;
    }

    public double getHeightInput(){return dataHolder.heightInput;}

    public void setResultInput(double t){dataHolder.resultInput = t;}

    public double getResultInput(){return dataHolder.resultInput;}

    public void setAgeInput(int a) { dataHolder.ageInput = a; }

    public double getAgeInput() { return dataHolder.ageInput; }

    public void setGoalInput(int g) { dataHolder.goalInput = g; }

    public double getGoalInput() { return dataHolder.goalInput; }

    public static synchronized dataHolder getInstance(){
        if (instance == null){
            instance = new dataHolder();
        }
        return instance;
    }

}
