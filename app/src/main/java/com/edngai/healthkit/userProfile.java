package com.edngai.healthkit;

import android.os.Bundle;
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
    private EditText weightIn, heightIn, ageIn, goalIn;
    private double result; // this is the bmi
    private int intWeight, intHeight, intAge, intGoal;
    private String resultString, wString, hString, aString, goalString;
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
        goalIn = (EditText) findViewById(R.id.userGoal);

        // get the latest created object's bmi & info

        ParseQuery<ParseObject> resultQuery = ParseQuery.getQuery("BMI");
        resultQuery.orderByDescending("createdAt");
        resultQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    // print 0
                } else {
                    // latest object returned in userBMI. Now, get the bmi. Set the bmi as global.
                    result = (double) user.get("userBMI");
                    g.setResultInput(result);
                }
                resultString = "Not yet Calculated.";
                displayInfo();
            }
        });

        ParseQuery<ParseObject> weightQuery = ParseQuery.getQuery("Weight");
        weightQuery.orderByDescending("createdAt");
        weightQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    // print 0
                } else {
                    intWeight = (int) user.get("pounds");
                    g.setWeightInput(intWeight);
                    wString = Integer.toString(intWeight);
                }
                displayInfo();
            }
        });

        ParseQuery<ParseObject> heightQuery = ParseQuery.getQuery("Height");
        heightQuery.orderByDescending("createdAt");
        heightQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    // print 0
                } else {
                    intHeight = (int) user.get("inches");
                    g.setHeightInput(intHeight);
                    hString = Integer.toString(intHeight);

                }
                displayInfo();
            }
        });

        ParseQuery<ParseObject> ageQuery = ParseQuery.getQuery("Age");
        ageQuery.orderByDescending("createdAt");
        ageQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    // print 0
                } else {
                    intAge = (int) user.get("userAge");
                    g.setAgeInput(intAge);
                    aString = Integer.toString(intAge);

                }
                displayInfo();
            }
        });

        ParseQuery<ParseObject> goalQuery = ParseQuery.getQuery("CalorieGoal");
        goalQuery.orderByDescending("createdAt");
        goalQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (user == null) {
                    // if there is no data in parse, then just print 0
                } else {
                    intGoal = (int) user.get("caloriesGoal");
                    g.setGoalInput(intGoal);
                    goalString = Integer.toString(intGoal);

                }
                displayInfo();
            }
        });

    }



    public void displayInfo() {
        //display global weight, height, age, BMI
        resultBMI.setText(resultString, TextView.BufferType.NORMAL);
        weightIn.setText(wString, TextView.BufferType.EDITABLE);
        heightIn.setText(hString, TextView.BufferType.EDITABLE);
        ageIn.setText(aString, TextView.BufferType.EDITABLE);
        goalIn.setText(goalString, TextView.BufferType.EDITABLE);
    }

    /*
     * calculateBMI()
     * This method calculates the BMI and sets global variables for weight, height, age, and
     * result. It returns the resulting bmi.
     *
     */
    public double calculateBMI( int w, int h, int a, int goal){
        double bmi;
        bmi = (w / (h * h)) *703.0;

        // Parse objects
        ParseObject weightObject = new ParseObject("Weight");
        ParseObject heightObject = new ParseObject("Height");
        ParseObject bmiObject = new ParseObject("BMI");
        ParseObject ageObject = new ParseObject("Age");
        ParseObject goalObject = new ParseObject("CalorieGoal");

        /* Store updated info into parse */
        weightObject.put("pounds", w);
        heightObject.put("inches", h);
        bmiObject.put("userBMI", bmi);
        ageObject.put("userAge", a);
        goalObject.put("caloriesGoal", goal);

        weightObject.saveInBackground();
        heightObject.saveInBackground();
        bmiObject.saveInBackground();
        ageObject.saveInBackground();
        goalObject.saveInBackground();

        // set global variables to new weight, height, bmi and age
        g.setWeightInput(w);
        g.setHeightInput(h);
        g.setResultInput(bmi);
        g.setAgeInput(a);
        g.setGoalInput(goal);

        // return the bmi
        return bmi;
    }

    // called when onClick() is called on the UPDATE button
    public void updateUser(View v){

        // check if text field weightIn are empty
        if( (weightIn.getText().length() == 0) ){
            weightIn.requestFocus();
            weightIn.setError("FIELD CANNOT BE EMPTY");
        }

        // check if text field heightIn are empty
        else if( (heightIn.getText().length() == 0) ){
            heightIn.requestFocus();
            heightIn.setError("FIELD CANNOT BE EMPTY");
        }

        // check if text field ageIn are empty
        else if( (ageIn.getText().length() == 0) ){
            ageIn.requestFocus();
            ageIn.setError("FIELD CANNOT BE EMPTY");
        }

        // check if text field ageIn is too large
        else if( (ageIn.getText().length() > 3) ) {
            ageIn.requestFocus();
            ageIn.setError("AGE TOO LARGE");

        }

        // check if text field weightIn is too large
        else if ( (weightIn.getText().length() > 3) ) {
            weightIn.requestFocus();
            weightIn.setError("WEIGHT TOO LARGE");
        }

        // check if text field heightIn is too large
        else if ( (heightIn.getText().length() > 3) ) {
            heightIn.requestFocus();
            heightIn.setError("HEIGHT TOO LARGE");
        }

        else{
            // local variables to hold the weight, height, age, and goal as strings
            int weight = Integer.parseInt(weightIn.getText().toString());
            int height = Integer.parseInt(heightIn.getText().toString());
            int age = Integer.parseInt(ageIn.getText().toString());
            int goal = Integer.parseInt(goalIn.getText().toString());

            // find the result and display that result bmi
            // also update age (put it in the calculateBMI method)
            result = calculateBMI(weight, height, age, goal);
            resultString = String.format("%.2f", result);
            // set weight, height, age Strings for display
            wString = String.format("%f", g.getWeightInput());
            hString = String.format("%f", g.getHeightInput());
            aString = String.format("%f", g.getAgeInput());
            goalString = String.format("%f", g.getGoalInput());
            displayInfo();
        }

    }
}
