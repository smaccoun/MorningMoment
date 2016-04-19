package com.example.stevenmaccoun.morningmoment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stevenmaccoun.morningmoment.RoutineTask;

import java.util.ArrayList;

/**
 * Created by stevenmaccoun on 4/18/16.
 */
public class MorningRoutineDbHelper extends SQLiteOpenHelper {

    private static MorningRoutineDbHelper instance;

    public static synchronized MorningRoutineDbHelper getHelper(Context context){
        if(instance == null){
            instance = new MorningRoutineDbHelper(context);
        }

        return instance;
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MorningRoutine.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ROUTINE_TABLE =
            "CREATE TABLE " + RoutineContract.Routine.TABLE_NAME + "(" +
                    RoutineContract.Routine._ID + " INTEGER PRIMARY KEY," +
                    RoutineContract.Routine.COLUMN_NAME_NM + TEXT_TYPE + COMMA_SEP +
                    RoutineContract.Routine.COLUMN_NAME_DESC + TEXT_TYPE + ")";

    private static final String SQL_CREATE_ROUTINE_TASK_TABLE =
            "CREATE TABLE " + RoutineContract.RoutineTask.TABLE_NAME + "(" +
                    RoutineContract.RoutineTask._ID + " INTEGER PRIMARY KEY," +
                    RoutineContract.RoutineTask.COLUMN_NAME_ROUTINE_NAME + TEXT_TYPE + COMMA_SEP +
                    RoutineContract.RoutineTask.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    RoutineContract.RoutineTask.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
                    RoutineContract.RoutineTask.COLUMN_NAME_DURATION_MS + INTEGER_TYPE + ")";



    public MorningRoutineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ROUTINE_TABLE);
        db.execSQL(SQL_CREATE_ROUTINE_TASK_TABLE);
        insertDefaultRoutines(db);
    }

    private long insertDefaultRoutines(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(RoutineContract.Routine.COLUMN_NAME_NM, "STANDARD ROUTINE");
        values.put(RoutineContract.Routine.COLUMN_NAME_DESC, "Most common daily routine");

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                RoutineContract.Routine.TABLE_NAME,
                null,
                values);

        ContentValues taskValues = new ContentValues();
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_ROUTINE_NAME, "STANDARD ROUTINE");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_TITLE, "Alexander Technique");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_DESC, "Lie on yo stomach and move yo legs DAWG");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_DURATION_MS, 30000);

        newRowId = db.insert(
                RoutineContract.RoutineTask.TABLE_NAME,
                null,
                taskValues);

        taskValues = new ContentValues();
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_ROUTINE_NAME, "STANDARD ROUTINE");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_TITLE, "Integral Bodywork");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_DESC, "Open up the integral bodywork module!");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_DURATION_MS, 3000);

        newRowId = db.insert(
                RoutineContract.RoutineTask.TABLE_NAME,
                null,
                taskValues);


        return newRowId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}