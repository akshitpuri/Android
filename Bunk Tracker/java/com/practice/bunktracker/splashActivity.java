package com.practice.bunktracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread mythread=new Thread()
        {
            public  void run()
            {
                try {
                    Thread.sleep(1500);
                } catch(Exception e)
                {
                    Toast.makeText(splashActivity.this, "Error Due To" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finally {
                    Intent myintent=new Intent(splashActivity.this,menuActivity.class);
                    startActivity(myintent);
                    splashActivity.this.finish();
                }
            }
        };mythread.start();

    }
}
