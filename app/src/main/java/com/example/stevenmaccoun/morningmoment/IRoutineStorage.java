package com.example.stevenmaccoun.morningmoment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * CRUD Interface for caching routines
 */
public interface IRoutineStorage {

    HashMap<Integer, Routine> cachedRoutines = new HashMap<>();

    Routine createRoutine(String title, ArrayList<RoutineTask> routineTasks, Date duration);

    boolean deleteRoutine(String title);

}
