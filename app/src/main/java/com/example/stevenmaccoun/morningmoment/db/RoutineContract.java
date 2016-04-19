package com.example.stevenmaccoun.morningmoment.db;

import android.provider.BaseColumns;

/**
 * Created by stevenmaccoun on 4/18/16.
 */
public class RoutineContract {

    public RoutineContract(){}

    public static abstract class Routine implements BaseColumns {
        public static final String TABLE_NAME = "Routine";
        public static final String COLUMN_NAME_NM = "routine_nm";
        public static final String COLUMN_NAME_DESC = "routine_desc";
    }

    public static abstract class RoutineTask implements BaseColumns{
        public static final String TABLE_NAME = "RoutineTask";
        public static final String COLUMN_NAME_ROUTINE_NAME = "routine_nm";
        public static final String COLUMN_NAME_TITLE = "task_nm";
        public static final String COLUMN_NAME_DESC = "task_desc";
        public static final String COLUMN_NAME_DURATION_MS = "duration_ms";
    }

}
