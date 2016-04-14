package com.example.stevenmaccoun.morningmoment;

import java.text.SimpleDateFormat;

/**
 * Created by stevenmaccoun on 4/14/16.
 */
public class RoutineTask {

    private String title;
    private String description;
    private SimpleDateFormat duration;

    public RoutineTask(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public SimpleDateFormat getDuration() {
        return duration;
    }


}
