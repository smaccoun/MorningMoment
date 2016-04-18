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
public class RoutineAdapter extends CursorAdapter {
    public RoutineAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.routine_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvDescription = (TextView) view.findViewById(R.id.description);

        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

        tvTitle.setText(title);
        tvDescription.setText(description);
    }
}
