package com.keeninfotech.databaseapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    //the list values in the List of type hero
    List<Contact> contactsList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    public ContactListAdapter(@NonNull Context context, int resource, @NonNull List<Contact> contactsList) {
        super(context, resource, contactsList);

        this.contactsList=contactsList;
        this.context=context;
        this.resource=resource;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        TextView textViewContactName = view.findViewById(R.id.textViewContactName);
        TextView textViewEmail = view.findViewById(R.id.textViewEmail);

        //getting the hero of the specified position
        Contact contact = contactsList.get(position);

        textViewContactName.setText("Contact Name :"+contact.getContactName());
        textViewEmail.setText("Email :"+ contact.getEmail());


        //finally returning the view
        return view;
    }
}
