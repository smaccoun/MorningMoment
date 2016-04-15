package com.example.stevenmaccoun.morningmoment;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RoutineActivity extends AppCompatActivity {

    private ListView routineTasksLV;
    private Button beginRoutineB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        routineTasksLV = (ListView) findViewById(R.id.routine_items);
        
        initializeRoutineTasksLV();

        beginRoutineB = (Button) findViewById(R.id.begin_routine);
        beginRoutineB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                routineTasksLV.setItemChecked(1, true);
                Toast.makeText(getApplicationContext(),
                        String.valueOf(routineTasksLV.getItemIdAtPosition(1)), Toast.LENGTH_SHORT)
                        .show();

            }
        });
    }

    private void initializeRoutineTasksLV(){

        ArrayList<RoutineTask> routineTasks = loadRoutineTasks();
        RoutineTaskAdapter routineTasksLVAdapter = new RoutineTaskAdapter(this, routineTasks);

        routineTasksLV.setAdapter(routineTasksLVAdapter);
        routineTasksLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;

                RoutineTask rt = (RoutineTask) routineTasksLV.getItemAtPosition(position);
                String description = rt.getDescription();

                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " + description , Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    
    private ArrayList<RoutineTask> loadRoutineTasks()
    {
        String[] taskList = new String[] {
                "Alexander Stomach",
                "Self-love tapping",
                "Integral body",
                "Intention setting"
        };

        ArrayList<RoutineTask> routineTasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        for(int i=0; i < taskList.length; ++i ){
            try {
                routineTasks.add(i, new RoutineTask(taskList[i], "blah", sdf.parse("05:00")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        return routineTasks;
    }

    private class RoutineTaskAdapter extends ArrayAdapter<RoutineTask> {
        public RoutineTaskAdapter(Context context, ArrayList<RoutineTask> routineTasks) {
            super(context, 0, routineTasks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            RoutineTask rt = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).
                        inflate(R.layout.activity_routine_task, parent, false);
            }
            // Lookup view for data population
            TextView taskTtile = (TextView) convertView.findViewById(R.id.task_title);
            TextView taskDescription = (TextView) convertView.findViewById(R.id.task_description);
            TextView taskDuration = (TextView) convertView.findViewById(R.id.task_duration);
            // Populate the data into the template view using the data object
            taskTtile.setText(rt.getTitle());
            taskDescription.setText(rt.getDescription());
            taskDuration.setText(rt.getDurationString());
            // Return the completed view to render on screen
            return convertView;
        }
    }

}
