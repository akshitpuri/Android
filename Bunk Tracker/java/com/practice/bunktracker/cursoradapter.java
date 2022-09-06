package com.practice.bunktracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class cursoradapter extends ResourceCursorAdapter {

    Context cr1;
    char firstLetter;
    TextView firstLetterView;
    String subjectName;


    cursoradapter(Context c, int layout, Cursor cursor, int flags) {
        super(c, layout, cursor, flags);
        this.cr1 = c;
    }

    @SuppressLint("WrongConstant")
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.list, parent, false);
    }
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.subject)).setText(cursor.getString(cursor.getColumnIndex("name")));
        this.subjectName = cursor.getString(cursor.getColumnIndex("name"));
        this.firstLetter = this.subjectName.charAt(0);
        this.firstLetterView = (TextView) view.findViewById(R.id.firstLetter);
        this.firstLetterView.setText(this.firstLetter + "");
        ((TextView) view.findViewById(R.id._id)).setText(cursor.getString(cursor.getColumnIndex("_id")));
        TextView percentView = (TextView) view.findViewById(R.id.percent);
        percentView.setText(cursor.getString(cursor.getColumnIndex("percent")) + "%");
        if (Double.valueOf(cursor.getString(cursor.getColumnIndex("percent"))).doubleValue() < ((double) Integer.parseInt(cursor.getString(cursor.getColumnIndex("minPercent"))))) {
            percentView.setTextColor(this.cr1.getResources().getColor(R.color.red));
        } else {
            percentView.setTextColor(this.cr1.getResources().getColor(R.color.green));
        }
    }

}
