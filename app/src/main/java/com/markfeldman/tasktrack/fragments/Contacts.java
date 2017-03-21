package com.markfeldman.tasktrack.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.database.DatabaseContract;
import com.markfeldman.tasktrack.utilities.PopUpUtilities;
import com.markfeldman.tasktrack.utilities.RecyclerViewContactsAdapter;
import com.markfeldman.tasktrack.utilities.RecyclerViewTasksAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contacts extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private RecyclerViewContactsAdapter recyclerViewContactsAdapter;
    private final int SEARCH_CONTACTS_LOADER_ID = 24;
    private String[] projection = {DatabaseContract.ContactsContract._ID,DatabaseContract.ContactsContract.CONTACT_NAME,
    DatabaseContract.ContactsContract.CONTACT_NUMBER};

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

        getActivity().getSupportLoaderManager().initLoader(SEARCH_CONTACTS_LOADER_ID,null,this);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fabContacts);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpUtilities.addContactsPopUpWindow(getActivity());
            }
        });

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri contactsQueryUri = DatabaseContract.ContactsContract.CONTENT_URI_CONTACTS;
        return new CursorLoader(getActivity(),contactsQueryUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0){
            recyclerViewContactsAdapter.swap(data);
        }else if (data.getCount() == 0) {
            Toast.makeText(getActivity(),"Nothing In there", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recyclerViewContactsAdapter.swap(null);
    }
}
