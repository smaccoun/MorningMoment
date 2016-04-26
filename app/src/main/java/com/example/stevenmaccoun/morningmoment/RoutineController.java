package com.example.stevenmaccoun.morningmoment;


import android.content.Context;

import com.example.stevenmaccoun.morningmoment.db.RoutineRepository;

/**
 * Singleton with one time instantion of state.
 * Used to create Routines and should be re-initialized
 * every time a new Routine is being created.
 */
public class RoutineController {

    private static RoutineController instance = null;

    private boolean isInitialized;
    private Routine routine; //The current routine

    private RoutineController(){
        isInitialized = false;
        routine = null;
    }

    public static RoutineController getInstance(){
        if(instance == null){
           instance = new RoutineController();
        }

        return instance;
    }

    public void initialize(Routine routine){
        if(isInitialized && routine.getName().equals(this.routine.getName())){
            return;
        }

        this.routine = routine;
        this.isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void addTask(RoutineTask task){
        this.routine.addRoutineTask(task);
    }

    public boolean persistRoutine(Context c){
        boolean success = new RoutineRepository(c).Save(this.routine);
        refresh();
        return success;
    }

    public void refresh(){
        this.instance = null;
    }

    public Routine getRoutine() {
        return routine;
    }
}
