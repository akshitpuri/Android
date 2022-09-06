package com.practice.bunktracker;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class helpafriendActivity extends AppCompatActivity {

    TextInputEditText subject, percentage, t, b;
    Button cal;
    int p, t1, b1;
    Double percent;
    TextView tv,tv2;
    Double classesToAttend;
    Double classesToBunk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpafriend);


        cal = findViewById(R.id.button);

        percentage = findViewById(R.id.per);
        t = findViewById(R.id.t);
        b = findViewById(R.id.b);
        tv=findViewById(R.id.tv);
        tv2=findViewById(R.id.tv2);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p = Integer.parseInt(percentage.getText().toString());
                t1 = Integer.parseInt(t.getText().toString());
                b1 = Integer.parseInt(b.getText().toString());
                percent = Double.valueOf((((double) (t1 - b1)) / Double.valueOf((double) t1).doubleValue()) * 100.0d);
                percent = Double.valueOf(((double) Math.round(percent.doubleValue() * 100.0d)) / 100.0d);

                if (p > 100 && b1 > t1) {
                    android.support.v7.app.AlertDialog.Builder mybuilder = new android.support.v7.app.AlertDialog.Builder(helpafriendActivity.this);
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
                    android.support.v7.app.AlertDialog.Builder mybuilder = new android.support.v7.app.AlertDialog.Builder(helpafriendActivity.this);
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
                    android.support.v7.app.AlertDialog.Builder mybuilder = new android.support.v7.app.AlertDialog.Builder(helpafriendActivity.this);
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


                }

                if(b1<t1 && p<100 ) {


                    double minPerDivByHund = ((double) helpafriendActivity.this.p) / 100.0d;
                    if (helpafriendActivity.this.percent.doubleValue() < ((double) helpafriendActivity.this.p)) {
                        helpafriendActivity.this.classesToAttend = Double.valueOf((((double) helpafriendActivity.this.b1) - (((double) helpafriendActivity.this.t1) * (1.0d - minPerDivByHund))) / (1.0d - minPerDivByHund));
                        int attend = (int) Math.ceil(helpafriendActivity.this.classesToAttend.doubleValue());
                        if (attend < 0) {
                            attend = 0;
                        }
                        helpafriendActivity.this.tv.setText("Attendance is : " + percent + " %");
                        helpafriendActivity.this.tv2.setText("Need " + attend + " " + "classes for safe attendance.");

                        return;
                    }
                    else if(helpafriendActivity.this.percent.doubleValue() > ((double) helpafriendActivity.this.p)) {
                        helpafriendActivity.this.classesToBunk = Double.valueOf(((((double) helpafriendActivity.this.t1) - (((double) helpafriendActivity.this.t1) * minPerDivByHund)) - ((double) helpafriendActivity.this.b1)) / minPerDivByHund);
                        int bunk = (int) Math.floor(helpafriendActivity.this.classesToBunk.doubleValue());
                        if (bunk < 0) {
                            bunk = 0;
                        }
                        helpafriendActivity.this.tv.setText("Attendance is : " + percent + " %");
                        helpafriendActivity.this.tv2.setText("Have " + bunk + " " + "safe bunks available.");
                    }
                }









            }
        }



        );

    }
}
