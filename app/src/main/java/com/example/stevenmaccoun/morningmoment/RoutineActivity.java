package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RoutineActivity extends AppCompatActivity {

    private ListView routineTasksLV;
    private Button beginRoutineB;
    private ArrayList<RoutineTask> routineTasks;
    private String routineName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);


        routineTasksLV = (ListView) findViewById(R.id.routine_items);
        routineName = '"' + getIntent().getStringExtra("routine_nm") + '"';

        Routine currentRoutine = RoutineTaskManager.getInstance().getCurrentRoutine();
        populateRoutineTasksLV(currentRoutine);

        beginRoutineB = (Button) findViewById(R.id.begin_routine);
        beginRoutineB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                routineTasksLV.setItemChecked(1, true);
                RoutineTaskManager.getInstance().setCurrentTaskToBeginning();
                RoutineTask currentTask = RoutineTaskManager.getInstance().getCurrentTask();
                Intent i = new Intent(RoutineActivity.this, RoutineTaskActivity.class);
                startActivity(i);

            }
        });
    }




    private void populateRoutineTasksLV(Routine routine){

        ArrayList<RoutineTask> routineTasks = new ArrayList<>(routine.getRoutineTasks());
        RoutineTaskAdapter routineTaskAdapter = new RoutineTaskAdapter(this, routineTasks);
        routineTasksLV.setAdapter(routineTaskAdapter);
    }

    public class RoutineTaskAdapter extends ArrayAdapter<RoutineTask> {
        public RoutineTaskAdapter(Context context, ArrayList<RoutineTask> routineTasks) {
            super(context, 0, routineTasks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            RoutineTask routineTask = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.activity_routine_task_lv, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.task_nm);
            TextView tvDesc = (TextView) convertView.findViewById(R.id.task_desc);
            TextView tvDuration = (TextView) convertView.findViewById(R.id.task_duration);
            // Populate the data into the template view using the data object
            tvName.setText(routineTask.getTitle());
            tvDesc.setText(routineTask.getDescription());
            tvDuration.setText(routineTask.getDurationString());
            // Return the completed view to render on screen
            return convertView;
        }
    }


}
