package com.practice.bunktracker;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.practice.bunktracker.R;

import java.util.ArrayList;

import java.util.Map;

public class addsubjects extends AppCompatActivity {



    TextInputEditText  subject,percentage,t,b;
    Button add;
    String s;
    int p,t1,b1;
    Button cnlbtn;
    Double percent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsubjects);
        add = findViewById(R.id.addbtn);
        cnlbtn = findViewById(R.id.cnlbtn);
        subject = findViewById(R.id.sub);
        percentage = findViewById(R.id.per);
        t = findViewById(R.id.t);
        b = findViewById(R.id.b);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (subject.getText().toString().equals(""))
                {
                    subject.setError("Fields cannot be left blank.");
                }
                else if(percentage.getText().toString().equals(""))
                {
                    percentage.setError("Percentage cannot be blank");
                }
                else if(b.getText().toString().equals(""))
                {
                    b.setError("Bunk Lecturers cannot be blank");
                }
                else if(t.getText().toString().equals(""))
                {
                    t.setError("Total Lecturers cannot be blank");
                }
                else
                {
                    s = subject.getText().toString();
                    p=Integer.parseInt(percentage.getText().toString());
                    t1 = Integer.parseInt(t.getText().toString());
                    b1 = Integer.parseInt(b.getText().toString());
                    percent = Double.valueOf((((double) (t1 - b1)) / Double.valueOf((double) t1).doubleValue()) * 100.0d);
                    percent = Double.valueOf(((double) Math.round(percent.doubleValue() * 100.0d)) / 100.0d);

                    if (p > 100 && b1 > t1) {
                        android.support.v7.app.AlertDialog.Builder mybuilder = new android.support.v7.app.AlertDialog.Builder(addsubjects.this);
                        mybuilder.setTitle("Input not correct");
                        mybuilder.setMessage("Minimum percentage cannot be greater than 100 and Bunked classes cannot be more than total classes");
                        mybuilder.setCancelable(false);
                        mybuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        android.support.v7.app.AlertDialog mydialog = mybuilder.create();
                        mydialog.show();
                    } else if (p > 100) {
                        android.support.v7.app.AlertDialog.Builder mybuilder = new android.support.v7.app.AlertDialog.Builder(addsubjects.this);
                        mybuilder.setTitle("Input not correct");
                        mybuilder.setMessage("Minimum percentage cannot be greater than 100");
                        mybuilder.setCancelable(false);
                        mybuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        android.support.v7.app.AlertDialog mydialog = mybuilder.create();
                        mydialog.show();
                    } else if (b1 > t1) {
                        android.support.v7.app.AlertDialog.Builder mybuilder = new android.support.v7.app.AlertDialog.Builder(addsubjects.this);
                        mybuilder.setTitle("Input not correct");
                        mybuilder.setMessage("Bunked classes cannot be more than total classes");
                        mybuilder.setCancelable(false);
                        mybuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        android.support.v7.app.AlertDialog mydialog = mybuilder.create();
                        mydialog.show();
                    } else {
                        addsubjects.this.insertSubject();
                        addsubjects.this.setResult(-1, null);
                        addsubjects.this.finish();

                    }

                }
//                android.support.v7.app.AlertDialog.Builder mybuilder = new android.support.v7.app.AlertDialog.Builder(addsubjects.this);
////                    mybuilder.setTitle("Input not correct");
////                    mybuilder.setMessage("Fields cannot be left blank");
////                    mybuilder.setCancelable(false);
////                    mybuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            dialog.cancel();
////                        }
////                    });
////                    android.support.v7.app.AlertDialog mydialog = mybuilder.create();
////                    mydialog.show();




//                if (s.equals("")||  || .getText().toString().equals(""))
//                {
//                    subject.setError("Fields cannot be left blank.");
//                }


            }
        });

        cnlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addsubjects.this.finish();
            }
        });



    }

    public void insertSubject() {
        ContentValues myvalues = new ContentValues();
        myvalues.put("name", s);
        myvalues.put("minPercent", Integer.valueOf(p));
        myvalues.put("bunkedClasses", Integer.valueOf(b1));
        myvalues.put("totalClasses", Integer.valueOf(t1));
        myvalues.put("percent", this.percent);
        Log.i("bunktracker", "before insertion" + myvalues.get("percent"));
        menuActivity.mydb.getWritableDatabase().insert("SubjectData", null, myvalues);
        myvalues.clear();

    }

    protected static void clearAll() {
        Log.i("bunktracker", "rows deleted = " + menuActivity.mydb.getWritableDatabase().delete("SubjectData", null, null));
    }

    protected static Cursor readSubjects() {
        return menuActivity.mydb.getWritableDatabase().query("SubjectData", Mydatabasehelper.columns, null, new String[0], null, null, null);
    }

    protected static Cursor readSubjectsNameAndPercentage() {
        return menuActivity.mydb.getWritableDatabase().query("SubjectData", Mydatabasehelper.columnSubjectNameandPercentage, null, new String[0], null, null, null);

    }

}
