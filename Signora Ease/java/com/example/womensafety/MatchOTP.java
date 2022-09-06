package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MatchOTP extends AppCompatActivity {
    EditText otp;
    Button ok;
    MyAdapter helper;
    String getotp;
    final Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_otp);
        otp=(EditText)findViewById(R.id.otp);
        ok=(Button)findViewById(R.id.ok);
        helper=new MyAdapter(this);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbotp=helper.getIdData();
                getotp=otp.getText().toString();
                String otp=helper.getOtpData(dbotp);
                if(otp.equals(getotp))
                {
                    Message1.message(context,"Otp Matched"+" :: "+"Welcome to DriveSafe");
                    Intent intent=new Intent(context,MapsActivity.class);
                    startActivity(intent);
                }
            }
        });



    }
    }
