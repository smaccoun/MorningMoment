package com.example.stevenmaccoun.morningmoment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stevenmaccoun on 4/14/16.
 */
public class RoutineTaskManager {

    private String routineName;
    private HashMap<Integer, RoutineTask> routineActivities;
    private Integer currentTaskNumber;

    public RoutineTaskManager(String routineName, HashMap<Integer, RoutineTask> routineActivities,
                              Integer currentTaskNumber)
    {
        this.routineName = new String(routineName);
        this.routineActivities = new HashMap<Integer, RoutineTask>(routineActivities);
        this.currentTaskNumber = new Integer(currentTaskNumber);
    }

    public Integer getCurrentTaskNumber() {
        return currentTaskNumber;
    }

    public Integer incrementCurrentTaskNumber(){
        currentTaskNumber += 1;
        return currentTaskNumber;
    }

    public RoutineTask getCurrentTask(){
        return routineActivities.get(currentTaskNumber);
    }
}
