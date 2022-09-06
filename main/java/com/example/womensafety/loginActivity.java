package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    Button lg;
    TextView sg;
    LoginDataBaseAdapter loginDataBaseAdapter;
    EditText userEdit, passEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        lg = findViewById(R.id.loginbtn);
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userEdit = (EditText) findViewById(R.id.loginEv);
                String user = userEdit.getText().toString();
                passEdit = (EditText) findViewById(R.id.passwordlg);
                String pass = passEdit.getText().toString();

                String storedPassword = loginDataBaseAdapter.getSingleEntry(user);
                if (pass.equals(storedPassword)) {
                    Intent myintent=new Intent(loginActivity.this,MainActivity.class);
                    startActivity(myintent);
                    loginActivity.this.finish();
                    Toast.makeText(loginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(loginActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }



            }
        });
        sg = findViewById(R.id.sg);
        sg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(loginActivity.this,signupActivity.class);
                startActivity(myintent);
                loginActivity.this.finish();
            }
        });



    }
}
