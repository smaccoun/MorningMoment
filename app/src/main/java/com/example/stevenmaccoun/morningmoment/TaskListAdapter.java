package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<RoutineTask> {
    public TaskListAdapter(Context context, ArrayList<RoutineTask> routineTasks) {
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

        return convertView;
    }
}