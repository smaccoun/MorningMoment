package com.example.stevenmaccoun.morningmoment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stevenmaccoun on 4/14/16.
 */
public class RoutineTask {

    private String title;
    private String description;
    private Date duration;

    public RoutineTask(String title, String description, Date duration){
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDurationDate() {
        return duration;
    }

    public String getDurationString(){
        return new SimpleDateFormat("HH:mm:ss").format(duration);
    }


}
