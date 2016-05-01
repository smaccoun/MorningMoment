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
        public static final String COLUMN_NAME_DURATION_MS = "duration_ms";
    }

    public static abstract class RoutineTask implements BaseColumns{
        public static final String TABLE_NAME = "RoutineTask";
        public static final String COLUMN_NAME_TITLE = "task_nm";
        public static final String COLUMN_NAME_DESC = "task_desc";
        public static final String COLUMN_NAME_DURATION_MS = "duration_ms";
        public static final String COLUMN_NAME_WEB_URL = "web_url";
        public static final String COLUMN_NAME_VIDEO_URL = "video_url";
    }

    public static abstract class RoutineTaskBridge implements BaseColumns{
        public static final String TABLE_NAME = "RoutineTaskBridge";
        public static final String COLUMN_NAME_ROUTINE_NM = "routine_nm";
        public static final String COLUMN_NAME_TASK_NM = "task_nm";
        public static final String COLUMN_ORDER_NO = "order_no";
    }

}
