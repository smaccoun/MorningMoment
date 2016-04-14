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

        String[] taskList = new String[] {
            "Alexander Stomach",
            "5 minute tapping",
            "10 minute integral body",
                "10 minute intention setting"
        };

        ArrayList<RoutineTask> sampleTasks = new ArrayList<>();

        for(int i=0; i < taskList.length; ++i ){
            sampleTasks.add(i, new RoutineTask(taskList[i], "blah"));
        }

        RoutineTaskAdapter routineTasksLVAdapter = new RoutineTaskAdapter(this, sampleTasks);

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
            // Populate the data into the template view using the data object
            taskTtile.setText(rt.getTitle());
            taskDescription.setText(rt.getDescription());
            // Return the completed view to render on screen
            return convertView;
        }
    }

}
