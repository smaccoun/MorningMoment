package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoutineTaskActivity extends AppCompatActivity {

    private TextView titleTV;
    private TextView descriptionTV;
    private TextView durationTV;

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private RoutineTaskCountdownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_task);

        titleTV = (TextView) findViewById(R.id.task_title);
        descriptionTV = (TextView) findViewById(R.id.task_description);
        durationTV = (TextView) findViewById(R.id.task_duration);

        Intent i = getIntent();
        titleTV.setText(i.getStringExtra("title"));
        descriptionTV.setText(i.getStringExtra("description"));
        durationTV.setText(i.getStringExtra("duration"));
        long startTimeMillis = 0;
        try {
            startTimeMillis = sdf.parse(i.getStringExtra("duration")).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        timer = new RoutineTaskCountdownTimer(startTimeMillis, 3);
        timer.start();

    }

    @Override
    public void onBackPressed(){
        timer.cancel();
        super.onBackPressed();
    }

    private class RoutineTaskCountdownTimer extends CountDownTimer {


        public RoutineTaskCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            durationTV.setText(sdf.format(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(context, "FINISHED!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
