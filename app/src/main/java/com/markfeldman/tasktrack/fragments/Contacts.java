package com.markfeldman.tasktrack.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.utilities.PopUpUtilities;
import com.markfeldman.tasktrack.utilities.RecyclerViewContactsAdapter;
import com.markfeldman.tasktrack.utilities.RecyclerViewTasksAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contacts extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewContactsAdapter recyclerViewContactsAdapter;

    public Contacts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewContacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerViewContactsAdapter = new RecyclerViewContactsAdapter();
        recyclerView.setAdapter(recyclerViewContactsAdapter);

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
