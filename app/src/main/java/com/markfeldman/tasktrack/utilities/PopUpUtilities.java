package com.markfeldman.tasktrack.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.markfeldman.tasktrack.R;

/**
 * Created by markfeldman on 3/14/17.
 */

public class PopUpUtilities {

    public static void addTaskPopUpWindow(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.add_task_pop_up, null);
        final EditText popUp = (EditText)view.findViewById(R.id.addName);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setTitle("ADD TASK");
        alert.setCancelable(true);

        alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

    public static void addContactsPopUpWindow(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.add_contacts_pop_up, null);
        final EditText popUp = (EditText)view.findViewById(R.id.addName);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setTitle("ADD CONTACT");
        alert.setCancelable(true);

        alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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