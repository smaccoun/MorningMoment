package com.example.stevenmaccoun.morningmoment;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class RoutineTaskActivity extends AppCompatActivity {

    private TextView tvTaskNm;
    private TextView tvTaskDesc;
    private TextView tvDuration;
    private TextView tvWebUrl;
    private VideoView videoView;
    private Button playVideoB;

    private Button pauseResumeB;
    private final String PAUSE_TEXT = "Pause";
    private final String RESUME_TEXT = "Resume";
    private enum TASK_STATE {
        PAUSED,
        RUNNING
    }
    private TASK_STATE currentTaskState;
    private long millisRemaining;

    private RoutineTask currentTask;
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
        tvWebUrl = (TextView) findViewById(R.id.url_link);
        pauseResumeB = (Button) findViewById(R.id.pause_resume_b);
        videoView = (VideoView) findViewById(R.id.videoView);
        playVideoB = (Button) findViewById(R.id.play_video_b);

        currentTask = RoutineTaskManager.getInstance().getCurrentTask();
        String taskNm = currentTask.getTitle();
        String routineDesc = currentTask.getDescription();
        String durationText = currentTask.getDurationString();
        String videoUrl = currentTask.getVideoUrlPath();

        tvTaskNm.setText(taskNm);
        tvTaskDesc.setText(routineDesc);
        tvDuration.setText(durationText);

        pauseResumeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTaskState == TASK_STATE.RUNNING){
                    pauseResumeB.setText(RESUME_TEXT);
                    timer.cancel();
                    currentTaskState = TASK_STATE.PAUSED;
                }
                else if(currentTaskState == TASK_STATE.PAUSED){
                    pauseResumeB.setText(PAUSE_TEXT);
                    startCountdownTimer();
                    currentTaskState = TASK_STATE.RUNNING;
                }

            }
        });

        if(currentTask.getWebUrlLink().length() > 0){
            tvWebUrl.setVisibility(View.VISIBLE);
            String url = currentTask.getWebUrlLink();
            tvWebUrl.setClickable(true);
            tvWebUrl.setMovementMethod(LinkMovementMethod.getInstance());
            String htmlUrl = "<u>" + url + "</u>";
            tvWebUrl.setText(Html.fromHtml(htmlUrl));
            tvWebUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String webUrl = currentTask.getWebUrlLink();
                    if (!webUrl.startsWith("http://") && !webUrl.startsWith("https://"))
                        webUrl = "http://" + webUrl;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                    startActivity(browserIntent);
                }
            });
        }
        else{
            tvWebUrl.setVisibility(View.GONE);
        }

        if(videoUrl.length() > 0){
            tvTaskDesc.setText(routineDesc + "\n" + videoUrl);
            videoView.setVisibility(View.VISIBLE);
            playVideoB.setVisibility(View.VISIBLE);
            tvDuration.setVisibility(View.GONE);
            pauseResumeB.setVisibility(View.GONE);

            MediaController mediaController = new MediaController(RoutineTaskActivity.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(Uri.parse(currentTask.getVideoUrlPath()));

            playVideoB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            finishTask();
                        }
                    });

                    videoView.start();
                    tvTaskDesc.setVisibility(View.GONE);
                    tvTaskNm.setVisibility(View.GONE);
                    playVideoB.setVisibility(View.GONE);
                }
            });

        } else{
            videoView.setVisibility(View.GONE);
            playVideoB.setVisibility(View.GONE);

            timer = new RoutineTaskCountdownTimer(currentTask.getDurationMillis(), 1000);
            tvTaskNm.setVisibility(View.VISIBLE);
            tvTaskDesc.setVisibility(View.VISIBLE);
            tvDuration.setVisibility(View.VISIBLE);
            pauseResumeB.setVisibility(View.VISIBLE);
            startCountdownTimer();
        }



    }

    private void startCountdownTimer(){
        timer.start();
    }

    public void finishRoutine(){
        if(timer != null){
            timer.cancel();
        }
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
            currentTaskState = TASK_STATE.RUNNING;
        }


        @Override
        public void onTick(long millisUntilFinished) {
            tvDuration.setText(sdf.format(millisUntilFinished));
            millisRemaining = millisUntilFinished;
        }

        @Override
        public void onFinish() {
            finishTask();
        }


    }

    private void finishTask(){
        alertFinishedTask();

        int taskNo = RoutineTaskManager.getInstance().incrementCurrentTaskNumber();
        if(taskNo < 0){
            finishRoutine();
            return;
        }

        proceedNextTaskDialog();
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
