package com.example.stevenmaccoun.morningmoment.db;

import android.provider.BaseColumns;

/**
 * Created by stevenmaccoun on 4/18/16.
 */
public class RoutineContract {

    public RoutineContract(){}

    public static abstract class Routine implements BaseColumns {
        public static final String TABLE_NAME = "Routine";
        public static final String COLUMN_NAME_ROUTINE_ID = "routineid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }

}
