package com.markfeldman.tasktrack.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.utilities.PopUpUtilities;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tasks extends Fragment {

    //https://github.com/danilao/fragments-viewpager-example

    public Tasks() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        final FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fabTasks);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpUtilities.addTaskPopUpWindow(getActivity());
            }
        });

        return view;
    }

}
