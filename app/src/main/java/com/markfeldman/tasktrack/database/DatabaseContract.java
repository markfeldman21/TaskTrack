package com.markfeldman.tasktrack.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String CONTENT_AUTHORITY = "com.markfeldman.tasktrack";
    //Uri for othet apps to be able to access
    public final static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //Possible paths that can be appended to BASE_CONTENT_URI to form valid URI

    public static final String TASK_TABLE = "task_table";
    public static final String CONTACTS_TABLE = "contacts_table";
    public static final String SELECTED_TASKS_TABLE = "selected_task_table";

    public static final class TasksContract implements BaseColumns {
        public final static Uri CONTENT_URI_TASKS = BASE_CONTENT_URI.buildUpon().appendPath(TASK_TABLE).build();
        public static final String TABLE_NAME = "tasks_table";
        public static final String TASK = "task";
    }

    public static final class ContactsContract implements BaseColumns {
        public final static Uri CONTENT_URI_CONTACTS = BASE_CONTENT_URI.buildUpon().appendPath(CONTACTS_TABLE).build();
        public static final String TABLE_NAME = "contacts_table";
        public static final String CONTACT_NAME = "contact_name";
        public static final String CONTACT_NUMBER = "contact_number";
    }

    public static final class SelectedTasks implements BaseColumns{
        public final static Uri CONTENT_URI_SELECTED_TASKS = BASE_CONTENT_URI.buildUpon().appendPath(SELECTED_TASKS_TABLE).build();
        public static final String TABLE_NAME = "selected_tasks_table";
        public static final String SELECTED_TASK = "task";
        public static final String TIME_STAMP = "time_stamp";
    }


}
