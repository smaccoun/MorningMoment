package com.example.stevenmaccoun.morningmoment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stevenmaccoun.morningmoment.Routine;
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

    public ArrayList<RoutineTask> GetAll(Context c) {
        SQLiteDatabase db = MorningRoutineDbHelper.getHelper(context).getWritableDatabase();

        Cursor cursor =
                db.rawQuery("SELECT _id, task_nm, task_desc, duration_ms " +
                        " FROM RoutineTask", null);

        ArrayList<RoutineTask> routineTasks = new ArrayList<>();

        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow("task_nm"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("task_desc"));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration_ms"));

            RoutineTask rt = new RoutineTask(name, description, duration);

            routineTasks.add(rt);
        }

        return routineTasks;
    }



    @Override
    public ArrayList<RoutineTask> GetAll() {
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

        values.put("task_nm", task.getTitle());
        values.put("task_desc", task.getDescription());
        values.put("duration_ms", task.getDurationMillis());

        boolean success = db.insert("RoutineTask", null, values) > 0;
        db.close();

        return success;
    }

    public static boolean Save(SQLiteDatabase db, Routine routine) {

        ContentValues values = new ContentValues();
        boolean success = true;

        for(RoutineTask task : routine.getRoutineTasks()){
            if (doesTaskExist(db, task.getTitle())) {
                continue;
            }
            values.put("task_nm", task.getTitle());
            values.put("task_desc", task.getDescription());
            values.put("duration_ms", task.getDurationMillis());

            success = success & (db.insert("RoutineTask", null, values) > 0);
        }

        return success;
    }

    private static boolean doesTaskExist(SQLiteDatabase db, String taskNm){
        Cursor c = null;

        String query = "select count(*) from RoutineTask WHERE task_nm = ?";
        c = db.rawQuery(query, new String[] {taskNm});
        if (c.moveToFirst()) {
            return c.getInt(0) > 0;
        }
        return false;

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
