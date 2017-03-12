package com.markfeldman.tasktrack.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.markfeldman.tasktrack.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tasks extends Fragment {
    private FloatingActionButton fab;


    public Tasks() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        return view;
    }

}
