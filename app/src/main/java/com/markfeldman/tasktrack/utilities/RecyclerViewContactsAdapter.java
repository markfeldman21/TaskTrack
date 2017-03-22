package com.markfeldman.tasktrack.utilities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.database.DatabaseContract;

public class RecyclerViewContactsAdapter extends RecyclerView.Adapter<RecyclerViewContactsAdapter.ContactsHolder> {
    private Cursor cursor;

    class ContactsHolder extends RecyclerView.ViewHolder{
        TextView contactsName;
        TextView contactsNumber;

        public ContactsHolder(View itemView) {
            super(itemView);
            contactsName = (TextView)itemView.findViewById(R.id.contact_name);
            contactsNumber = (TextView)itemView.findViewById(R.id.contact_number);
        }
    }

    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.individual_contact;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecyclerViewContactsAdapter.ContactsHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsHolder holder, int position) {
        cursor.moveToPosition(position);
        long id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.ContactsContract._ID));
        holder.itemView.setTag(id);
        holder.contactsName.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ContactsContract.CONTACT_NAME)));
        holder.contactsNumber.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ContactsContract.CONTACT_NUMBER)));
    }

    @Override
    public int getItemCount() {
        if (cursor==null){
            return 0;
        }
        return cursor.getCount();
    }

    public void swap (Cursor c){
        this.cursor = c;
        notifyDataSetChanged();
    }
}
