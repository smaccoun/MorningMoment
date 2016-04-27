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

    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);

    static {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    }


    public static long toLong(String sdfString)  {
        Long millis = null;

        try {
            millis = sdf.parse(sdfString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  millis;
    }

    public static String toString(long millis){
        return sdf.format(new Date(millis));
    }
}
