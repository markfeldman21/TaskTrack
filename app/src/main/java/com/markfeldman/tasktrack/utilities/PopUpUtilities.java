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
import java.util.ArrayList;
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

    public static void listTodaysTasks(final Context context, Date dateClicked){
        final ArrayList<String> boxesChecked = new ArrayList<>();
        String[] projection = {DatabaseContract.TasksContract._ID,DatabaseContract.TasksContract.TASK};
        final Uri uri = DatabaseContract.TasksContract.CONTENT_URI_TASKS;
        final Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
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
                    if (cursor!=null){
                        cursor.moveToPosition(which);
                        String selected = cursor.getString(cursor.getColumnIndex(DatabaseContract.SelectedTasks.SELECTED_TASK));
                        if (isChecked){
                            boxesChecked.add(selected);
                        }else {
                            boxesChecked.remove(selected);
                        }
                    }
                }
            }).setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentValues[] cvArr;
                    Uri checkedTasks = DatabaseContract.SelectedTasks.CONTENT_URI_SELECTED_TASKS;
                    cvArr = addContentValues(boxesChecked);
                    context.getContentResolver().bulkInsert(checkedTasks,cvArr);
                    boxesChecked.clear();

                }
            }).setNegativeButton("TODAY'S COMPLETED", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String[] projection1 = {DatabaseContract.SelectedTasks._ID,DatabaseContract.SelectedTasks.SELECTED_TASK};
                    final Uri uri1 = DatabaseContract.SelectedTasks.CONTENT_URI_SELECTED_TASKS;
                    Cursor cursor1 = context.getContentResolver().query(uri1,projection1,null,null,null);
                    if(cursor1!=null){
                        final CharSequence[] tasks = cursorToChar(cursor1);
                        AlertDialog.Builder alertCompleted = new AlertDialog.Builder(context);
                        alertCompleted.setTitle("Tasks Completed");
                        alertCompleted.setCancelable(true);

                        alertCompleted.setItems(tasks, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });
                        alertCompleted.create();
                        alertCompleted.show();
                        cursor1.close();
                    }

                }
            });
            alert.create();
            alert.show();

        }

    }

    private static ContentValues[] addContentValues(ArrayList<String> arrayList){
        ContentValues[] cvArray = new ContentValues[arrayList.size()];

        for (int i = 0; i<arrayList.size();i++){
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.SelectedTasks.SELECTED_TASK,arrayList.get(i));
            cvArray[i] = cv;
        }
        return cvArray;

    }

    private static CharSequence[] cursorToChar(Cursor cursor1){
        ArrayList<String>arrayList = new ArrayList<String>();
        for(cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()) {
            arrayList.add(cursor1.getString(cursor1.getColumnIndex(DatabaseContract.SelectedTasks.SELECTED_TASK)));
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }
}
