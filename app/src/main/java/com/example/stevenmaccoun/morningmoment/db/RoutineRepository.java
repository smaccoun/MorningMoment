package com.example.stevenmaccoun.morningmoment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stevenmaccoun.morningmoment.Routine;
import com.example.stevenmaccoun.morningmoment.RoutineTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by stevenmaccoun on 4/25/16.
 */
public class RoutineRepository implements IRepository<Routine, String> {

    private Context context;
    private static final String ROUTINE_TABLE_NAME = "Routine";
    private static final String ROUTINE_TASK_BRIDGE_TABLE_NAME = "RoutineTaskBridge";

    public RoutineRepository(Context c){
        this.context = c;
    }



    @Override
    public ArrayList<Routine> GetAll() {
        SQLiteDatabase db = MorningRoutineDbHelper.getHelper(context).getWritableDatabase();
        Cursor cursor =
                db.rawQuery("SELECT r._id, r.routine_nm, r.routine_desc, " +
                                    "rt.task_nm, rt.task_desc, rt.duration_ms" +
                            " FROM Routine r " +
                            " INNER JOIN RoutineTaskBridge b ON b.routine_nm = r.routine_nm" +
                            " INNER JOIN RoutineTask rt ON rt.task_nm = b.task_nm", null);

        HashMap<String, Routine> routineMap = new HashMap<>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String routineNm = cursor.getString(cursor.getColumnIndexOrThrow("routine_nm"));
                String routineDesc = cursor.getString(cursor.getColumnIndexOrThrow("routine_desc"));
                String taskNm = cursor.getString(cursor.getColumnIndexOrThrow("task_nm"));
                String taskDesc = cursor.getString(cursor.getColumnIndexOrThrow("task_desc"));
                int taskDuration = cursor.getInt(cursor.getColumnIndexOrThrow("duration_ms"));
                RoutineTask rt = new RoutineTask(taskNm, taskDesc, taskDuration);

                if(routineMap.containsKey(routineNm)){
                    routineMap.get(routineNm).addRoutineTask(rt);
                }
                else{
                    ArrayList<RoutineTask> tasks = new ArrayList<>();
                    tasks.add(rt);
                    Routine routine = new Routine(routineNm, routineDesc, tasks);
                    routineMap.put(routineNm, routine);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }

        ArrayList<Routine> routines = new ArrayList<>();
        for(Routine r : routineMap.values()){
            routines.add(r);
        }

        return routines;
    }



    @Override
    public Routine GetById(String s) {

        return null;
    }

    @Override
    public boolean Save(Routine routine) {
        SQLiteDatabase db = MorningRoutineDbHelper.getHelper(context).getWritableDatabase();

        boolean success;
        success = insertRoutineRows(db, routine) &
                  insertRoutineTaskBridgeRows(db, routine) &
                  insertRoutineTaskRows(db, routine);

        db.close();

        return success;
    }

    private boolean insertRoutineRows(SQLiteDatabase db, Routine routine){

        ContentValues values = new ContentValues();

        values.put("routine_nm", routine.getName());
        values.put("routine_desc", routine.getDescription());
        values.put("duration_ms", routine.getDurationMillis());

        boolean success = db.insert(ROUTINE_TABLE_NAME, null, values) > 0;
        return success;
    }


    private boolean insertRoutineTaskBridgeRows(SQLiteDatabase db, Routine routine){
        ContentValues values = new ContentValues();
        String routineNm = routine.getName();

        boolean success = true;

        for(RoutineTask rt : routine.getRoutineTasks()){
            values.put("routine_nm", routineNm);
            values.put("task_nm", rt.getTitle());

            success = success & db.insert(ROUTINE_TASK_BRIDGE_TABLE_NAME, null, values) > 0;
        }

        return success;
    }

    private boolean insertRoutineTaskRows(SQLiteDatabase db, Routine routine){
        return RoutineTaskRepository.Save(db, routine);
    }

    @Override
    public boolean Update(Routine obj) {
        return false;
    }

    @Override
    public boolean Delete(String s) {
        return false;
    }
}
