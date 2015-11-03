package com.edngai.healthkit;


/**
 * Created by VilanPC on 11/2/2015.
 */
public class dataHolder {
    private static dataHolder instance;
    private static double weightInput;
    private static double heightInput;
    private static double resultInput;

    private dataHolder(){}

    public void setWeightInput(double t){
        dataHolder.weightInput = t;
    }

    public double getWeightInput(){
        return dataHolder.weightInput;
    }

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

    public static synchronized dataHolder getInstance(){
        if (instance == null){
            instance = new dataHolder();
        }
        return instance;
    }
    /*
    private double heightInput;
    private double globalBMI =0;

    public double getWeightInput() {return weightInput;}
    public void setWeightInput(double weightData) {this.weightInput = weightData;}

    public double getHeightInput() {return heightInput;}
    public void setHeightInput(double heightData) {this.heightInput = heightData;}

    public double getGlobalBMI() {return globalBMI;}
    public void setGlobalBMI(double bmiData) {this.globalBMI = bmiData;}
    */
}
