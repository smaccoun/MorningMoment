package com.example.stevenmaccoun.morningmoment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by stevenmaccoun on 4/16/16.
 */
public class RoutineStorage implements IRoutineStorage {

    @Override
    public Routine createRoutine(String title, ArrayList<RoutineTask> routineTasks, Date duration) {
        Routine routine = new Routine(title, routineTasks, duration.getTime());
        cachedRoutines.put(1, routine);
        return routine;
    }

    @Override
    public boolean deleteRoutine(String title) {

        if (cachedRoutines.containsKey(title)){
            cachedRoutines.remove(title);
            return true;
        }

        return false;
    }

    public HashMap<Integer, Routine> getAllRoutines(){
        return cachedRoutines;
    }

    public String[] getAllRoutineNames(){
        return cachedRoutines.keySet().toArray(new String[0]);
    }



}
