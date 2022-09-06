package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread mythread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (Exception e) {
                    Toast.makeText(splashActivity.this, "Error Due To" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                            .getBoolean("isFirstRun", true);
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putBoolean("isFirstRun", false).commit();

                    if (isFirstRun) {

                        startActivity(new Intent(splashActivity.this, loginActivity.class));

                    }

                    else{ startActivity(new Intent(splashActivity.this, MainActivity.class));}


                    splashActivity.this.finish();
                }
            }
        }
                ;mythread.start();
    }
}
