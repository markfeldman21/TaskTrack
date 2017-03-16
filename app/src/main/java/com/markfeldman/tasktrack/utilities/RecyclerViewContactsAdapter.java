package com.markfeldman.tasktrack.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.markfeldman.tasktrack.R;

public class RecyclerViewContactsAdapter extends RecyclerView.Adapter<RecyclerViewContactsAdapter.ContactsHolder> {

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
        holder.contactsName.setText("Mark");
        holder.contactsNumber.setText("617 862 83 75");
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
