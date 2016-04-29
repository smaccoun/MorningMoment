package com.example.stevenmaccoun.morningmoment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevenmaccoun.morningmoment.db.RoutineTaskRepository;
import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

import org.w3c.dom.Text;

import java.text.ParseException;

public class CreateRoutineTaskActivity extends AppCompatActivity {

    private Button createTaskB;
    private TextView tvName;
    private TextView tvDesc;
    private TextView tvDuration;
    private TextView tvWebUrl;

    public static final String PUBLIC_STATIC_STRING_IDENTIFIER = "routine_nm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine_task);

        tvName = (TextView) findViewById(R.id.nm_entry);
        tvDesc = (TextView) findViewById(R.id.desc_entry);
        tvDuration = (TextView) findViewById(R.id.duration_entry);
        tvWebUrl = (TextView) findViewById(R.id.web_url_entry);

        createTaskB = (Button) findViewById(R.id.save);
        createTaskB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidForm()){
                    String name = tvName.getText().toString();
                    String desc = tvDesc.getText().toString();
                    String webUrl = tvWebUrl.getText().toString();
                    long duration = DateFormatHandler.toLong(tvDuration.getText().toString());

                    RoutineTask rt = new RoutineTask(name, desc, duration, webUrl);
                    RoutineTaskRepository rtr = new RoutineTaskRepository(getApplicationContext());
                    rtr.Save(rt);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(PUBLIC_STATIC_STRING_IDENTIFIER, rt.getTitle());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
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
