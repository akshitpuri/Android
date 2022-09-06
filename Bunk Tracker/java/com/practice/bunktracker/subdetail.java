package com.practice.bunktracker;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class subdetail extends AppCompatActivity {

    int bunk;
    Button bunkDec;
    Button bunkInc;
    String bunkedClassesFromIntent;
    TextView bunkedView;
    Double classesToAttend;
    Double classesToBunk;
    TextView disp1;

    int edit = 0;
    int idFromIntent;
    String minimumPercentFromIntent;
    int minimumPercentInInt;
    TextView minimumView;
    Double percent;
    TextView percentView;
    String percentageFromIntent;
    ProgressBar progress;
    String subjectNameFromIntent;
    TextView subjectView;
    int tot;
    String totalClassesFromIntent;
    Button totalDec;
    Button totalInc;
    TextView totalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdetail);
        this.subjectNameFromIntent = getIntent().getStringExtra("subject name");
        this.minimumPercentFromIntent = getIntent().getStringExtra("minimum percentage");
        this.bunkedClassesFromIntent = getIntent().getStringExtra("bunked classes");
        this.totalClassesFromIntent = getIntent().getStringExtra("total classes");
        this.percentageFromIntent = getIntent().getStringExtra("attendance percentage");
        this.idFromIntent = getIntent().getIntExtra("id", -1);
        bunkDec = findViewById(R.id.bunkedClassDec);
        bunkInc = findViewById(R.id.bunkedClassInc);
        totalDec = findViewById(R.id.totalDec);
        totalInc = findViewById(R.id.totalInc);
        disp1=findViewById(R.id.displayView1);

        bunkedView=findViewById(R.id.bunked);
        percentView=findViewById(R.id.percentDisplay);
        totalView=findViewById(R.id.total);
        progress=findViewById(R.id.progressBar);
        subjectView=findViewById(R.id.subjectName);
        minimumView=findViewById(R.id.percentage);

        this.bunkedView.setText(this.bunkedClassesFromIntent);
        this.bunk = Integer.parseInt(this.bunkedView.getText().toString());
        this.totalView.setText(this.totalClassesFromIntent);
        this.tot = Integer.parseInt(this.totalView.getText().toString());
        this.minimumView.setText(this.minimumPercentFromIntent + "%");
        this.subjectView.setText(this.subjectNameFromIntent);
        this.percentView.setText(this.percentageFromIntent + "%");
        this.percent = Double.valueOf(this.percentageFromIntent);
        this.minimumPercentInInt = Integer.parseInt(this.minimumPercentFromIntent);
        calculate();
        bunkInc.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           subdetail.this.bunkDec.setEnabled(true);
                                           subdetail subjectDetails = subdetail.this;
                                           subjectDetails.bunk++;
                                           subjectDetails = subdetail.this;
                                           subjectDetails.tot++;
                                           subdetail.this.bunkedView.setText(subdetail.this.bunk + "");
                                           subdetail.this.totalView.setText(subdetail.this.tot + "");
                                           subdetail.this.calculate();
                                           if (subdetail.this.bunk != subdetail.this.tot) {
                                               subdetail.this.totalDec.setEnabled(true);
                                           }


                                       }
                                   }
        );
        totalInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subdetail subjectDetails = subdetail.this;
                subjectDetails.tot++;
                subdetail.this.totalView.setText(subdetail.this.tot + "");
                subdetail.this.totalDec.setEnabled(true);
                subdetail.this.calculate();
            }
        });
        bunkDec.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subdetail.this.bunk > 0 && subdetail.this.tot > 0) {
                    subdetail subjectDetails = subdetail.this;
                    subjectDetails.bunk--;
                    subjectDetails = subdetail.this;
                    subjectDetails.tot--;
                    if (subdetail.this.bunk == 0) {
                        subdetail.this.bunkDec.setEnabled(false);
                        subdetail.this.bunkedView.setText("0");
                    } else {
                        subdetail.this.bunkedView.setText(subdetail.this.bunk + "");
                    }
                    subdetail.this.totalView.setText(subdetail.this.tot + "");
                }
                subdetail.this.calculate();
            }
        }));
        totalDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subdetail subjectDetails = subdetail.this;
                subjectDetails.tot--;
                if (subdetail.this.tot < subdetail.this.bunk) {
                    subdetail.this.tot = subdetail.this.bunk;
                    return;
                }
                if (subdetail.this.tot >= 0) {
                    subdetail.this.totalView.setText(subdetail.this.tot + "");
                    if (subdetail.this.tot == subdetail.this.bunk) {
                        subdetail.this.totalDec.setEnabled(false);
                    }
                } else {
                    subdetail.this.tot = 0;
                    subdetail.this.totalView.setText("0");
                    subdetail.this.totalDec.setEnabled(false);
                }
                subdetail.this.calculate();
            }
        });
    }

        void calculate () {
            this.percent = Double.valueOf((((double) (this.tot - this.bunk)) / Double.valueOf((double) this.tot).doubleValue()) * 100.0d);
            this.percent = Double.valueOf(((double) Math.round(this.percent.doubleValue() * 100.0d)) / 100.0d);
            this.percentView.setText(this.percent + "%");
            int progresspercent = (int) Math.round(this.percent.doubleValue());
            for (int i = 0; i <= progresspercent; i++) {
                this.progress.setProgress(i);
            }
            double minPerDivByHund = ((double) this.minimumPercentInInt) / 100.0d;
            if (this.percent.doubleValue() < ((double) this.minimumPercentInInt)) {
                this.classesToAttend = Double.valueOf((((double) this.bunk) - (((double) this.tot) * (1.0d - minPerDivByHund))) / (1.0d - minPerDivByHund));
                int attend = (int) Math.ceil(this.classesToAttend.doubleValue());
                if (attend < 0) {
                    attend = 0;
                }
                this.disp1.setText("You need " + attend + " " + "classes for safe attendance.");

                return;
            }
            this.classesToBunk = Double.valueOf(((((double) this.tot) - (((double) this.tot) * minPerDivByHund)) - ((double) this.bunk)) / minPerDivByHund);
            int bunk = (int) Math.floor(this.classesToBunk.doubleValue());
            if (bunk < 0) {
                bunk = 0;
            }
            this.disp1.setText("You have " + bunk + " " + "safe bunks available.");

        }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("bunked", bunk);
        outState.putInt("total", tot);
        outState.putDouble("percent", percent.doubleValue());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.bunkedClassesFromIntent = String.valueOf(savedInstanceState.getInt("bunked"));
        this.totalClassesFromIntent = String.valueOf(savedInstanceState.getInt("total"));
        this.percentageFromIntent = String.valueOf(savedInstanceState.getDouble("percent"));
        this.bunkedView.setText(this.bunkedClassesFromIntent);
        this.bunk = Integer.parseInt(this.bunkedView.getText().toString());
        this.totalView.setText(this.totalClassesFromIntent);
        this.tot = Integer.parseInt(this.totalView.getText().toString());
        this.percentView.setText(this.percentageFromIntent + "%");
        calculate();
    }

    protected void onPause() {
        super.onPause();
        this.percent = Double.valueOf((((double) (this.tot - this.bunk)) / Double.valueOf((double) this.tot).doubleValue()) * 100.0d);
        this.percent = Double.valueOf(((double) Math.round(this.percent.doubleValue() * 100.0d)) / 100.0d);
        ContentValues cv = new ContentValues();
        cv.put("bunkedClasses", Integer.valueOf(bunk));
        cv.put("totalClasses", Integer.valueOf(tot));
        cv.put("percent", percent);
        menuActivity.mydb.getWritableDatabase().update("SubjectData", cv, "_id=?", new String[]{this.idFromIntent + ""});
        setResult(-1, new Intent());
    }



}

