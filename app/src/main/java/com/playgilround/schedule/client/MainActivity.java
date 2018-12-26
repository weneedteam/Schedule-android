package com.playgilround.schedule.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.playgilround.schedule.client.Activity.ScheCalendarActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalendar = (Button) findViewById(R.id.calendarBtn);
        btnCalendar.setOnClickListener(btnCalendar_listener);
    }

    //Show Calendar Activity
    private View.OnClickListener btnCalendar_listener = view -> {
        Intent calendarIntent = new Intent(getBaseContext(), ScheCalendarActivity.class);
        startActivity(calendarIntent);
    };

}

