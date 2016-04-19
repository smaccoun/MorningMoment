package com.example.stevenmaccoun.morningmoment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * CRUD Interface for RoutineTasks
 */
public interface IRoutineTaskStorage {

    HashMap<String, RoutineTask> allRoutineTasks = new HashMap<>();

    RoutineTask createTask(String title, String description, long durationMillis);

    void destroyTask(String title);

    void updateTaskDescription(String title, String description);

    void updateTaskDuration(String title, Date duration);
}
