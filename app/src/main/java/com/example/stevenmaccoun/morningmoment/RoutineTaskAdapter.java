package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by stevenmaccoun on 4/18/16.
 */
public class RoutineTaskAdapter extends CursorAdapter {
    public RoutineTaskAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_routine_task_lv, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView taskTtile = (TextView) view.findViewById(R.id.task_title_lv);
        TextView taskDescription = (TextView) view.findViewById(R.id.task_description_lv);
        TextView taskDuration = (TextView) view.findViewById(R.id.task_duration_lv);
        // Populate the data into the template view using the data object

        String taskNm = cursor.getString(cursor.getColumnIndexOrThrow("task_nm"));
        String taskDesc = cursor.getString(cursor.getColumnIndexOrThrow("task_desc"));

        taskTtile.setText(taskNm);
        taskDescription.setText(taskDesc);
    }
}
