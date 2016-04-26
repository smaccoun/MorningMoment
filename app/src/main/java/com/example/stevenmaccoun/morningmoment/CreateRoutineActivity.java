package com.example.stevenmaccoun.morningmoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stevenmaccoun.morningmoment.db.RoutineRepository;
import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

import java.text.ParseException;
import java.util.ArrayList;

public class CreateRoutineActivity extends AppCompatActivity {

    private Button saveB;
    private Button createTaskB;
    private TextView routineNmTV;
    private TextView routineDescTV;
    private TextView routineDurationTV;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine);

        saveB = (Button) findViewById(R.id.save);
        createTaskB = (Button) findViewById(R.id.add_task);

        routineNmTV = (TextView) findViewById(R.id.nm_entry);
        routineDescTV = (TextView) findViewById(R.id.desc_entry);
        routineDurationTV = (TextView) findViewById(R.id.duration_entry);

        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoutine();
                finish();
            }
        });

        createTaskB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateRoutineActivity.this, CreateRoutineTaskActivity.class);
                i.putExtra("routine_nm", routineNmTV.getText().toString());
                startActivity(i);
            }
        });
    }

    public boolean saveRoutine(){
        RoutineRepository rr = new RoutineRepository(getApplicationContext());
        String name = routineNmTV.getText().toString();
        String description = routineDescTV.getText().toString();

        long duration = 0;
        try {
            duration = DateFormatHandler.getInstance().toLong(routineDurationTV.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Routine routine = new Routine(name, description, new ArrayList<RoutineTask>(), duration);
        boolean success = rr.Save(routine);

        return success;
    }

}
