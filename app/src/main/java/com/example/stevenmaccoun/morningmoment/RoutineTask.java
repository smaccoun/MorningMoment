package com.example.stevenmaccoun.morningmoment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by stevenmaccoun on 4/14/16.
 */
public class RoutineTask {

    private String title;
    private String description;
    private long durationSeconds;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);

    public RoutineTask(String title, String description, long durationSeconds){
        this.title = title;
        this.description = description;
        this.durationSeconds = durationSeconds;

        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
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
        return sdf.format(new Date(durationSeconds));
    }


}
