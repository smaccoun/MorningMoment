package com.example.stevenmaccoun.morningmoment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stevenmaccoun on 4/18/16.
 */
public class MorningRoutineDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MorningRoutine.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RoutineContract.Routine.TABLE_NAME + "(" +
                    RoutineContract.Routine._ID + " INTEGER PRIMARY KEY," +
                    RoutineContract.Routine.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    RoutineContract.Routine.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + ")";



    public MorningRoutineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        insertDefaultRoutines(db);
    }

    private long insertDefaultRoutines(SQLiteDatabase db){
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(RoutineContract.Routine.COLUMN_NAME_TITLE, "STANDARD ROUTINE");
        values.put(RoutineContract.Routine.COLUMN_NAME_DESCRIPTION, "Most common daily routine");

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                RoutineContract.Routine.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
