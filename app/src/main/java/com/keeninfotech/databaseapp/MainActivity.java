package com.keeninfotech.databaseapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        EditText editTextContactId,editTextContactName,editTextEmail;
        ListView listView;
        List<Contact> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        editTextContactId=findViewById(R.id.editTextContactId);
        editTextContactName=findViewById(R.id.editTextContactName);
        editTextEmail=findViewById(R.id.editTextEmail);

      bindListView();


    }

    public void bindListView()
    {
        contactList=new ArrayList<>();
        DBAdapter db=new DBAdapter(this);

        db.open();

        Cursor cursor=db.getAllContacts();
        if(cursor.moveToFirst())
        {
            do {

                Contact contact=new Contact();
                contact.setId(cursor.getInt(0));
                contact.setContactName(cursor.getString(1));
                contact.setEmail(cursor.getString(2));

                contactList.add(contact);

            }while(cursor.moveToNext());
        }
        db.close();

        ContactListAdapter adapter=new ContactListAdapter(this,R.layout.contactrow,contactList);

        listView.setAdapter(adapter);

    }
    public void onClickSave(View view)
    {


        String name=editTextContactName.getText().toString();
        String email=editTextEmail.getText().toString();

        DBAdapter db=new DBAdapter(this);
        db.open();
         db.insertContact(name,email);
        db.close();

        DisplayToastMessage("Contact Created Successfully");
        ResetAllViews();

        bindListView();
    }

    public void onClickSearch(View v)
    {
        int id=Integer.parseInt(editTextContactId.getText().toString());
        DBAdapter db=new DBAdapter(this);
        db.open();

        Cursor cursor=db.getContact(id);
        if(cursor.moveToFirst())
        {
            editTextContactName.setText(cursor.getString(1));
            editTextEmail.setText(cursor.getString(2));

        }else {
            DisplayToastMessage("Contact Not Found");
            ResetAllViews();
        }


        db.close();
    }
    public void onClickGetAll(View v)
    {
        DBAdapter db=new DBAdapter(this);

        db.open();

        Cursor cursor=db.getAllContacts();
        if(cursor.moveToFirst())
        {
            do {

                String contact="Id :"+cursor.getString(0)+" Name:"+cursor.getString(1)+" Email :"+cursor.getString(2);

                DisplayToastMessage(contact);
            }while(cursor.moveToNext());
        }
        db.close();



    }

    public void onClickDelete(View v)
    {
        int id=Integer.parseInt(editTextContactId.getText().toString());
        DBAdapter db=new DBAdapter(this);
        db.open();

        boolean status=db.deleteContact(id);

        if(status)
        {
            DisplayToastMessage("Contact Deleted Successfully");
        }else {
            DisplayToastMessage("Contact Not Found");

        }
        ResetAllViews();

        db.close();
        bindListView();
    }

    public void onClickUpdate(View v)
    {
        int id=Integer.parseInt(editTextContactId.getText().toString());

        String name=editTextContactName.getText().toString();
        String email=editTextEmail.getText().toString();

        DBAdapter db=new DBAdapter(this);
        db.open();

        boolean status=db.updateContact(id,name,email);

        if(status)
        {
            DisplayToastMessage("Contact Updated Successfully");
        }else {
            DisplayToastMessage("Contact Not Found");

        }
        ResetAllViews();

        db.close();

        bindListView();
    }
    public void DisplayToastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void ResetAllViews()
    {
        editTextContactId.setText("");
        editTextContactName.setText("");
        editTextEmail.setText("");
    }


}
