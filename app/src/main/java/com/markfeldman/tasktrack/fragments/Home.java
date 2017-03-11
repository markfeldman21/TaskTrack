package com.markfeldman.tasktrack.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.markfeldman.tasktrack.R;


public class Home extends Fragment {

    public Home() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button cal = (Button)view.findViewById(R.id.cal_button);
        Button tasks = (Button) view.findViewById(R.id.tasks_button);
        Button contacts = (Button)view.findViewById(R.id.contacts_button);

        return view;
    }
}
