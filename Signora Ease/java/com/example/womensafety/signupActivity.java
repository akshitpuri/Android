package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signupActivity extends AppCompatActivity {
    Button sg;
    TextView lg;

    LoginDataBaseAdapter loginDataBaseAdapter;
    EditText fnameEdit, userEdit, passEdit, confirmEdit, phEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fnameEdit = (EditText) findViewById(R.id.signup_name);

        userEdit = (EditText) findViewById(R.id.signup_user);
        passEdit = (EditText) findViewById(R.id.signup_password);
        confirmEdit = (EditText) findViewById(R.id.signup_pass);
        phEdit = (EditText) findViewById(R.id.signup_phone);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        sg = findViewById(R.id.signupbtn);
        sg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = fnameEdit.getText().toString();

                String user = userEdit.getText().toString();
                String pass = passEdit.getText().toString();
                String confirmPass = confirmEdit.getText().toString();
                String phone = phEdit.getText().toString();

                if (fname.equals("") || user.equals("") || pass.equals("") || confirmPass.equals("") || phone.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (!pass.equals(confirmPass)) {
                    Toast.makeText(getApplicationContext(), "Please enter same password", Toast.LENGTH_SHORT).show();
                    return;
                }else if(pass.length() < 4  ){
                    Toast.makeText(getApplicationContext(), "Password must contain atleast 4 characters", Toast.LENGTH_SHORT).show();
                    return;
                } else if(phone.length()<10 || phone.length()>10){
                    Toast.makeText(getApplicationContext(), "Enter correct phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    Contact contact = new Contact();
                    contact.setFname(fname);
                    contact.setUser(user);
                    contact.setPass(pass);
                    contact.setCpass(confirmPass);
                    contact.setPhone(phone);

                    loginDataBaseAdapter.insertEntry(contact);
                    Intent myintent=new Intent(signupActivity.this,loginActivity.class);
                    startActivity(myintent);
                    signupActivity.this.finish();
                    Toast.makeText(signupActivity.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
                }


            }
        });
        lg = findViewById(R.id.lg);
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(signupActivity.this,loginActivity.class);
                startActivity(myintent);
                signupActivity.this.finish();
            }
        });


    }
}
