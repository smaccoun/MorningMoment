package com.example.stevenmaccoun.morningmoment;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stevenmaccoun on 4/14/16.
 */
public class RoutineTaskManager {

    private static RoutineTaskManager instance = null;

    private String routineName;
    private ArrayList<RoutineTask> routineActivities;
    private Integer currentTaskNumber = 0;

    private RoutineTaskManager(){}

    public static RoutineTaskManager getInstance(){
        if(instance == null){
            instance = new RoutineTaskManager();
        }

        return instance;
    }

    public RoutineTaskManager initializeRoutine(String routineName, ArrayList<RoutineTask> routineActivities)
    {
        instance.routineName = new String(routineName);
        instance.routineActivities = new ArrayList<RoutineTask>(routineActivities);
        instance.currentTaskNumber = new Integer(0);

        return instance;
    }

    public Integer getCurrentTaskNumber() {
        return currentTaskNumber;
    }

    public Integer incrementCurrentTaskNumber(){
        currentTaskNumber += 1;
        if(currentTaskNumber >= instance.routineActivities.size()){
            return -1;
        }

        return currentTaskNumber;
    }

    public RoutineTask getCurrentTask(){
        return routineActivities.get(currentTaskNumber);
    }
}
