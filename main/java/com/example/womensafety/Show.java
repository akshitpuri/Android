package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.womensafety.MapsActivity.drop_place;
import static com.example.womensafety.MapsActivity.pickup_place;

public class Show extends AppCompatActivity {
    MyAdapter4 helper;
    public static String so,des,cart,pr;
    int per;
    EditText s,d,ct;
    Button showmap;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        helper=new MyAdapter4(this);
        s=(EditText)findViewById(R.id.s);
        d=(EditText)findViewById(R.id.d);
        ct=(EditText)findViewById(R.id.ct);

        showmap=(Button)findViewById(R.id.showmap);
        Route fr=new Route();

        so=pickup_place;
        des= drop_place;
        cart=fr.cartype;

        so=so.trim();
        des=des.trim();


        s.setText(so);
        d.setText(des);
        ct.setText(cart);

        showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Show.this,Order.class);
                startActivity(intent);

            }
        });


    }
}
