package com.example.hospitalmanagementsystem.patient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hospitalmanagementsystem.R;

public class Wait_Appointment extends AppCompatActivity {

    String username,password,user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_appointment);

        Bundle bb = getIntent().getExtras();
        assert bb != null;
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");
    }
}
