package com.example.hospitalmanagementsystem;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hospitalmanagementsystem.desktop_admin.AdminNavigation;
import com.example.hospitalmanagementsystem.doctor.DoctorNavigation;
import com.example.hospitalmanagementsystem.patient.PatientNavigation;
import com.example.hospitalmanagementsystem.staff_member.StaffNavigation;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    EditText fname, lname, age, dd, yy, city, pincode, mobno, uname, password;
    String fnames, lnames, ages, sexs, bgroups, dobs, dds, yys, mms, citys, pincodes, mobnos, unames, passwords, utypes;
    Button register;
    Spinner usertype, mm, sex, bgroup;
    Toolbar toolbar;

    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DEFINING ALL VIEWS
        fname = findViewById(R.id.etfname);
        lname = findViewById(R.id.etlname);
        age = findViewById(R.id.etage);
        dd = findViewById(R.id.etdd);
        yy = findViewById(R.id.etyy);
        city = findViewById(R.id.etcity);
        pincode = findViewById(R.id.etpin);
        mobno = findViewById(R.id.etmobile);
        uname = findViewById(R.id.etusername);
        password = findViewById(R.id.etpassword);
        register = findViewById(R.id.bregister);
        usertype = findViewById(R.id.spinnerusertype);
        mm = findViewById(R.id.spinnerMonth);
        sex = findViewById(R.id.spinnerSex);
        bgroup = findViewById(R.id.spinnerbgroup);
        dbh = new DatabaseHelper(this);

        //SET UP THE SPINNER DROPDOWN
        List<String> category = new ArrayList<>();
        category.add("Patient");
        category.add("Doctor");
        category.add("Staff Member");
        category.add("Desktop Admin");

        List<String> sexc = new ArrayList<>();
        sexc.add("Male");
        sexc.add("Female");

        List<String> bgroupc = new ArrayList<>();
        bgroupc.add("A+");
        bgroupc.add("A-");
        bgroupc.add("B+");
        bgroupc.add("B-");
        bgroupc.add("O+");
        bgroupc.add("O-");
        bgroupc.add("AB+");
        bgroupc.add("AB-");

        List<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, category);
        ArrayAdapter<String> adapterm = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bgroupc);
        ArrayAdapter<String> adapterb = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexc);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usertype.setAdapter(adapter);
        mm.setAdapter(adapterm);
        sex.setAdapter(adapters);
        bgroup.setAdapter(adapterb);

        register.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                lnames = lname.getText().toString();
                fnames = fname.getText().toString();
                ages = age.getText().toString();
                dds = dd.getText().toString();
                yys = yy.getText().toString();
                citys = city.getText().toString();
                pincodes = pincode.getText().toString();
                unames = uname.getText().toString();
                passwords = password.getText().toString();
                mobnos = mobno.getText().toString();
                utypes = usertype.getSelectedItem().toString();
                mms = mm.getSelectedItem().toString();
                sexs = sex.getSelectedItem().toString();
                bgroups = bgroup.getSelectedItem().toString();

                if (fnames.equals("") || lnames.equals("") || ages.equals("") || dds.equals("") ||
                        yys.equals("") || citys.equals("") || pincodes.equals("") || unames.equals("") ||
                        passwords.equals("") || mobnos.equals("")) {
                    Message.message(Register.this, "Please Fill in all your Details");
                } else {

                    //CHECK WHETHER THE ENTRY ALREADY EXISTS
                    Cursor y = dbh.checkduplicates_in_user_credentials(unames, passwords, getResources().getString(R.string.user_credentials));
                    if (y.moveToFirst()) {
                        Message.message(Register.this, "User Already Exists");
                        Message.message(Register.this, "Login With Your Username and Password");
                        finish();
                    } else {
                        //SETUP DATABASE QUERY
                        if (dds.length() == 1)
                            dds = "0" + dds;
                        dobs = dds + " " + mms + " " + yys;

                        boolean b = dbh.insert_user_credentials(fnames, lnames, ages, dobs, citys, pincodes, unames, passwords, mobnos, utypes, sexs, bgroups);
                        if (b) {
                            Intent i;
                            Bundle bb = new Bundle();
                            bb.putString("username", unames);
                            bb.putString("password", passwords);
                            bb.putString("user_type", utypes);

                            switch (utypes) {
                                case "Patient":
                                    i = new Intent(Register.this, PatientNavigation.class);
                                    break;
                                case "Doctor":
                                    i = new Intent(Register.this, DoctorNavigation.class);
                                    break;
                                case "Staff Member":
                                    i = new Intent(Register.this, StaffNavigation.class);
                                    break;
                                default:
                                    i = new Intent(Register.this, AdminNavigation.class);
                                    break;
                            }

                            i.putExtras(bb);
                            startActivity(i);
                            finishAffinity();
                        }
                    }
                }
            }
        });
    }
}
