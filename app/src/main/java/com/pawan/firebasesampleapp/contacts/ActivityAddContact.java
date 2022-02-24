package com.pawan.firebasesampleapp.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pawan.firebasesampleapp.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityAddContact extends AppCompatActivity {

    EditText et_f_name, et_l_name, et_email, et_phone;
    Button btn_save;

    //our database reference object
    DatabaseReference databaseContacts;

    ListView listViewContacts;

    //a list to store all the contacts from firebase database`
    List<AddContacts> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        //getting the reference of contacts node
        databaseContacts = FirebaseDatabase.getInstance().getReference("contacts");
        et_f_name = (EditText) findViewById(R.id.editTextFName);
        et_l_name = (EditText) findViewById(R.id.editTextLName);
        et_email = (EditText) findViewById(R.id.editTextEmail);
        et_phone = (EditText) findViewById(R.id.editTextPhone);
        btn_save = (Button) findViewById(R.id.buttonAddContact);
        listViewContacts = (ListView) findViewById(R.id.listViewContacts);
        //list to store contacts
        contactsList = new ArrayList<>();


        //adding an onclicklistener to button
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addContacts()
                //the method is defined below
                //this method is actually performing the write operation
                addContacts();
            }
        });


        listViewContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
            AddContacts cont = contactsList.get(i);
            showUpdateDeleteDialog(cont);
            return true;
        });

    }

    private void showUpdateDeleteDialog(AddContacts cont) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.contact_update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextFName = (EditText) dialogView.findViewById(R.id.editTextFName);
        final EditText editTextLName = (EditText) dialogView.findViewById(R.id.editTextLName);
        final EditText etEmail = (EditText) dialogView.findViewById(R.id.Email);
        final EditText etPhone = (EditText) dialogView.findViewById(R.id.Phone);

        editTextFName.setText(cont.getFname());
        editTextLName.setText(cont.getLname());
        etEmail.setText(cont.getEmail());
        etPhone.setText(cont.getPhone());

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle("Update or Delete");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = editTextFName.getText().toString().trim();
                String lname = editTextLName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                    updateContacts(cont.getKey(),fname, lname, email,phone);
                    b.dismiss();
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteContact(cont.getKey());
                b.dismiss();

            }
        });
    }

    /*
     * This method is saving a new contacts to the
     * Firebase Realtime Database
     * */
    private void addContacts() {
        //getting the values to save
        String f_name = et_f_name.getText().toString().trim();
        String l_name = et_l_name.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(f_name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Contacts
            String id = databaseContacts.push().getKey();

            //creating an Contacts Object
            AddContacts contactst = new AddContacts(id, f_name, l_name, email, phone);

            //Saving the Contacts
            databaseContacts.child(id).setValue(contactst);

            //setting edittext to blank again
            et_f_name.setText("");
            et_l_name.setText("");
            et_email.setText("");
            et_phone.setText("");

            //displaying a success toast
            Toast.makeText(this, "Contact added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }


    private boolean deleteContact(String id) {



        //getting the specified contacts reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("contacts").child(id);
        //removing contacts
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Contacts Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean updateContacts(String key, String fname, String lname, String email, String phone) {
        //getting the specified contacts reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("contacts").child(key);

        //updating contacts
        AddContacts contacts = new AddContacts(key, fname, lname, email, phone);
        dR.setValue(contacts);
        Toast.makeText(getApplicationContext(), "Contact Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseContacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous contacts list
                contactsList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e("SFWWQWF", postSnapshot.getKey());
                    Log.e("SFWWQWF", postSnapshot.getValue().toString());
                    //getting cont
                    AddContacts cont = postSnapshot.getValue(AddContacts.class);
                    //adding cont to the list
                    contactsList.add(cont);
                }

                //creating adapter
                AdapterContactList contactListtAdapter = new AdapterContactList(ActivityAddContact.this, contactsList);
                //attaching adapter to the listview
                listViewContacts.setAdapter(contactListtAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}