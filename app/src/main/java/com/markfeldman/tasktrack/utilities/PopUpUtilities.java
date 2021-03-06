package com.markfeldman.tasktrack.utilities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.database.DatabaseContract;
import com.markfeldman.tasktrack.database.TaskTrackerDatabase;

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
                Log.v("TAG","URI ==== " + uri);
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

}
