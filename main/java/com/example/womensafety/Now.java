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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.womensafety.MapsActivity.drop_place;
import static com.example.womensafety.MapsActivity.pickup_place;

public class Now extends AppCompatActivity {
    EditText eso,edes,efare,ecid,ecart,edname,ecarno,edmob;
    String sou,desti,cart,time,date,cid,str1,st;

    BookAdapter helper;
    DriverAdapter dvhelper;
    Button btn;
    String num;
    MyAdapter helper1;
    String values1[];
    String msg;
    final Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now);
        btn=(Button)findViewById(R.id.button3);
        ecid=(EditText)findViewById(R.id.cname);
        eso=(EditText)findViewById(R.id.source);
        edes=(EditText)findViewById(R.id.destination);

        ecart=(EditText)findViewById(R.id.dcartype);
        edname=(EditText)findViewById(R.id.dname);
        ecarno=(EditText)findViewById(R.id.carno);
        edmob=(EditText)findViewById(R.id.dmob);
        helper=new BookAdapter(this);
        helper1=new MyAdapter(this);
        dvhelper=new DriverAdapter(this);
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        time = dfTime.format(Calendar.getInstance().getTime());
        Show s=new Show();
        sou=pickup_place;
        desti=drop_place;
        cart=s.cart;

        DateFormat df1=new SimpleDateFormat("dd/MM/yyyy");
        date=df1.format(Calendar.getInstance().getTime());
        cid=helper1.getIdData();

        ecid.setText(cid);
        eso.setText(sou);
        edes.setText(desti);

        ecart.setText(cart);


        String sscart=cart;
        sscart=sscart.trim();
        st=dvhelper.getData(sscart);
        str1 = st.toString();
        values1=str1.split("/");

        edname.setText(values1[0]);
        ecarno.setText(values1[1]);
        edmob.setText(values1[2]);

        EnterMobile et=new EnterMobile();
        num =et.no;
        msg=values1[0]+" is on the way."+"  "+"Car No. - "+values1[1]+"  "+"Mobile No. - "+values1[2];
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(num, null, msg, pi,null);
                helper.insertData(cid,sou,desti,cart,date,time,values1[0]);
            }
        });



    }
}