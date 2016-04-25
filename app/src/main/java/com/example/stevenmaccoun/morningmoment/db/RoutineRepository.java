package com.example.stevenmaccoun.morningmoment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.stevenmaccoun.morningmoment.Routine;

import java.util.ArrayList;

/**
 * Created by stevenmaccoun on 4/25/16.
 */
public class RoutineRepository implements IRepository<Routine, String> {

    private Context context;
    private static final String ROUTINE_TABLE_NAME = "Routine";

    public RoutineRepository(Context c){
        this.context = c;
    }


    @Override
    public ArrayList<Routine> GetAll(String s) {
        return null;
    }

    @Override
    public Routine GetById(String s) {
        return null;
    }

    @Override
    public boolean Save(Routine routine) {
        SQLiteDatabase db = MorningRoutineDbHelper.getHelper(context).getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("routine_nm", routine.getName());
        values.put("routine_desc", routine.getDescription());
        values.put("duration_ms", routine.getDurationText());

        boolean success = db.insert(ROUTINE_TABLE_NAME, null, values) > 0;
        db.close();

        return success;
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
