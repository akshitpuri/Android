package com.example.womensafety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListCheckAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ListCheckAdapter(Context context, String[] values) {
        super(context, R.layout.listpics, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.listpics, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.label);

        textView.setText(values[position]);

        return rowView;
    }

}
