package com.example.stevenmaccoun.morningmoment;

import java.text.SimpleDateFormat;

/**
 * Created by stevenmaccoun on 4/14/16.
 */
public class RoutineTask {

    private String title;
    private String description;
    private long durationSeconds;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public RoutineTask(String title, String description, long durationSeconds){
        this.title = title;
        this.description = description;
        this.durationSeconds = durationSeconds;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationSeconds(long durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getDurationString(){
        return sdf.format(durationSeconds);
    }


}
