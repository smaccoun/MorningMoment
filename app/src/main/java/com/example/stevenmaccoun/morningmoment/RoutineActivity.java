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
import android.widget.Toast;

import java.util.ArrayList;

public class RoutineActivity extends AppCompatActivity {

    private ListView routineTasksLV;
    private Button beginRoutineB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        routineTasksLV = (ListView) findViewById(R.id.routine_items);

        Routine currentRoutine = RoutineTaskManager.getInstance().getCurrentRoutine();
        populateRoutineTasksLV(currentRoutine);

        beginRoutineB = (Button) findViewById(R.id.begin_routine);
        beginRoutineB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                routineTasksLV.setItemChecked(1, true);
                RoutineTaskManager.getInstance().setCurrentTaskToBeginning();
                Intent i = new Intent(RoutineActivity.this, RoutineTaskActivity.class);
                startActivity(i);

            }
        });
    }




    private void populateRoutineTasksLV(Routine routine) {

        ArrayList<RoutineTask> routineTasks = routine.getRoutineTasks();
        TaskListAdapter taskListAdapter = new TaskListAdapter(this, routineTasks);
        taskListAdapter.notifyDataSetChanged();
        routineTasksLV.setAdapter(taskListAdapter);
    }

}
