package com.example.stevenmaccoun.morningmoment;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class RoutineTaskActivity extends AppCompatActivity {

    private TextView tvTaskNm;
    private TextView tvTaskDesc;
    private TextView tvDuration;

    private RoutineTaskCountdownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_task);

        initialize();
    }

    private void initialize(){
        tvTaskNm = (TextView) findViewById(R.id.task_nm);
        tvTaskDesc = (TextView) findViewById(R.id.task_desc);
        tvDuration = (TextView) findViewById(R.id.task_duration);

        RoutineTask currentTask = RoutineTaskManager.getInstance().getCurrentTask();
        String taskNm = currentTask.getTitle();
        String routineDesc = currentTask.getDescription();
        String duration = currentTask.getDurationString();

        tvTaskNm.setText(taskNm);
        tvTaskDesc.setText(routineDesc);
        tvDuration.setText(duration);

        launchTimer(duration);
    }

    private void launchTimer(String duration){
        long millisToFinish = DateFormatHandler.toLong(duration);

        timer = new RoutineTaskCountdownTimer(millisToFinish, 1000);
        timer.start();
    }

    public void finishRoutine(){
        timer.cancel();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Congrats, you finished!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Okay!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });


        AlertDialog alert = builder1.create();
        alert.show();


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
            tvDuration.setText(sdf.format(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            alertFinishedTask();

            int taskNo = RoutineTaskManager.getInstance().incrementCurrentTaskNumber();
            if(taskNo < 0){
                finishRoutine();
                return;
            }

            proceedNextTaskDialog();
        }

    }

    private void proceedNextTaskDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Ready for next task?!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Okay!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        initialize();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private void alertFinishedTask(){
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500);
    }
}
