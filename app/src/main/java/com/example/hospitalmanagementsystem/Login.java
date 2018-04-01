package com.example.hospitalmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hospitalmanagementsystem.desktop_admin.Desktop_Admin;
import com.example.hospitalmanagementsystem.doctor.Doctor;
import com.example.hospitalmanagementsystem.patient.Patient;
import com.example.hospitalmanagementsystem.staff_member.Staff_Member;

/**
 * Created by Neeraj on 17-Mar-16.
 */
public class Login extends AppCompatActivity {

    EditText username, password;
    String usernames, passwords;
    Button Bregister, Blogin;
    DatabaseHelper dbh;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = findViewById(R.id.etusername);
        password = findViewById(R.id.etpassword);
        Bregister = findViewById(R.id.bregister);
        Blogin = findViewById(R.id.blogin);
        dbh = new DatabaseHelper(this);

        Bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        Blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernames = username.getText().toString();
                passwords = password.getText().toString();

                Cursor y = dbh.checkduplicates_in_user_credentials(usernames, passwords, getResources().getString(R.string.user_credentials));

                if (y.moveToFirst()) {
                    String ut = y.getString(7);
                    Message.message(Login.this, "Welcome");

                    Bundle b = new Bundle();
                    b.putString("username", usernames);
                    b.putString("password", passwords);
                    b.putString("user_type", ut);

                    Intent i;
                    if (ut.equals("Doctor")) {
                        i = new Intent(Login.this, Doctor.class);
                    } else if (ut.equals("Patient")) {
                        i = new Intent(Login.this, Patient.class);
                    } else if (ut.equals("Staff Member")) {
                        i = new Intent(Login.this, Staff_Member.class);
                    } else {
                        i = new Intent(Login.this, Desktop_Admin.class);
                    }

                    i.putExtras(b);
                    startActivity(i);
                } else {
                    Message.message(Login.this, "No Such User Exists");
                    Message.message(Login.this, "Please Register Your self");
                }

                y.close();
            }
        });
        dbh.close();
    }
}
