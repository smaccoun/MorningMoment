package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import com.example.stevenmaccoun.morningmoment.db.MorningRoutineDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class RoutineActivity extends AppCompatActivity {

    private ListView routineTasksLV;
    private Button beginRoutineB;
    private ArrayList<RoutineTask> routineTasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);


        routineTasksLV = (ListView) findViewById(R.id.routine_items);

        loadRoutineTasks();
        populateRoutineTasksLV();

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
                i.putExtra("durationMillis", currentTask.getDurationMillis());
                startActivity(i);

            }
        });
    }

    private RoutineTaskManager createRoutineTaskManager(){

        HashMap<Integer, RoutineTask> routineTaskHashMap = new HashMap<Integer, RoutineTask>();

        for(int i=0; i < routineTasks.size(); ++i){
            routineTaskHashMap.put(i, routineTasks.get(i));
        }

        RoutineTaskManager rtm = new RoutineTaskManager(getIntent().getStringExtra("routine_nm"),
                routineTaskHashMap);

        return rtm;
    }

    private ArrayList<RoutineTask> loadRoutineTasks() {

        String routineNm = '"' + getIntent().getStringExtra("routine_nm") + '"';

        String rtView =
                " SELECT rt._id, rt.task_nm, rt.task_desc, rt.duration_ms " +
                        " FROM RoutineTask rt " +
                        " INNER JOIN Routine r " +
                        " ON rt.routine_nm = r.routine_nm" +
                        " WHERE r.routine_nm = " + routineNm;

        Cursor c = MorningRoutineDbHelper
                .getHelper(getApplicationContext())
                .getWritableDatabase().rawQuery(rtView, null);

        TreeMap<Integer, RoutineTask> posRoutineTasks = new TreeMap<>();

        try
        {
            while (c.moveToNext()) {
                Integer pos = c.getInt(c.getColumnIndexOrThrow("_id"));
                String taskNm = c.getString(c.getColumnIndexOrThrow("task_nm"));
                String taskDesc = c.getString(c.getColumnIndexOrThrow("task_desc"));
                Integer taskDuration = c.getInt(c.getColumnIndexOrThrow("duration_ms"));
                posRoutineTasks.put(pos, new RoutineTask(taskNm, taskDesc, taskDuration));
            }
        } catch (Exception e){
            c.close();
        }

        routineTasks = new ArrayList<>(posRoutineTasks.values());

        return routineTasks;

    }

    private void populateRoutineTasksLV(){

        RoutineTaskAdapter routineTaskAdapter = new RoutineTaskAdapter(this, routineTasks);
        routineTasksLV.setAdapter(routineTaskAdapter);

        routineTasksLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RoutineTask rt = (RoutineTask) parent.getItemAtPosition(position);
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
            TextView tvName = (TextView) convertView.findViewById(R.id.task_title);
            TextView tvDesc = (TextView) convertView.findViewById(R.id.task_description);
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
