package com.edngai.healthkit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class userProfile extends AppCompatActivity {

    private TextView resultBMI;
    private EditText weightIn, heightIn, ageIn;
    private double result;
    private int w, h, a;
    private String resultString, wString, hString, aString;
    dataHolder g = dataHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeApp();
    }


    private void initializeApp(){
        ageIn = (EditText) findViewById(R.id.userAge);
        weightIn = (EditText) findViewById(R.id.userWeight);
        heightIn = (EditText) findViewById(R.id.userHeight);
        resultBMI = (TextView) findViewById(R.id.resultOut);

        // get the latest created object's bmi & info

        ParseQuery<ParseObject> query = ParseQuery.getQuery("BMI");
        query.orderByDescending("createdAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    resultString = String.format("%.2f", result);
                    displayBMI();
                    displayInfo();
                } else {
                    result = (double) user.get("userBMI");
                    resultString = String.format("%.2f", result);
                    displayBMI();
                    displayInfo();
                }
            }
        });

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Weight");
        query2.orderByDescending("createdAt");
        query2.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    wString = String.format("%d", w);
                    displayBMI();
                    displayInfo();
                } else {
                    w = (int) user.get("pounds");
                    wString = String.format("%d", w);
                    displayBMI();
                    displayInfo();
                }
            }
        });

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Height");
        query3.orderByDescending("createdAt");
        query3.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    hString = String.format("%d", h);
                    displayBMI();
                    displayInfo();
                } else {
                    h = (int) user.get("inches");
                    hString = String.format("%d", h);
                    displayBMI();
                    displayInfo();
                }
            }
        });

        ParseQuery<ParseObject> query4 = ParseQuery.getQuery("Age");
        query4.orderByDescending("createdAt");
        query4.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    aString = String.format("%d", a);
                    displayBMI();
                    displayInfo();
                } else {
                    a = (int) user.get("userAge");
                    aString = String.format("%d", a);
                    displayBMI();
                    displayInfo();
                }
            }
        });
    }

    public void displayBMI(){
        // display the text
        resultBMI.setText(resultString, TextView.BufferType.NORMAL);
    }

    public void displayInfo() {
        //display global weight, height, age
        weightIn.setText(wString, TextView.BufferType.EDITABLE);
        heightIn.setText(hString, TextView.BufferType.EDITABLE);
        ageIn.setText(aString, TextView.BufferType.EDITABLE);
    }


    /*
     * calculateBMI()
     * This method calculates the BMI and sets global variables for weight, height, age, and
     * result. It returns the resulting bmi.
     *
     */
    public double calculateBMI( double w, double h, double a){
        double bmi;
        bmi = (w / (h * h)) *703.0;

        // Parse objects
        ParseObject weightObject = new ParseObject("Weight");
        ParseObject heightObject = new ParseObject("Height");
        ParseObject bmiObject = new ParseObject("BMI");
        ParseObject ageObject = new ParseObject("Age");

        /* Store updated info into parse */
        weightObject.put("pounds", w);
        heightObject.put("inches", h);
        bmiObject.put("userBMI", bmi);
        ageObject.put("userAge", a);

        weightObject.saveInBackground();
        heightObject.saveInBackground();
        bmiObject.saveInBackground();
        ageObject.saveInBackground();

        // set global variables to new weight, height, bmi and age
        g.setWeightInput(w);
        g.setHeightInput(h);
        g.setResultInput(bmi);
        g.setAgeInput(a);

        // return the bmi
        return bmi;
    }

    // called when onClick() is called on the UPDATE button
    public void updateUser(View v){

        // local variables to hold the weight and height as strings
        double weight = Double.parseDouble(weightIn.getText().toString());
        double height = Double.parseDouble( heightIn.getText().toString() );
        // local variable to hold age as string
        double age = Double.parseDouble(ageIn.getText().toString());

        // find the result and display that result bmi
        // also update age (put it in the calculateBMI method)
        result = calculateBMI(weight, height, age);
        resultString = String.format("%.2f", result);
        // set weight, height, age Strings for display
        wString = String.format("%f", g.getWeightInput());
        hString = String.format("%f", g.getHeightInput());
        aString = String.format("%f", g.getAgeInput());
        displayBMI();
        displayInfo();
        //Taking out confirmBMI because that popup will happen in BMI page
        //confirmBMI();

    }

    // A Pop Up Box Opens indicating what you bmi means
    public void confirmBMI(){
        AlertDialog.Builder builder1;
        builder1 = new AlertDialog.Builder(this);

        if ( g.getResultInput() < 15 ) {
            builder1.setMessage("Your BMI means: very severely underweight"); }
        else if ( (g.getResultInput() >= 15 ) && (g.getResultInput() < 16)  ) {
            builder1.setMessage("Your BMI means: severely underweight"); }
        else if ( (g.getResultInput() >= 16 ) && (g.getResultInput() < 18.5)  ) {
            builder1.setMessage("Your BMI means: underweight"); }
        else if ( (g.getResultInput() >= 18.5 ) && (g.getResultInput() < 25)  ) {
            builder1.setMessage("Your BMI means: normal"); }
        else if ( (g.getResultInput() >= 25 ) && (g.getResultInput() < 30)  ) {
            builder1.setMessage("Your BMI means: overweight"); }
        else if ( (g.getResultInput() >= 30 ) && (g.getResultInput() < 35)  ) {
            builder1.setMessage("Your BMI means: Obese Class I (Moderately obese)"); }
        else if ( (g.getResultInput() >= 35 ) && (g.getResultInput() < 40)  ) {
            builder1.setMessage("Your BMI means: Obese Class II (Severely obese)"); }
        else if ( (g.getResultInput() > 40 ) ) {
            builder1.setMessage("Your BMI means: Obese Class III (Very severely obese)"); }

        builder1.setCancelable(true);
        builder1.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
