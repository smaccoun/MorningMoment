package com.example.stevenmaccoun.morningmoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RoutineTaskActivity extends AppCompatActivity {

    private TextView title;
    private TextView description;
    private TextView duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_task);

        title = (TextView) findViewById(R.id.task_title);
        description = (TextView) findViewById(R.id.task_description);
        duration = (TextView) findViewById(R.id.task_duration);

        Intent i = getIntent();
        title.setText(i.getStringExtra("title"));
        description.setText(i.getStringExtra("description"));
        duration.setText(i.getStringExtra("duration"));
    }
}
