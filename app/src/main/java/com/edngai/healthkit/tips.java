package com.edngai.healthkit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class tips extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tips");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> tips, ParseException e) {
                if (e == null) {
                    TextView tip = (TextView) findViewById(R.id.tip);
                    tip.setText(getTip(tips));
                } else {
                    Log.d("tips", "Error: " + e.getMessage());
                }
            }
        });
    }

    private String getTip(List<ParseObject> tips) {
        ParseObject tip =  tips.get((int) (Math.random() * tips.size()));
        return tip.getString("tip");
    }
}
