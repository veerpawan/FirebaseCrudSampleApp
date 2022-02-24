package com.pawan.firebasesampleapp.contacts;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pawan.firebasesampleapp.Artist;
import com.pawan.firebasesampleapp.R;

import java.util.List;

public class AdapterContactList extends ArrayAdapter<AddContacts> {
    private Activity context;
    List<AddContacts> contacts;

    public AdapterContactList(Activity context, List<AddContacts> adapterContactLists) {
        super(context, R.layout.layout_contact_list, adapterContactLists);
        this.context = context;
        this.contacts = adapterContactLists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_contact_list, null, true);

        TextView tv_f_name = (TextView) listViewItem.findViewById(R.id.tv_f_name);
        TextView tv_l_name = (TextView) listViewItem.findViewById(R.id.tv_l_name);
        TextView tv_email = (TextView) listViewItem.findViewById(R.id.tv_email);
        TextView tv_phone = (TextView) listViewItem.findViewById(R.id.tv_phone);


        AddContacts cont = contacts.get(position);
        tv_f_name.setText(cont.getFname());
        tv_l_name.setText(cont.getLname());
        tv_email.setText(cont.getEmail());
        tv_phone.setText(cont.getPhone());

        return listViewItem;
    }
}

