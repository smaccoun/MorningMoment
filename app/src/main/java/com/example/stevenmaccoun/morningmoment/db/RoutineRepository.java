package com.example.stevenmaccoun.morningmoment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.stevenmaccoun.morningmoment.Routine;
import com.example.stevenmaccoun.morningmoment.RoutineTask;

import java.util.ArrayList;

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
        return null;
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
        values.put("duration_ms", routine.getDurationText());

        boolean success = db.insert(ROUTINE_TABLE_NAME, null, values) > 0;
        return success;
    }


    private boolean insertRoutineTaskBridgeRows(SQLiteDatabase db, Routine routine){
        ContentValues values = new ContentValues();
        String routineNm = routine.getName();

        boolean success = true;

        for(RoutineTask rt : routine.getRoutineTasks()){
            values.put("routine_nm", routineNm);
            values.put("routine_task_nm", rt.getTitle());

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
