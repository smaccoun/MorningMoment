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

import com.example.stevenmaccoun.morningmoment.db.RoutineContract;
import com.example.stevenmaccoun.morningmoment.db.RoutineRepository;
import com.example.stevenmaccoun.morningmoment.db.RoutineTaskRepository;
import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

import java.text.ParseException;
import java.util.ArrayList;

public class CreateRoutineActivity extends AppCompatActivity {

    private Button saveB;
    private Button createTaskB;
    private Button viewTasksB;
    private TextView routineNmTV;
    private TextView routineDescTV;
    private TextView routineDurationTV;

    private ListView tasksLV;
    private TextView currentTasksTV;

    private ArrayList<RoutineTask> currentTasks = new ArrayList<>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine);

        saveB = (Button) findViewById(R.id.save);
        createTaskB = (Button) findViewById(R.id.create_task);

        routineNmTV = (TextView) findViewById(R.id.nm_entry);
        routineDescTV = (TextView) findViewById(R.id.desc_entry);
        routineDurationTV = (TextView) findViewById(R.id.duration_entry);

        currentTasksTV = (TextView) findViewById(R.id.current_tasks_TV);

        viewTasksB = (Button) findViewById(R.id.view_task_lv);
        saveB = (Button) findViewById(R.id.save);

        tasksLV = (ListView) findViewById(R.id.existing_tasks_lv);
        displayCurrentTasks();

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
                Routine routine = createRoutineFromEntries();
                if(routine == null){
                    Toast.makeText(getApplicationContext(), "Please enter a routine name", Toast.LENGTH_LONG);
                    return;
                }

                RoutineController.getInstance().initialize(routine);
                startActivity(i);
            }
        });

        viewTasksB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateTaskLV();
                tasksLV.setVisibility(View.VISIBLE);
            }
        });

        tasksLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoutineTask rt = (RoutineTask) parent.getAdapter().getItem(position);
                currentTasks.add(rt);
                Toast.makeText(getApplicationContext(), "Added " + rt.getTitle(), Toast.LENGTH_LONG);
                displayCurrentTasks();
                tasksLV.setVisibility(View.GONE);
            }
        });
    }

    private void displayCurrentTasks(){
        String taskStr = "";
        for(RoutineTask rt : currentTasks){
            taskStr += rt.getTitle() + ", ";
        }

        currentTasksTV.setText(taskStr);
    }

    private void populateTaskLV(){
        ArrayList<RoutineTask> routineTasks = new RoutineTaskRepository(this).GetAll(this);
        RoutineTaskAdapter adapter = new RoutineTaskAdapter(this, routineTasks);
        tasksLV.setAdapter(adapter);
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



    private Routine createRoutineFromEntries(){
        String name = routineNmTV.getText().toString();
        //Must at least have a name for routine to exist
        if(name == null){
            return null;
        }

        String description = routineDescTV.getText().toString();

        Routine routine = new Routine(name, description, new ArrayList<RoutineTask>(), 0);
        return routine;
    }

    public boolean saveRoutine(){
        String routineNm = routineNmTV.getText().toString();
        String routineDesc = routineDescTV.getText().toString();
        Routine routine = new Routine(routineNm, routineDesc, currentTasks, 0);

        boolean success = new RoutineRepository(this).Save(routine);
        return success;
    }

    @Override
    protected void onResume(){
        super.onResume();
        tasksLV.setVisibility(View.GONE);
    }

}
