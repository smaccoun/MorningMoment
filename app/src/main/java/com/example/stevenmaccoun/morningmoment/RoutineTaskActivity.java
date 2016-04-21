package com.example.stevenmaccoun.morningmoment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class RoutineTaskActivity extends AppCompatActivity {

    private TextView titleTV;
    private TextView descriptionTV;
    private TextView durationTV;

    private RoutineTaskCountdownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_task);

        initialize();
    }

    private void initialize(){
        titleTV = (TextView) findViewById(R.id.task_title);
        descriptionTV = (TextView) findViewById(R.id.task_description);
        durationTV = (TextView) findViewById(R.id.task_duration);

        RoutineTask currentTask = RoutineTaskManager.getInstance().getCurrentTask();
        String taskNm = currentTask.getTitle();
        String routineDesc = currentTask.getDescription();
        String duration = currentTask.getDurationString();

        titleTV.setText(taskNm);
        descriptionTV.setText(routineDesc);
        durationTV.setText(duration);

        launchTimer(duration);
    }

    private void launchTimer(String duration){
        long millisToFinish = 0;
        try {
            millisToFinish = DateFormatHandler.getInstance().toLong(duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        timer = new RoutineTaskCountdownTimer(millisToFinish, 1000);
        timer.start();
    }

    public void finishRoutine(){
        timer.cancel();
        finish();
    }

    @Override
    public void onBackPressed(){
        timer.cancel();
        super.onBackPressed();
    }

    private class RoutineTaskCountdownTimer extends CountDownTimer {

        private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);


        public RoutineTaskCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        }


        @Override
        public void onTick(long millisUntilFinished) {
            durationTV.setText(sdf.format(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            int taskNo = RoutineTaskManager.getInstance().incrementCurrentTaskNumber();
            if(taskNo < 0){
                finishRoutine();
                return;
            }

            initialize();
        }

    }
}
