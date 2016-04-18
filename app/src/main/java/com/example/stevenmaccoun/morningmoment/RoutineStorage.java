package com.example.stevenmaccoun.morningmoment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        cachedRoutines.put(title, routine);
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

    public HashMap<String, Routine> getAllRoutines(){
        return cachedRoutines;
    }

    public String[] getAllRoutineNames(){
        return cachedRoutines.keySet().toArray(new String[0]);
    }

    public void loadDefaultRoutines(){

        String routineName = "Standard Routine";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        ArrayList<RoutineTask> routineTasks = new ArrayList<>();
        RoutineTask alexander = null;
        RoutineTask tapping = null;
        RoutineTask integralBody = null;
        try {
            alexander = new RoutineTask("Alexander Laydown",
                    "Laydown on belly, extend each leg without flexing quads.", sdf.parse("00:01:00"));
            tapping = new RoutineTask("Presence/Resistance Tapping",
                    "Do a standard presence resistance tap.", sdf.parse("00:05:00"));
            integralBody = new RoutineTask("Integral Body 10", "Do 10 minute integral body work",
                                            sdf.parse("00:10:00"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        routineTasks.add(alexander);
        routineTasks.add(tapping);
        routineTasks.add(integralBody);

        cachedRoutines.put(routineName, new Routine(routineName, routineTasks, 160000));

        routineName = "Long Standard Routine";
        routineTasks = new ArrayList<>();
        RoutineTask yoga = null;
        try {
            yoga = new RoutineTask("Integral Body 10", "Do 10 minute integral body work",
                    sdf.parse("00:10:00"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        routineTasks.add(alexander);
        routineTasks.add(tapping);
        routineTasks.add(integralBody);
        routineTasks.add(yoga);

        cachedRoutines.put(routineName, new Routine(routineName, routineTasks, 160000));
    }

}
