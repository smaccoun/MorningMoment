package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.content.Intent;
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
import java.util.HashMap;

public class RoutineActivity extends AppCompatActivity {

    private ListView routineTasksLV;
    private Button beginRoutineB;
    private RoutineTaskAdapter routineTasksLVAdapter;

    private Intent previousIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        previousIntent = getIntent();

        routineTasksLV = (ListView) findViewById(R.id.routine_items);
        
        initializeRoutineTasksLV();

        beginRoutineB = (Button) findViewById(R.id.begin_routine);
        beginRoutineB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                routineTasksLV.setItemChecked(1, true);
                RoutineTaskManager rtm = createRoutineTaskManager();
                RoutineTask currentTask = rtm.getCurrentTask();
                Intent i = new Intent(RoutineActivity.this, RoutineTaskActivity.class);
                i.putExtra("title", currentTask.getTitle());
                i.putExtra("description", currentTask.getDescription());
                i.putExtra("duration", currentTask.getDurationString());
                startActivity(i);

            }
        });
    }

    private RoutineTaskManager createRoutineTaskManager(){
        ArrayList<RoutineTask> routineTasks = routineTasksLVAdapter.getRoutineTasks();
        HashMap<Integer, RoutineTask> routineTaskHashMap = new HashMap<Integer, RoutineTask>();
        for(int i = 0; i < routineTasks.size(); ++i){
            routineTaskHashMap.put(i, routineTasks.get(i));
        }

        RoutineTaskManager rtm = new RoutineTaskManager(previousIntent.getStringExtra("routine_name"),
                routineTaskHashMap);

        return rtm;
    }

    private void initializeRoutineTasksLV(){

        routineTasksLVAdapter = new RoutineTaskAdapter(this, loadRoutineTasks());
        routineTasksLV.setAdapter(routineTasksLVAdapter);
        routineTasksLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RoutineTask rt = (RoutineTask) routineTasksLV.getItemAtPosition(position);
                String title = rt.getTitle();
                String description = rt.getDescription();
                String duration = rt.getDurationString();

                Intent i = new Intent(RoutineActivity.this, RoutineTaskActivity.class);
                i.putExtra("title", title);
                i.putExtra("description", description);
                i.putExtra("duration", duration);
                startActivity(i);
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
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        for(int i=0; i < taskList.length; ++i ){
            try {
                routineTasks.add(i, new RoutineTask(taskList[i], "blah", sdf.parse("00:05:00")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        return routineTasks;
    }

    private class RoutineTaskAdapter extends ArrayAdapter<RoutineTask> {
        private ArrayList<RoutineTask> routineTasks;

        public RoutineTaskAdapter(Context context, ArrayList<RoutineTask> routineTasks) {
            super(context, 0, routineTasks);
            this.routineTasks = routineTasks;
        }

        public ArrayList<RoutineTask> getRoutineTasks(){ return routineTasks;}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            RoutineTask rt = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).
                        inflate(R.layout.activity_routine_task_lv, parent, false);
            }
            // Lookup view for data population
            TextView taskTtile = (TextView) convertView.findViewById(R.id.task_title_lv);
            TextView taskDescription = (TextView) convertView.findViewById(R.id.task_description_lv);
            TextView taskDuration = (TextView) convertView.findViewById(R.id.task_duration_lv);
            // Populate the data into the template view using the data object
            taskTtile.setText(rt.getTitle());
            taskDescription.setText(rt.getDescription());
            taskDuration.setText(rt.getDurationString());
            // Return the completed view to render on screen
            return convertView;
        }
    }

}
