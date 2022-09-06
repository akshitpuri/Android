package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.womensafety.MapsActivity.ctype;
import static com.example.womensafety.MapsActivity.drop_place;
import static com.example.womensafety.MapsActivity.pickup_place;

public class Route extends AppCompatActivity {
    Spinner spinner2;
    Button btnAdd;
    String str,ci;
    String[] values;
    MyAdapter1 helper;
    MyAdapter2 helper1;
    MyAdapter3 helper2;
    MyAdapter4 helper3;
    DriverAdapter dvhelper;
    Context context=this;
    public static String source,destination,cartype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        btnAdd = (Button) findViewById(R.id.button2);
        helper=new MyAdapter1(context);
        helper1=new MyAdapter2(context);
        helper2=new MyAdapter3(context);
        helper3=new MyAdapter4(context);
        dvhelper=new DriverAdapter(context);

        spinner2 = (Spinner) findViewById(R.id.spinner3);
        source=pickup_place;
        destination=drop_place;
        cartype=ctype;

        loadSpinnerData2();

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                cartype = parent.getItemAtPosition(position).toString(); //this is your selected item
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helper2.insertData("Mini");
                helper2.insertData("Micro");
                helper2.insertData("Sedan");
                helper2.insertData("Prime");

                dvhelper.insertData("Ms. Jagisha Luther","Mini","PB 08 1692","9877773778");
                dvhelper.insertData("Mrs. Yashaswi","Micro","PB 08 9374","9915731917");
                dvhelper.insertData("Ms. Kirti Narang","Sedan","PB 08 5819","7973119544");
                dvhelper.insertData("Mr. Akshit Puri","Prime","PB 08 7820","7837239393");

                Intent i=new Intent(context,Show.class);
                startActivity(i);
            }
        });
    }

    private void loadSpinnerData2() {
        ci=helper2.getData();
        str = ci.toString();
        values=str.split(" ");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,values);

        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }
}
