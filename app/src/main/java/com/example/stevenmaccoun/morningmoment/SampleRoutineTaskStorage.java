package com.example.stevenmaccoun.morningmoment;

import java.util.Date;

/**
 * Temporary class for storing routineTasks without a database
 */
public class SampleRoutineTaskStorage implements IRoutineTaskStorage {

    @Override
    public RoutineTask createTask(String title, String description, Date duration)
    {
        RoutineTask rt = new RoutineTask(title, description, duration);
        allRoutineTasks.put(title, rt);
        return rt;
    }

    @Override
    public void destroyTask(String title) {
        allRoutineTasks.remove(title);
    }

    @Override
    public void updateTaskDescription(String title, String description) {
        allRoutineTasks.get(title).setDescription(description);
    }

    @Override
    public void updateTaskDuration(String title, Date duration) {
        allRoutineTasks.get(title).setDuration(duration);
    }
}
