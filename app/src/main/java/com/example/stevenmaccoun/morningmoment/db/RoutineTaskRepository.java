package com.example.stevenmaccoun.morningmoment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.stevenmaccoun.morningmoment.RoutineTask;

import java.util.ArrayList;

/**
 * Created by stevenmaccoun on 4/25/16.
 */
public class RoutineTaskRepository implements IRepository<RoutineTask, String> {

    private Context context;

    public RoutineTaskRepository(Context context){
       this.context = context;
    }

    @Override
    public ArrayList<RoutineTask> GetAll(String s) {
        return null;
    }

    @Override
    public RoutineTask GetById(String s) {
        return null;
    }

    @Override
    public boolean Save(RoutineTask task) {
        SQLiteDatabase db = MorningRoutineDbHelper.getHelper(context).getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("routine_nm", task.getTitle());
        values.put("routine_desc", task.getDescription());
        values.put("duration_ms", task.getDurationString());

        boolean success = db.insert("RoutineTask", null, values) > 0;
        db.close();

        return success;
    }

    @Override
    public boolean Update(RoutineTask obj) {
        return false;
    }

    @Override
    public boolean Delete(String s) {
        return false;
    }
}
