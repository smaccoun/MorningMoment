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
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stevenmaccoun.morningmoment.db.MorningRoutineDbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RoutineActivity extends AppCompatActivity {

    private ListView routineTasksLV;
    private Button beginRoutineB;

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

        String rtNmString = '"' + previousIntent.getStringExtra("routine_nm") + '"';

        String rtView =
                " SELECT rt._id, rt.task_nm, rt.task_desc " +
                        " FROM RoutineTask rt " +
                        " INNER JOIN Routine r " +
                        " ON rt.routine_nm = r.routine_nm" +
                        " WHERE r.routine_nm = " + rtNmString;

        Cursor c =
                MorningRoutineDbHelper.getHelper(getApplicationContext())
                .getWritableDatabase().rawQuery(rtView, null);

        HashMap<Integer, RoutineTask> routineTaskHashMap = new HashMap<Integer, RoutineTask>();
        try {
            c.moveToFirst();
            while (c.moveToNext()) {
                RoutineTask rt = new RoutineTask(c.getString(1), c.getString(2), 3000);
                routineTaskHashMap.put(c.getPosition(), rt);
            }
        } catch (Exception e){
            c.close();
        }

        RoutineTaskManager rtm = new RoutineTaskManager(previousIntent.getStringExtra("routine_nm"),
                routineTaskHashMap);

        return rtm;
    }

    private void initializeRoutineTasksLV(){

        String rtNmString = '"' + previousIntent.getStringExtra("routine_nm") + '"';

        String rtView =
                " SELECT rt._id, rt.task_nm, rt.task_desc, rt.duration_ms " +
                " FROM RoutineTask rt " +
                " INNER JOIN Routine r " +
                " ON rt.routine_nm = r.routine_nm" +
                        " WHERE r.routine_nm = " + rtNmString;

        Cursor routineTasksLVCursor = MorningRoutineDbHelper
                                    .getHelper(getApplicationContext())
                                    .getWritableDatabase().rawQuery(rtView, null);
        RoutineTaskAdapter routineTaskAdapter = new RoutineTaskAdapter(this, routineTasksLVCursor, 0);
        routineTasksLV.setAdapter(routineTaskAdapter);

        routineTasksLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor c = (Cursor) routineTasksLV.getItemAtPosition(position);
                RoutineTask rt = new RoutineTask(c.getString(1), c.getString(2), c.getInt(3));
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

    private class RoutineTaskAdapter extends CursorAdapter {
        public RoutineTaskAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.activity_routine_task_lv, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView taskTitleTV = (TextView) view.findViewById(R.id.task_title_lv);
            TextView taskDescriptionTV = (TextView) view.findViewById(R.id.task_description_lv);
            TextView taskDurationTV = (TextView) view.findViewById(R.id.task_duration_lv);
            // Populate the data into the template view using the data object

            String taskNm = cursor.getString(cursor.getColumnIndexOrThrow("task_nm"));
            String taskDesc = cursor.getString(cursor.getColumnIndexOrThrow("task_desc"));
            Integer taskDuration = cursor.getInt(cursor.getColumnIndexOrThrow("duration_ms"));

            RoutineTask rt = new RoutineTask(taskNm, taskDesc, taskDuration);

            taskTitleTV.setText(rt.getTitle());
            taskDescriptionTV.setText(rt.getDescription());
            taskDurationTV.setText(rt.getDurationString());
        }
    }


}
