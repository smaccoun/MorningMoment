package com.example.stevenmaccoun.morningmoment;

import java.util.ArrayList;

/**
 * Created by stevenmaccoun on 4/16/16.
 */
public class Routine {

    private String title;
    private ArrayList<RoutineTask> routineTasks;
    private long durationMillis;

    public Routine(String title, ArrayList<RoutineTask> routineTasks, long durationMillis) {
        this.title = title;
        this.routineTasks = new ArrayList<>(routineTasks);
        this.durationMillis = durationMillis;
    }

    public boolean addRoutineTask(RoutineTask routineTask){
        routineTasks.add(routineTask);
        return true;
    }

    public boolean removeRoutineTask(RoutineTask routineTask) {
        if (routineTasks.contains(routineTask)){
            routineTasks.remove(routineTask);
            return true;
        }

        return false;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<RoutineTask> getRoutineTasks() {
        return routineTasks;
    }

    public long getDurationMillis() {
        return durationMillis;
    }


}
