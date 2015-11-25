package com.edngai.healthkit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dayCalories extends AppCompatActivity {

    int positionClicked, result;
    private TextView dailyCaloriesText, dailyGoalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_calories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily Calories");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent i = getIntent();
        positionClicked = i.getIntExtra("position", 0) + 1;
        displayToList();
        displayGoal();

    }

    public void displayToList(){
        final List<String> caloriesArray = new ArrayList<String>();
        final List<String> amountArray = new ArrayList<String>();
        ParseQuery query = new ParseQuery("Calories");

        query.whereEqualTo("dayOfWeek", positionClicked);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> caloriesList, ParseException e) {

                if (e == null) {

                    for (ParseObject foodItem : caloriesList) {
                        caloriesArray.add((String) foodItem.get("food"));
                        amountArray.add((String) foodItem.get("calories"));
                    }

                    //Convert from lists to arrays
                    String[] finalCaloriesArray = new String[caloriesArray.size()];
                    caloriesArray.toArray(finalCaloriesArray);

                    String[] finalAmountArray = new String[amountArray.size()];
                    amountArray.toArray(finalAmountArray);

                    displayTotalCalories(finalAmountArray);

                    //Create a map to pass into ListView
                    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                    for (int i = 0; i < caloriesArray.size(); i++) {
                        Map<String, String> datamap = new HashMap<String, String>(2);
                        datamap.put("food", finalCaloriesArray[i]);
                        datamap.put("calories", "Calories: " + finalAmountArray[i]);
                        data.add(datamap);
                    }

                    //Create Simple Adapter to display Item and subitem
                    SimpleAdapter adapter = new SimpleAdapter(dayCalories.this, data, android.R.layout.simple_list_item_2,
                            new String[]{"food", "calories"}, new int[]{android.R.id.text1, android.R.id.text2});
                    ListView dayListView = (ListView) findViewById(R.id.dayCaloriesListView);
                    dayListView.setAdapter(adapter);

                } else {
                    //Log.d("Error with calorie retrieval", "Error: " + e.getMessage());
                }
            }
        });

    }

    public void displayTotalCalories(String[] calories) {

        dailyCaloriesText = (TextView) findViewById(R.id.dailyCalories);
        int total = 0;

        for (int i = 0; i < calories.length; i++) {
            total = total + (Integer.parseInt(calories[i]));
        }

        dailyCaloriesText.setText("Calories: " + Integer.toString(total), TextView.BufferType.NORMAL);

    }

    public void displayGoal() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CalorieGoal");
        query.orderByDescending("createdAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {

                // latest object returned in userBMI. Now, get the bmi. Set the bmi as global.
                result = (int) user.get("caloriesGoal");

            }

        });

        dailyGoalText = (TextView) findViewById(R.id.dailyGoal);
        dailyGoalText.setText("Daily Goal: " + Integer.toString(2000));

    }



}
