package com.edngai.healthkit;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    //used for register alarm manager
    PendingIntent pendingIntent;
    //used to store running alarmmanager instance
    AlarmManager alarmManager;
    //Callback function for Alarmmanager event
    BroadcastReceiver mReceiver;

    private TextView alarmToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        alarmToggle = (TextView) findViewById(R.id.alarmToggle);

        //Register AlarmManager Broadcast receive.
        RegisterAlarmBroadcast();

        Button logoutButton = (Button) findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                startActivity(new Intent(Settings.this, Login.class));

            }

        });
    }

    public void onClickSetAlarm(View v)
    {
        if ((alarmToggle.getText()).equals("OFF")) {
            //Get the current time and set alarm after 10 seconds from current time
            // so here we get
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);
            alarmToggle.setText("ON", TextView.BufferType.NORMAL);
        }
        else {
            UnregisterAlarmBroadcast();
            alarmToggle.setText("OFF", TextView.BufferType.NORMAL);
        }
    }

    private void RegisterAlarmBroadcast()
    {
        Log.i("Alarm Example:RegisterAlarmBroadcast()", "Going to register Intent.RegisterAlramBroadcast");

        //This is the call back function(BroadcastReceiver) which will be call when your
        //alarm time will reached.
        mReceiver = new BroadcastReceiver()
        {
            private static final String TAG = "Alarm Example Receiver";

            @Override
            public void onReceive(Context context, Intent intent)
            {
                /**
                 * This makes the reminder a toast, but we want something bigger
                 *
                 Log.i(TAG, "BroadcastReceiver::OnReceive() >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                 Toast.makeText(context, "Remember to log your calories for the day!", Toast.LENGTH_LONG).show();
                 */

                //Creates reminder on the notification bar
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.calories_button)
                                .setContentTitle("FitKit Reminder")
                                .setContentText("Remember to log your calories for the day!")
                                .setWhen(System.currentTimeMillis())
                                .setAutoCancel(true);

                // Creates an explicit intent for calories activity in app
                Intent resultIntent = new Intent(context, calories.class);

                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(calories.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(0, mBuilder.build());
            }

        };

        // register the alarm broadcast here
        //registerReceiver(mReceiver, new IntentFilter("com.myalarm.alarmexample") );
        //pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.myalarm.alarmexample"), 0);
        registerReceiver(mReceiver, new IntentFilter("com.edngai.healthkit;") );
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.edngai.healthkit;"), 0);
        alarmManager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
    }

    private void UnregisterAlarmBroadcast()
    {
        alarmManager.cancel(pendingIntent);
        getBaseContext().unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}