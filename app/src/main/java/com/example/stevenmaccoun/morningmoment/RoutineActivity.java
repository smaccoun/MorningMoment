package com.example.stevenmaccoun.morningmoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RoutineActivity extends AppCompatActivity {

    private ListView routineTasksLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        routineTasksLV = (ListView) findViewById(R.id.routine_items);
        
        initializeRoutineTasks();

    }

    private void initializeRoutineTasks(){

        String[] taskList = new String[] {
            "Alexander Stomach",
            "5 minute tapping",
            "10 minute integral body",
                "10 minute intention setting"
        };

        ArrayAdapter<String> routineTasksLVAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, taskList);

        routineTasksLV.setAdapter(routineTasksLVAdapter);

        routineTasksLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;

                String itemValue = (String) routineTasksLV.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

                
            }
        });
    }


}
