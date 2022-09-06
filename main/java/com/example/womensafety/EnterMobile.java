package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class EnterMobile extends AppCompatActivity {

            EditText mob,mess;
            Button genotp;
            String otp="";
            public static String no;
            final Context context=this;
            MyAdapter helper;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_enter_mobile);
                mob=(EditText)findViewById(R.id.mobnumber);
                genotp=(Button)findViewById(R.id.send);
                helper=new MyAdapter(this);

                genotp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        no=mob.getText().toString();


                        //Getting intent and PendingIntent instance
                        Intent intent=new Intent(getApplicationContext(),MatchOTP.class);
                        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                        otp=getRanString();
                        //Get the SmsManager instance and call the sendTextMessage method to send message
                        SmsManager sms=SmsManager.getDefault();
                        sms.sendTextMessage(no, null, otp, pi,null);
                        Message1.message(context,"Otp Sent");


                        if(no.isEmpty())
                        {
                            Message1.message(context,"Enter Data");
                        }
                        else
                        {
                            long id = helper.insertData(no,otp);
                            if(id>=0)
                            {
                                Message1.message(context,"OTP Sent");
                            } else
                            {
                                Message1.message(context,"Otp not sent");

                            }
                        }

                    }
                });

            }
            public String getRanString() {
                String RANCHARS = "1234567890";
                StringBuilder ra = new StringBuilder();
                Random rnd = new Random();
                while (ra.length() < 5) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * RANCHARS.length());
                    ra.append(RANCHARS.charAt(index));
                }
                String ranStr = ra.toString();
                return ranStr;

            }
        }
