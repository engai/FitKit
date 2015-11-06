package com.edngai.healthkit;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class bmi extends AppCompatActivity {

    private TextView resultBMI;
    private EditText weightIn, heightIn;
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
        resultBMI = (TextView) findViewById(R.id.resultOut);

        // get the latest created object's bmi

        ParseQuery<ParseObject> query = ParseQuery.getQuery("BMI");
        query.orderByDescending("createdAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    // no data in user's bmi. so, just print 0, the default
                    resultString = String.format("%.2f", result);
                    displayBMI();

                } else {
                    // latest object returned in userBMI. Now, get the bmi. Set the bmi as global.
                    result = (double) user.get("userBMI");
                    g.setResultInput(result);
                    resultString = String.format("%.2f", result);
                    displayBMI();
                }
            }
        });
    }

    public void displayBMI(){
        // display the text
        resultBMI.setText(resultString, TextView.BufferType.NORMAL);
    }

    /*
     * calculateBMI()
     * This method calculates the BMI and sets global variables for weight, height, and
     * result. It returns the resulting bmi.
     *
     */
    public double calculateBMI( double w, double h){
        double bmi;
        bmi = (w / (h * h)) *703.0;

        // Parse objects
        ParseObject weightObject = new ParseObject("Weight");
        ParseObject heightObject = new ParseObject("Height");
        ParseObject bmiObject = new ParseObject("BMI");

        /* Store updated weight and bmi into parse */
        weightObject.put("pounds", w);
        heightObject.put("inches", h);
        bmiObject.put("userBMI", bmi);

        weightObject.saveInBackground();
        heightObject.saveInBackground();
        bmiObject.saveInBackground();

        // set global variables to new weight, height, and bmi
        g.setWeightInput(w);
        g.setHeightInput(h);
        g.setResultInput(bmi);

        // return the bmi
        return bmi;
    }

    // called when onClick() is called on the Calculate BMI button
    public void runBMI(View v){

        // local variables to hold the weight and height as strings
        double weight = Double.parseDouble(weightIn.getText().toString());
        double height = Double.parseDouble( heightIn.getText().toString() );

        // find the result and display that result bmi
        result = calculateBMI(weight, height);
        resultString = String.format("%.2f", result);
        displayBMI();
        confirmBMI();

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
