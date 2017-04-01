package com.markfeldman.tasktrack.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskTrackerDatabase {
    private final String DATABASE_NAME = "task_tracker_database";
    private final int DATABASE_VERSION = 1;
    private SQLiteDatabase mDb;
    private TaskTrackerDatabaseHelper taskTrackerDatabaseHelper;
    private Context context;

    public TaskTrackerDatabase(Context context){
        this.context = context;
        taskTrackerDatabaseHelper = new TaskTrackerDatabaseHelper(context);
    }

    public TaskTrackerDatabase openReadable(){
        mDb =taskTrackerDatabaseHelper.getReadableDatabase();
        return this;
    }

    public TaskTrackerDatabase openWritable(){
        mDb =taskTrackerDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDb.close();
    }

    public void beginTransaction(){
        mDb.beginTransaction();
    }

    public void transactionSuccesful(){
        mDb.setTransactionSuccessful();
    }

    public void endTransaction(){
        mDb.endTransaction();
    }


    public Cursor getAllTaskRows(){
        return mDb.query(DatabaseContract.TasksContract.TABLE_NAME,null,null,null,null,null,DatabaseContract.TasksContract.TASK + " ASC");
    }

    public Cursor getAllContactRows(){
        return mDb.query(DatabaseContract.ContactsContract.TABLE_NAME,null,null,null,null,null,DatabaseContract.ContactsContract.CONTACT_NAME + " ASC");
    }

    public Cursor getAllSelectedTaskRows(){
        return mDb.query(DatabaseContract.SelectedTasks.TABLE_NAME,null,null,null,null,null,null);
    }

    public Cursor getSpecificTaskRow(String[] projection,String[] rowID){
        String selection = DatabaseContract.TasksContract._ID+ "=?";
        Cursor c = mDb.query(DatabaseContract.TasksContract.TABLE_NAME,projection,selection,rowID,null,null,null);
        if (c!=null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getPreviouslySelectedTasks(String[] projection,String selection, String[] selectionArgs){
        Cursor c =  mDb.query(true, DatabaseContract.SelectedTasks.TABLE_NAME, projection, selection,
                selectionArgs,null,null,null,null);
        Log.v("TAG", "CURSOR COUNT ==== " + c.getCount());

        if (c!=null){
            c.moveToFirst();
        }
        return c;
    }


    public void deleteTaskTable(){
        mDb.delete(DatabaseContract.TasksContract.TABLE_NAME,null,null);
    }

    public void deleteContactTable(){
        mDb.delete(DatabaseContract.ContactsContract.TABLE_NAME,null,null);
    }

    public void deleteAllTaskRows(){
        Cursor c = getAllTaskRows();
        int rowID = c.getColumnIndexOrThrow(DatabaseContract.TasksContract._ID);
        String idToDelete = Integer.toString(rowID);
        String[]args = {idToDelete};
        if (c.moveToFirst()){
            do{
                mDb.delete(DatabaseContract.TasksContract.TABLE_NAME," _id=?",args);
            }while(c.moveToNext());
        }
    }

    public void deleteAllContactRows(){
        Cursor c = getAllContactRows();
        int rowID = c.getColumnIndexOrThrow(DatabaseContract.ContactsContract._ID);
        String idToDelete = Integer.toString(rowID);
        String[]args = {idToDelete};
        if (c.moveToFirst()){
            do{
                mDb.delete(DatabaseContract.ContactsContract.TABLE_NAME," _id=?",args);
            }while(c.moveToNext());
        }
    }

    public boolean deleteSelectedTaskRow(long rowID){
        String where = DatabaseContract.SelectedTasks._ID + "=" + rowID;
        return mDb.delete(DatabaseContract.SelectedTasks.TABLE_NAME,where,null) !=0;
    }


    //SQLite return statement returns long containing id of inserted Row
    public long insertRowToTasks(ContentValues cv){
        return mDb.insert(DatabaseContract.TasksContract.TABLE_NAME, null,cv);
    }

    public long insertRowToContacts(ContentValues cv){
        return mDb.insert(DatabaseContract.ContactsContract.TABLE_NAME, null,cv);
    }

    public long insertRowToSelectedTasks(ContentValues cv){
        return mDb.insert(DatabaseContract.SelectedTasks.TABLE_NAME, null,cv);
    }

    public boolean deleteTaskRow(String where){
        return mDb.delete(DatabaseContract.TasksContract.TABLE_NAME,where,null) !=0;
    }

    public boolean deleteContactsRow(String where){
        return mDb.delete(DatabaseContract.ContactsContract.TABLE_NAME,where,null) !=0;
    }


    private class TaskTrackerDatabaseHelper extends SQLiteOpenHelper{
        private final static String CREATE_TASKS_TABLE = "CREATE TABLE " + DatabaseContract.TasksContract.TABLE_NAME+
                " ("+ DatabaseContract.TasksContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.TasksContract.TASK + " TEXT NOT NULL " +
                ");";

        private final static String CREATE_SELECTED_TASKS_TABLE = "CREATE TABLE " + DatabaseContract.SelectedTasks.TABLE_NAME+
                " ("+ DatabaseContract.TasksContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.SelectedTasks.SELECTED_TASK + " TEXT NOT NULL, " +
                DatabaseContract.SelectedTasks.DATE_STAMP + " TEXT NOT NULL, " +
                DatabaseContract.SelectedTasks.TIME_STAMP + " TEXT NOT NULL " +
                ");";

        private final static String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DatabaseContract.ContactsContract.TABLE_NAME+
                " ("+ DatabaseContract.ContactsContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.ContactsContract.CONTACT_NAME + " TEXT NOT NULL, " +
                DatabaseContract.ContactsContract.CONTACT_NUMBER + " TEXT NOT NULL " +
                ");";

        public TaskTrackerDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_CONTACTS_TABLE);
            db.execSQL(CREATE_TASKS_TABLE);
            db.execSQL(CREATE_SELECTED_TASKS_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TasksContract.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ContactsContract.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TasksContract.TABLE_NAME);
            onCreate(db);

        }
    }
}
