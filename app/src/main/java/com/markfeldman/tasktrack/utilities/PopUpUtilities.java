package com.markfeldman.tasktrack.utilities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.database.DatabaseContract;
import com.markfeldman.tasktrack.database.TaskTrackerDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PopUpUtilities {

    public static void addTaskPopUpWindow(final Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.add_task_pop_up, null);
        final EditText popUpGetTask = (EditText)view.findViewById(R.id.addTask);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setTitle("ADD TASK");
        alert.setCancelable(true);

        alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v("TAG","IN POP UP TASKS");
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.TasksContract.TASK,popUpGetTask.getText().toString());
                Uri uri = DatabaseContract.TasksContract.CONTENT_URI_TASKS;
                context.getContentResolver().insert(uri,contentValues);
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create();
        alert.show();
    }

    public static void addContactsPopUpWindow(final Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.add_contacts_pop_up, null);
        final EditText popUpName = (EditText)view.findViewById(R.id.addName);
        final EditText popUpNumber = (EditText)view.findViewById(R.id.addNumber);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setTitle("ADD CONTACT");
        alert.setCancelable(true);

        alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.ContactsContract.CONTACT_NAME,popUpName.getText().toString());
                contentValues.put(DatabaseContract.ContactsContract.CONTACT_NUMBER,popUpNumber.getText().toString());
                Uri uri = DatabaseContract.ContactsContract.CONTENT_URI_CONTACTS;
                context.getContentResolver().insert(uri,contentValues);
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create();
        alert.show();
    }

    public static void listTodaysTasks(Context context, Date dateClicked){
        String[] projection = {DatabaseContract.TasksContract._ID,DatabaseContract.TasksContract.TASK};
        Uri uri = DatabaseContract.TasksContract.CONTENT_URI_TASKS;
        Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);
        cursor.moveToFirst();
        Date date = new Date();
        long time = date.getTime();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy", Locale.US);
        String dateClickedText = formatDate.format(dateClicked);
        String dateCurrentText = formatDate.format(time);

        if(dateClickedText.equals(dateCurrentText)){
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Tasks");
            alert.setCancelable(true);

            alert.setMultiChoiceItems(cursor,DatabaseContract.TasksContract._ID,DatabaseContract.TasksContract.TASK, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked){
                        //add to new item Checked Database
                        //put time
                    }

                }
            });
            alert.create();
            alert.show();
        }


    }

}
