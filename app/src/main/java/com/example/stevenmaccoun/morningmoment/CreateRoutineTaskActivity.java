package com.example.stevenmaccoun.morningmoment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevenmaccoun.morningmoment.db.RoutineTaskRepository;
import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;
import com.ipaulpro.afilechooser.utils.FileUtils;

import org.w3c.dom.Text;

import java.io.File;
import java.text.ParseException;

public class CreateRoutineTaskActivity extends AppCompatActivity {

    private Button createTaskB;
    private TextView tvName;
    private TextView tvDesc;
    private TextView tvDuration;
    private TextView tvWebUrl;
    private Button addVideoB;
    private TextView tvVideoURLPath;

    public static final String PUBLIC_STATIC_STRING_IDENTIFIER = "routine_nm";
    private static final int REQUEST_CHOOSER = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine_task);

        tvName = (TextView) findViewById(R.id.nm_entry);
        tvDesc = (TextView) findViewById(R.id.desc_entry);
        tvDuration = (TextView) findViewById(R.id.duration_entry);
        tvWebUrl = (TextView) findViewById(R.id.web_url_entry);
        addVideoB = (Button) findViewById(R.id.add_video_b);
        tvVideoURLPath = (TextView) findViewById(R.id.tv_video_url_path);
        tvVideoURLPath.setVisibility(View.GONE);

        createTaskB = (Button) findViewById(R.id.save);
        createTaskB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidForm()){
                    String name = tvName.getText().toString();
                    String desc = tvDesc.getText().toString();
                    String webUrl = tvWebUrl.getText().toString();
                    String videoUrl = tvVideoURLPath.getText().toString();
                    long duration = DateFormatHandler.toLong(tvDuration.getText().toString());

                    RoutineTask rt = new RoutineTask(name, desc, duration, webUrl, videoUrl);
                    RoutineTaskRepository rtr = new RoutineTaskRepository(getApplicationContext());
                    rtr.Save(rt);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(PUBLIC_STATIC_STRING_IDENTIFIER, rt.getTitle());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        addVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the ACTION_GET_CONTENT Intent
                Intent getContentIntent = FileUtils.createGetContentIntent();

                Intent intent = Intent.createChooser(getContentIntent, "Select a file");
                startActivityForResult(intent, REQUEST_CHOOSER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHOOSER:
                if (resultCode == RESULT_OK) {

                    final Uri uri = data.getData();

                    // Get the File path from the Uri
                    String path = FileUtils.getPath(this, uri);
                    MediaPlayer mp = MediaPlayer.create(this, Uri.parse(path));
                    int duration = mp.getDuration();
                    mp.release();
                    tvVideoURLPath.setText(path);
                    tvVideoURLPath.setVisibility(View.VISIBLE);
                    tvDuration.setText(DateFormatHandler.toString(duration));
                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (path != null && FileUtils.isLocal(path)) {
                        File file = new File(path);
                    }
                }
                break;
        }
    }

    private boolean isValidForm(){

        boolean isValidForm = true;
        String errorMsg = "";

        //Ensure taskNm has at least one valid character or digit
        boolean doesNameContainValidChars
                = tvName.getText().toString().matches(".*[a-zA-Z]+.*");
        if(!doesNameContainValidChars){
            isValidForm = false;
            errorMsg = errorMsg + "\n" + "* Please enter a valid routine name";
        }

        //Ensure duration is entered
        if(tvDuration.getText().length() < 1){
            isValidForm = false;
            errorMsg = errorMsg + "\n" + "* Please enter a valid duration";
        }


        if(!isValidForm){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(errorMsg);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Okay",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder1.create();
            alert.show();
        }

        return isValidForm;
    }
}
