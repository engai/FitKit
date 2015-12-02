package com.edngai.healthkit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


//Crystal: I commented out some stuff just in case things go wrong
//I know what the original code looked like; we can clean up later!

public class calories extends AppCompatActivity {

    public Hashtable<String, String> foods = new Hashtable<String, String>();

    public final static String ID_EXTRA = "com.edngai.healthkit._ID";

    /**
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] days = {"Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }*/

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
        getSupportActionBar().setTitle("Calories");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        displayDate();
        /*displayToList();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calorie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
        }*/

        switch (item.getItemId()) {
            case R.id.add_calories:
                showInputDialog();
            /**
                 Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.menuitem2:
                Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            */
            default:
                break;
        }

        return true;

    }

    private TextView resultText;

    protected void showInputDialog() {

        // get add_calories.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(calories.this);
        final View promptView = layoutInflater.inflate(R.layout.add_calories, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(calories.this);
        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    //NEED CODE HERE:
                    // save entered food item and calories to local variables
                    // which will be used in onClick method below
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog.Builder addCalories = new AlertDialog.Builder(calories.this);
                        addCalories.setTitle("Confirm");
                        addCalories.setMessage("Are you sure this is correct?");
                        //some code to display food item and calories in the dialog!!!
                        addCalories.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //IMPLEMENT SAVING OF INFO HERE!!!
                                //!!!
                                //!!!

                                String food = ((EditText) promptView.findViewById(R.id.edittext)).getText().toString();
                                String calories = ((EditText) promptView.findViewById(R.id.edittext2)).getText().toString();
                                Calendar c = Calendar.getInstance();
                                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                                ParseObject item = new ParseObject("Calories");
                                item.put("food", food);
                                item.put("calories", calories);
                                item.put("dayOfWeek", dayOfWeek);
                                item.saveInBackground();

                                //Toast to show button works
                                Toast added = Toast.makeText(getApplicationContext(),
                                        food + " added!", Toast.LENGTH_SHORT);
                                added.show();

                            }
                        });
                        addCalories.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        addCalories.show();

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void displayDate(){
        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(onListClick);
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){

            Intent i = new Intent(calories.this, dayCalories.class);

            i.putExtra("position", position);
            startActivity(i);

        }
    };



}
