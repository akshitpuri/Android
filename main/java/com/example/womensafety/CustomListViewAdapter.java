package com.example.womensafety;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<Contact> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<Contact> contacts) {
        super(context, resourceId, contacts);
        this.context = context;
    }

    /*private view holder class*/
    public class ViewHolder {
        ImageView imageView;
        TextView contactNumber;
        TextView contactName;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Contact contact = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.mylistview, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.contactName = (TextView) convertView.findViewById(R.id.myname);
            holder.contactNumber = (TextView) convertView.findViewById(R.id.mynumber);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.contactName.setText(contact.getName());
        holder.contactNumber.setText(contact.getPhoneNumber());
        //    holder.imageView.setImageResource(Integer.parseInt(Contact.getImage()));
        holder.imageView.setImageResource(R.drawable.ic_account_circle_black_24dp);

        return convertView;
    }
}