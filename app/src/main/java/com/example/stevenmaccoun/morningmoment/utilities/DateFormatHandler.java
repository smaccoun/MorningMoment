package com.example.stevenmaccoun.morningmoment.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by stevenmaccoun on 4/19/16.
 */
public class DateFormatHandler {

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
    private static DateFormatHandler instance = null;

    private DateFormatHandler(){
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static DateFormatHandler getInstance(){
        if(instance==null){
            return new DateFormatHandler();
        }

        return instance;
    }

    public long toLong(String sdfString) throws ParseException {
        return sdf.parse(sdfString).getTime();
    }

    public String toString(long millis){
        return sdf.format(new Date(millis));
    }
}
