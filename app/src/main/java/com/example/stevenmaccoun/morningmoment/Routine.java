package com.example.stevenmaccoun.morningmoment;

import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

import java.util.ArrayList;

/**
 * Created by stevenmaccoun on 4/16/16.
 */
public class Routine {

    private String name;
    private String description;
    private ArrayList<RoutineTask> routineTasks;
    private long durationMillis = 0;

    public String getDescription() {
        return description;
    }

    public Routine(String name, String description, ArrayList<RoutineTask> routineTasks) {
        this.name = name;
        this.description = description;

        this.routineTasks = new ArrayList<>(routineTasks);

        for(RoutineTask rt : routineTasks){
            this.durationMillis += rt.getDurationMillis();
        }
    }

    public boolean addRoutineTask(RoutineTask routineTask){
        routineTasks.add(routineTask);
        this.durationMillis += routineTask.getDurationMillis();
        return true;
    }

    public boolean removeRoutineTask(RoutineTask routineTask) {
        if (routineTasks.contains(routineTask)){
            routineTasks.remove(routineTask);
            return true;
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public ArrayList<RoutineTask> getRoutineTasks() {
        return routineTasks;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public String getDurationText() {
        return DateFormatHandler.toString(durationMillis);
    }


}
