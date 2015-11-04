package com.edngai.healthkit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class bmi extends AppCompatActivity {

    private TextView resultBMI;
    private EditText weightIn, heightIn;
    private Button calculateBMI;
    private double weight;
    private double height;
    private double result;
    private String resultString;
    dataHolder g = dataHolder.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeApp();
    }

    private void initializeApp(){
        weightIn = (EditText) findViewById(R.id.userWeight);
        heightIn = (EditText) findViewById(R.id.userHeight);
        calculateBMI = (Button) findViewById(R.id.BMIbutton);
        resultBMI = (TextView) findViewById(R.id.resultOut);

        result = g.getResultInput();
        resultString = String.format("%.2f", result);
        displayBMI();
    }

    public void displayBMI(){
        // display the text
        resultBMI.setText(resultString, TextView.BufferType.NORMAL);
    }

    public void runBMI(View v){

        // local variables to hold the weight and height as strings
        weight = Double.parseDouble(weightIn.getText().toString());
        height = Double.parseDouble( heightIn.getText().toString() );

        // find the result and display that result bmi
        result = (weight / (height * height)) *703.0;
        resultString = String.format("%.2f", result);
        displayBMI();

        // The user has inputted new weight, height, and age. So update the global variables.
        g.setWeightInput(weight);
        g.setHeightInput(height);
        g.setResultInput(result);

    }

}
