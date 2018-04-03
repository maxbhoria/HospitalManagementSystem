package com.example.hospitalmanagementsystem;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hospitalmanagementsystem.desktop_admin.AdminNavigation;
import com.example.hospitalmanagementsystem.doctor.DoctorNavigation;
import com.example.hospitalmanagementsystem.patient.PatientNavigation;
import com.example.hospitalmanagementsystem.staff_member.StaffNavigation;

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
        toolbar = findViewById(R.id.toolbar);
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
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
                        i = new Intent(Login.this, DoctorNavigation.class);
                    } else if (ut.equals("Patient")) {
                        i = new Intent(Login.this, PatientNavigation.class);
                    } else if (ut.equals("Staff Member")) {
                        i = new Intent(Login.this, StaffNavigation.class);
                    } else {
                        i = new Intent(Login.this, AdminNavigation.class);
                    }

                    i.putExtras(b);
                    startActivity(i);
                    finishAffinity();
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
