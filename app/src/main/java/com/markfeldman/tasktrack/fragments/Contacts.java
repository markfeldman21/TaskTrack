package com.markfeldman.tasktrack.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.utilities.PopUpUtilities;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contacts extends Fragment {


    public Contacts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fabContacts);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpUtilities.addContactsPopUpWindow(getActivity());
            }
        });

        return view;
    }

}
