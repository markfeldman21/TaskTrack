package com.markfeldman.tasktrack.data;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.markfeldman.tasktrack.database.DatabaseContract;
import com.markfeldman.tasktrack.database.TaskTrackerDatabase;

public class ContentProvider extends android.content.ContentProvider {
    public final static int CODE_TASK = 100;
    public final static int CODE_TASK_ID = 101;
    public final static int CODE_CONTACTS = 200;
    public final static  int CODE_CONTACTS_ID = 201;

    private static final UriMatcher sUriMatcher = buildURIMatcher();
    private TaskTrackerDatabase taskTrackerDatabase;

    public static UriMatcher buildURIMatcher(){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority,DatabaseContract.TASK_TABLE,CODE_TASK);
        uriMatcher.addURI(authority,DatabaseContract.TASK_TABLE + "/#",CODE_TASK_ID);
        uriMatcher.addURI(authority,DatabaseContract.CONTACTS_TABLE,CODE_CONTACTS);
        uriMatcher.addURI(authority, DatabaseContract.CONTACTS_TABLE + "/#",CODE_CONTACTS_ID);

        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        taskTrackerDatabase = new TaskTrackerDatabase(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        taskTrackerDatabase.openReadable();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch(match){
            case CODE_TASK:{
                cursor = taskTrackerDatabase.getAllTaskRows();
                break;
            }
            case CODE_CONTACTS:{
                cursor = taskTrackerDatabase.getAllContactRows();
                break;
            }
            default:
                throw new UnsupportedOperationException("UKNOWN URI:" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = sUriMatcher.match(uri);
        taskTrackerDatabase.openWritable();
        long rowsInserted=0;
            switch (match){
                case CODE_TASK:{
                    Log.v("TAG","INSERT PROVIDER TASKS");
                    taskTrackerDatabase.beginTransaction();
                    rowsInserted = taskTrackerDatabase.insertRowToTasks(values);
                    taskTrackerDatabase.transactionSuccesful();
                    taskTrackerDatabase.endTransaction();
                    break;
                }
                case CODE_CONTACTS:{
                    Log.v("TAG","INSERT PROVIDER CONTACTS");
                    taskTrackerDatabase.beginTransaction();
                    rowsInserted = taskTrackerDatabase.insertRowToContacts(values);
                    taskTrackerDatabase.transactionSuccesful();
                    taskTrackerDatabase.endTransaction();
                    break;
                }

        }

        if(rowsInserted>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        switch (match){
            case CODE_TASK:{
                taskTrackerDatabase.deleteTaskRow(selection);
                break;
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
