package com.emsi.front_contact.presentation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emsi.front_contact.R;
import com.emsi.front_contact.domaine.entities.Contact;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    private final Context context;
    private final List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }


    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.contact_layout, parent, false);
        }

        TextView textView = view.findViewById(R.id.textView);
        Contact contact = contactList.get(position);
        textView.setText(contact.getNom() + " - " + contact.getNum());
        Log.d("ContactAdapter", "Affichage du contact : " + contact.getNom());
        return view;
    }
}
