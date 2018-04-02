package com.example.hospitalmanagementsystem.doctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.hospitalmanagementsystem.DatabaseHelper;
import com.example.hospitalmanagementsystem.doctor.doctor_patient.Report_Upload;
import com.example.hospitalmanagementsystem.doctor.leaves.Leaves;
import com.example.hospitalmanagementsystem.Feedback;
import com.example.hospitalmanagementsystem.Personal_Info;
import com.example.hospitalmanagementsystem.R;

public class Doctor extends AppCompatActivity {

    String username,password,user_type;
    DatabaseHelper dbh;
    TextView dname;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor);

        dbh = new DatabaseHelper(this);
        dname = findViewById(R.id.tv_d_name);


        Bundle bb = getIntent().getExtras();
        assert bb != null;
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        Cursor y = dbh.checkduplicates_in_user_credentials(username, password,getResources().getString(R.string.user_credentials));

        if (y.moveToFirst()) {
            String name = y.getString(1);
            dname.setText("Welcome Dr. "+name);
        }
    }

    public void onClick(View view){

        Intent i;
        Bundle b = new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        b.putString("user_type",user_type);

        switch (view.getId())
        {
            case R.id.b_d_info:
                i = new Intent(Doctor.this, Personal_Info.class);
                break;
            case R.id.b_add_specialization:
                i = new Intent(Doctor.this, Specialization.class);
                break;
            case R.id.b_d_leaves:
                i = new Intent(Doctor.this, Leaves.class);
                break;
            case R.id.b_d_upload_report:
                i = new Intent(Doctor.this, Report_Upload.class);
                break;
            case R.id.b_d_staff_assigned:
                i = new Intent(Doctor.this, View_Assigned_Staff.class);
                break;
            case R.id.b_d_slot:
                i = new Intent(Doctor.this, D_Slot.class);
                break;
            default:
                i = new Intent(Doctor.this, Feedback.class);
                break;
        }
        i.putExtras(b);
        startActivity(i);
    }
}