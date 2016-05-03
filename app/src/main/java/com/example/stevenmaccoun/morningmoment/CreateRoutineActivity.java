package com.example.stevenmaccoun.morningmoment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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

import com.example.stevenmaccoun.morningmoment.db.RoutineRepository;
import com.example.stevenmaccoun.morningmoment.db.RoutineTaskRepository;

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

    static final int INTENT_RETURN_INT = 1;

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
                if(isValidForm()){
                    saveRoutine();
                    finish();
                }
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
                startActivityForResult(i, INTENT_RETURN_INT);
            }
        });

        viewTasksB.setOnClickListener(new ViewTaskBClickListener());

        tasksLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoutineTask rt = (RoutineTask) parent.getAdapter().getItem(position);
                currentTasks.add(rt);
                displayCurrentTasks();
                tasksLV.setVisibility(View.GONE);
            }
        });
    }

    private class ViewTaskBClickListener implements View.OnClickListener {

        private ArrayList<RoutineTask> selectedTasks = new ArrayList<>();

        @Override
        public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateRoutineActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.dialog_lv, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Task List");
                alertDialog.setMessage("Select one or more tasks from the list");
                alertDialog.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for(RoutineTask rt : selectedTasks){
                                    currentTasks.add(rt);
                                }
                                selectedTasks.clear();
                                displayCurrentTasks();
                            }
                        });
                ListView lv = (ListView) convertView.findViewById(R.id.listView);
                ArrayList<RoutineTask> routineTasks =
                        new RoutineTaskRepository(CreateRoutineActivity.this)
                                .GetAll(CreateRoutineActivity.this);

                TaskListAdapter taskListAdapter = new TaskListAdapter(CreateRoutineActivity.this, routineTasks);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    private ArrayList<Integer> selectedIndeces = new ArrayList<>();

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if(!selectedIndeces.contains(position) || selectedIndeces.size() == 0) {
                            selectedIndeces.add(position);
                            parent.getChildAt(position).setBackgroundColor(Color.GREEN);
                            RoutineTask routineTask = (RoutineTask) parent.getItemAtPosition(position);
                            selectedTasks.add(routineTask);
                        }else{
                            selectedIndeces.remove(selectedIndeces.indexOf(position));
                            selectedTasks.remove(parent.getItemAtPosition(position));
                            view.setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                });
                lv.setAdapter(taskListAdapter);
                alertDialog.show();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (INTENT_RETURN_INT) : {
                if (resultCode == Activity.RESULT_OK) {
                    String taskNm = data.getStringExtra(CreateRoutineTaskActivity.PUBLIC_STATIC_STRING_IDENTIFIER);
                    RoutineTask rt = new RoutineTaskRepository(this).GetById(taskNm);
                    currentTasks.add(rt);
                    displayCurrentTasks();
                }
                break;
            }
        }
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

        Routine routine = new Routine(name, description, new ArrayList<RoutineTask>());
        return routine;
    }

    public boolean saveRoutine(){
        String routineNm = routineNmTV.getText().toString();
        String routineDesc = routineDescTV.getText().toString();
        Routine routine = new Routine(routineNm, routineDesc, currentTasks);

        boolean success = new RoutineRepository(this).Save(routine);
        return success;
    }

    private boolean isValidForm(){

        boolean isValidForm = true;
        String errorMsg = "";

        //Ensure Routine has a name with at least one character or digit
        if(!routineNmTV.getText().toString().matches(".*[a-zA-Z]+.*")){
            isValidForm = false;
            errorMsg = errorMsg + "Routine name must contain at least one valid letter or digit";
        }

        //Ensure Routine has at least one task
        if(currentTasks.size() == 0){
            isValidForm = false;
            errorMsg = errorMsg + "\n" + "Routine must contain at least one task";
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



    @Override
    protected void onResume(){
        super.onResume();
        tasksLV.setVisibility(View.GONE);
    }



}
