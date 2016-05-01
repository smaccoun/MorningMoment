package com.example.stevenmaccoun.morningmoment;

import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

import java.net.URI;
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
    private long durationMillis;
    private String webUrlLink;
    private String videoUrlPath;

    public RoutineTask(String title, String description, long durationSeconds, String webUrlLink){
        this.title = title;
        this.description = description;
        this.durationMillis = durationSeconds;
        this.webUrlLink = webUrlLink;
    }


    public RoutineTask(String title, String description, long durationSeconds,
                       String webUrlLink, String videoUrlPath){
        this.title = title;
        this.description = description;
        this.durationMillis = durationSeconds;
        this.webUrlLink = webUrlLink;
        this.videoUrlPath = videoUrlPath;
    }

    public String getVideoUrlPath() {
        return videoUrlPath;
    }

    public String getWebUrlLink() {
        return webUrlLink;
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

    public void setDurationMillis(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public String getDurationString(){
        return DateFormatHandler.toString(durationMillis);
    }

    public long getDurationMillis(){
        return durationMillis;
    }


}
