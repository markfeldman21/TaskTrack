package com.markfeldman.tasktrack.utilities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.database.DatabaseContract;
import java.util.ArrayList;
import java.util.Date;
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

    public static void listTodaysTasks(final Context context, final Date dateClicked){
        final ArrayList<String> boxesChecked = new ArrayList<>();
        String[] projection = {DatabaseContract.TasksContract._ID,DatabaseContract.TasksContract.TASK};
        final Uri uri = DatabaseContract.TasksContract.CONTENT_URI_TASKS;
        final Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
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

                if (SharedPreferencesUtility.areNotificationsEnabled(context)){
                    sendMessage(context,boxesChecked);
                    Toast.makeText(context,"SAVED! TEXT NOTIFICATIONS ON",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"SAVED! TEXT NOTIFICATIONS OFF",Toast.LENGTH_LONG).show();
                }
                boxesChecked.clear();

            }
        }).setNeutralButton("TODAY'S COMPLETED", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor cursor1 = getCursorForDateQuery(context,dateClicked);
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

    public static void listOtherDaysTasks(final Context context, Date dateClicked){
        Cursor cursorForDate = getCursorForDateQuery(context,dateClicked);
        if(cursorForDate!=null) {
            cursorForDate.moveToFirst();
            final CharSequence[] tasks = cursorToChar(cursorForDate);
            AlertDialog.Builder alertCompleted = new AlertDialog.Builder(context);
            alertCompleted.setTitle("Tasks Completed");
            alertCompleted.setCancelable(true);

            alertCompleted.setItems(tasks, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {


                }

            });
            alertCompleted.create();
            alertCompleted.show();
            cursorForDate.close();

        }


    }

    private static Cursor getCursorForDateQuery(Context context,Date dateClicked){
        String[] projection1 = {DatabaseContract.SelectedTasks._ID,DatabaseContract.SelectedTasks.SELECTED_TASK,
                DatabaseContract.SelectedTasks.TIME_STAMP, DatabaseContract.SelectedTasks.DATE_STAMP};
        String searchBy = DateUtility.formatToDDMMYY(dateClicked);
        Uri authority = DatabaseContract.SelectedTasks.CONTENT_URI_SELECTED_TASKS;
        authority = authority.buildUpon().appendPath(searchBy).build();
        String selection = DatabaseContract.SelectedTasks.DATE_STAMP + " = ? ";
        String[] selectionArgs = {searchBy};
        return context.getContentResolver().query(authority,projection1,selection,selectionArgs,null);
    }

    private static ContentValues[] addContentValues(ArrayList<String> arrayList){
        ContentValues[] cvArray = new ContentValues[arrayList.size()];
        String currentTime = DateUtility.getCurrentTime();
        String date = DateUtility.getCurrentDate();

        for (int i = 0; i<arrayList.size();i++){
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.SelectedTasks.SELECTED_TASK,arrayList.get(i));
            cv.put(DatabaseContract.SelectedTasks.TIME_STAMP,currentTime);
            cv.put(DatabaseContract.SelectedTasks.DATE_STAMP,date);
            cvArray[i] = cv;
        }
        return cvArray;

    }

    private static CharSequence[] cursorToChar(Cursor cursor1){
        ArrayList<String>arrayList = new ArrayList<String>();
        for(cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()) {
            String task = cursor1.getString(cursor1.getColumnIndex(DatabaseContract.SelectedTasks.SELECTED_TASK));
            String time = cursor1.getString(cursor1.getColumnIndex(DatabaseContract.SelectedTasks.TIME_STAMP));
            arrayList.add(task + " (" + time + ")");
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static void sendMessage(Context context,ArrayList<String> arrayList){
        StringBuilder stringBuilder = new StringBuilder();
        SmsManager smsM = SmsManager.getDefault();
        for (int i = 0;i<arrayList.size(); i++){
            stringBuilder.append(i + 1 + ". " +arrayList.get(i) +"\n");
        }
        Uri authority = DatabaseContract.ContactsContract.CONTENT_URI_CONTACTS;
        Cursor contacts = context.getContentResolver().query(authority,null,null,null,null);


        for (contacts.moveToFirst(); !contacts.isAfterLast(); contacts.moveToNext()){
            String num = contacts.getString(contacts.getColumnIndex(DatabaseContract.ContactsContract.CONTACT_NUMBER));
            smsM.sendTextMessage(num, null, "Hey, here is all I've done so far: \n" + stringBuilder + " at " + DateUtility.getCurrentTime(), null, null);
            Log.v("TAG", "NUMBER ====== " + num + "\n");
        }

        if(contacts!=null){
            contacts.close();
        }
    }


    /*

    public static void addFakaData(Context context){
        ContentValues[] cvArr = new ContentValues[4];

        ContentValues cv1 = new ContentValues();
        cv1.put(DatabaseContract.SelectedTasks.SELECTED_TASK, "SHIT");
        cv1.put(DatabaseContract.SelectedTasks.TIME_STAMP, "1 PM");
        cv1.put(DatabaseContract.SelectedTasks.DATE_STAMP, "28/03/17");
        cvArr[0] = cv1;

        ContentValues cv2 = new ContentValues();
        cv2.put(DatabaseContract.SelectedTasks.SELECTED_TASK, "SHAAAT");
        cv2.put(DatabaseContract.SelectedTasks.TIME_STAMP, "2 PM");
        cv2.put(DatabaseContract.SelectedTasks.DATE_STAMP, "28/03/17");
        cvArr[1] = cv2;

        ContentValues cv3 = new ContentValues();
        cv3.put(DatabaseContract.SelectedTasks.SELECTED_TASK, "poop");
        cv3.put(DatabaseContract.SelectedTasks.TIME_STAMP, "2 PM");
        cv3.put(DatabaseContract.SelectedTasks.DATE_STAMP, "27/03/17");
        cvArr[2] = cv3;

        ContentValues cv4 = new ContentValues();
        cv4.put(DatabaseContract.SelectedTasks.SELECTED_TASK, "DANCE");
        cv4.put(DatabaseContract.SelectedTasks.TIME_STAMP, "2 PM");
        cv4.put(DatabaseContract.SelectedTasks.DATE_STAMP, "28/03/17");
        cvArr[3] = cv4;

        Uri checkedTasks = DatabaseContract.SelectedTasks.CONTENT_URI_SELECTED_TASKS;
        context.getContentResolver().bulkInsert(checkedTasks,cvArr);
    }
    */
}
