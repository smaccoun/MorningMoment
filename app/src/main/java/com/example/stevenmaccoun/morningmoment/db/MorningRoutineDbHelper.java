package com.example.stevenmaccoun.morningmoment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

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
    private static final String TEXT_TYPE_NOT_NULL = " TEXT NOT NULL ";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String INTEGER_NOT_NULL_TYPE = " INTEGER NOT NULL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ROUTINE_TABLE =
            "CREATE TABLE " + RoutineContract.Routine.TABLE_NAME + "(" +
                    RoutineContract.Routine._ID + " INTEGER PRIMARY KEY," +
                    RoutineContract.Routine.COLUMN_NAME_NM + " TEXT UNIQUE NOT NULL " + COMMA_SEP +
                    RoutineContract.Routine.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
                    RoutineContract.Routine.COLUMN_NAME_DURATION_MS + INTEGER_TYPE + ")";

    private static final String SQL_CREATE_ROUTINE_TASK_TABLE =
            "CREATE TABLE " + RoutineContract.RoutineTask.TABLE_NAME + "(" +
                    RoutineContract.RoutineTask._ID + " INTEGER PRIMARY KEY," +
                    RoutineContract.RoutineTask.COLUMN_NAME_TITLE + " TEXT UNIQUE NOT NULL " + COMMA_SEP +
                    RoutineContract.RoutineTask.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
                    RoutineContract.RoutineTask.COLUMN_NAME_DURATION_MS + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.RoutineTask.COLUMN_NAME_WEB_URL + " TEXT DEFAULT ''" + COMMA_SEP +
                    RoutineContract.RoutineTask.COLUMN_NAME_VIDEO_URL + " TEXT DEFAULT ''" + ")";

    private static final String SQL_CREATE_ROUTINE_TASK_BRIDGE_TABLE =
            "CREATE TABLE " + RoutineContract.RoutineTaskBridge.TABLE_NAME + "(" +
                    RoutineContract.RoutineTask._ID + " INTEGER PRIMARY KEY," +
                    RoutineContract.RoutineTaskBridge.COLUMN_NAME_ROUTINE_NM + TEXT_TYPE_NOT_NULL
                            + COMMA_SEP +
                    RoutineContract.RoutineTaskBridge.COLUMN_NAME_TASK_NM + TEXT_TYPE_NOT_NULL
                            + COMMA_SEP +
                    RoutineContract.RoutineTaskBridge.COLUMN_ORDER_NO + INTEGER_NOT_NULL_TYPE + ")";


    /*
       ***** View Creation ********
     */

    private static final String SQL_CREATE_GET_ROUTINE_TASKS_VW =
            "CREATE VIEW IF NOT EXISTS get_routines_tasks_vw AS " +
                    "SELECT r._id AS routine_id, " +
                            "r.routine_nm, " +
                            "r.routine_desc, " +
                            "rt.task_nm, " +
                            "rt.task_desc, " +
                            "rt.duration_ms AS task_duration, " +
                            "rt.web_url, " +
                            "rt.video_url, " +
                            "b.order_no" +
                    " FROM Routine r " +
                    " INNER JOIN RoutineTaskBridge b ON b.routine_nm = r.routine_nm" +
                    " INNER JOIN RoutineTask rt ON rt.task_nm = b.task_nm";



    public MorningRoutineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create all tables
        db.execSQL(SQL_CREATE_ROUTINE_TABLE);
        db.execSQL(SQL_CREATE_ROUTINE_TASK_TABLE);
        db.execSQL(SQL_CREATE_ROUTINE_TASK_BRIDGE_TABLE);

        //Create all views
        db.execSQL(SQL_CREATE_GET_ROUTINE_TASKS_VW);

        //Load default data
        insertDefaultRoutines(db);
    }

    private long insertDefaultRoutines(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(RoutineContract.Routine.COLUMN_NAME_NM, "STANDARD ROUTINE");
        values.put(RoutineContract.Routine.COLUMN_NAME_DESC, "Most common daily routine");
        values.put(RoutineContract.Routine.COLUMN_NAME_DURATION_MS, DateFormatHandler.toLong("00:05:00"));

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                RoutineContract.Routine.TABLE_NAME,
                null,
                values);

        ContentValues taskValues = new ContentValues();
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_TITLE, "Alexander Technique");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_DESC, "Lie on yo stomach and move yo legs DAWG");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_DURATION_MS, 120000);

        newRowId = db.insert(
                RoutineContract.RoutineTask.TABLE_NAME,
                null,
                taskValues);

        ContentValues bridgeValues = new ContentValues();
        bridgeValues.put(RoutineContract.RoutineTaskBridge.COLUMN_NAME_ROUTINE_NM, "STANDARD ROUTINE");
        bridgeValues.put(RoutineContract.RoutineTaskBridge.COLUMN_NAME_TASK_NM, "Alexander Technique");
        bridgeValues.put(RoutineContract.RoutineTaskBridge.COLUMN_ORDER_NO, 1);

        newRowId = db.insert(
                RoutineContract.RoutineTaskBridge.TABLE_NAME,
                null,
                bridgeValues);

        taskValues = new ContentValues();
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_TITLE, "Integral Bodywork");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_DESC, "Open up the integral bodywork module!");
        taskValues.put(RoutineContract.RoutineTask.COLUMN_NAME_DURATION_MS, 1000000);

        newRowId = db.insert(
                RoutineContract.RoutineTask.TABLE_NAME,
                null,
                taskValues);

        bridgeValues = new ContentValues();
        bridgeValues.put(RoutineContract.RoutineTaskBridge.COLUMN_NAME_ROUTINE_NM, "STANDARD ROUTINE");
        bridgeValues.put(RoutineContract.RoutineTaskBridge.COLUMN_NAME_TASK_NM, "Integral Bodywork");
        bridgeValues.put(RoutineContract.RoutineTaskBridge.COLUMN_ORDER_NO, 2);

        newRowId = db.insert(
                RoutineContract.RoutineTaskBridge.TABLE_NAME,
                null,
                bridgeValues);


        return newRowId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
