package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.database.Cursor;

import com.example.stevenmaccoun.morningmoment.db.MorningRoutineDbHelper;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by stevenmaccoun on 4/14/16.
 */
public class RoutineTaskManager {

    private static RoutineTaskManager instance = null;

    private Integer currentTaskNumber = 0;
    private Routine currentRoutine = null;

    private RoutineTaskManager(){}

    public static RoutineTaskManager getInstance(){
        if(instance == null){
            instance = new RoutineTaskManager();
        }

        return instance;
    }

    public RoutineTaskManager initializeRoutine(Context context, String routineNm)
    {
        instance.currentRoutine = loadRoutine(context, routineNm);
        instance.currentTaskNumber = 0;

        return instance;
    }

    public int setCurrentTaskToBeginning(){
        instance.currentTaskNumber = 0;
        return currentTaskNumber;
    }


    private Routine loadRoutine(Context context, String routineNm){

        String qRoutineNm = "\"" + routineNm + "\"";
        ArrayList<RoutineTask> routineTasks;

        String rtView =
                " SELECT rt._id, rt.task_nm, rt.task_desc, rt.duration_ms " +
                        " FROM RoutineTask rt " +
                        " INNER JOIN RoutineTaskBridge b " +
                        " ON b.task_nm = rt.task_nm " +
                        " WHERE b.routine_nm = " + qRoutineNm;

        Cursor c = MorningRoutineDbHelper.getHelper(context)
                .getWritableDatabase().rawQuery(rtView, null);

        TreeMap<Integer, RoutineTask> posRoutineTasks = new TreeMap<>();

        try
        {
            while (c.moveToNext()) {
                Integer pos = c.getInt(c.getColumnIndexOrThrow("_id"));
                String taskNm = c.getString(c.getColumnIndexOrThrow("task_nm"));
                String taskDesc = c.getString(c.getColumnIndexOrThrow("task_desc"));
                Integer taskDuration = c.getInt(c.getColumnIndexOrThrow("duration_ms"));
                posRoutineTasks.put(pos, new RoutineTask(taskNm, taskDesc, taskDuration));
            }
        } catch (Exception e){
            c.close();
        }

        routineTasks = new ArrayList<>(posRoutineTasks.values());
        currentRoutine = new Routine(routineNm, "default", routineTasks);

        return currentRoutine;
    }

    public Routine getCurrentRoutine(){
        return currentRoutine;
    }

    public Integer getCurrentTaskNumber() {
        return currentTaskNumber;
    }

    public Integer incrementCurrentTaskNumber(){
        currentTaskNumber += 1;
        if(currentTaskNumber >= instance.currentRoutine.getRoutineTasks().size()){
            return -1;
        }

        return currentTaskNumber;
    }

    public RoutineTask getCurrentTask(){
        if(currentTaskNumber >= instance.currentRoutine.getRoutineTasks().size()){

        }

        return instance.currentRoutine.getRoutineTasks().get(currentTaskNumber);
    }
}
