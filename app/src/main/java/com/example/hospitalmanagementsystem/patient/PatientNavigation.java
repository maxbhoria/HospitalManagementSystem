package com.example.hospitalmanagementsystem.patient;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hospitalmanagementsystem.DatabaseHelper;
import com.example.hospitalmanagementsystem.Feedback;
import com.example.hospitalmanagementsystem.MainActivity;
import com.example.hospitalmanagementsystem.Personal_Info;
import com.example.hospitalmanagementsystem.R;
import com.example.hospitalmanagementsystem.patient.view_report.View_Report;

public class PatientNavigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String username, password, user_type;
    DatabaseHelper dbh;
    TextView pname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.patient_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id1 = item.getItemId();
        Bundle b = new Bundle();
        b.putString("username", username);
        b.putString("password", password);
        b.putString("user_type", user_type);

        Intent i=new Intent();

        switch (id1) {
            case R.id.pat_profile:
                i = new Intent(PatientNavigation.this, Personal_Info.class);
                break;
            case R.id.pat_appointment:
                i = new Intent(PatientNavigation.this, Appointment.class);
                break;
            case R.id.pat_reports:
                i = new Intent(PatientNavigation.this, View_Report.class);
                break;
            case R.id.pat_bills:
                i = new Intent(PatientNavigation.this, Bills.class);
                break;
            case R.id.pat_feedback:
                i = new Intent(PatientNavigation.this, Feedback.class);
                break;
        }

        i.putExtras(b);
        startActivity(i);
/*
        i.putExtras(b);
        startActivity(i);
        if (id == R.id.pat_appointment) {
            Intent intent = new Intent(PatientNavigation.this, Appointment.class);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        } else if (id == R.id.pat_reports) {
            Intent i2 = new Intent(PatientNavigation.this, View_Report.class);
            i2.putExtras(b);
            startActivity(i2);
            finish();

        } else if (id == R.id.pat_bills) {
            Intent i3 = new Intent(PatientNavigation.this, Bills.class);
            i3.putExtras(b);
            startActivity(i3);
            finish();

        } else if (id == R.id.pat_feedback) {
            Intent i4 = new Intent(PatientNavigation.this, Feedback.class);
            i4.putExtras(b);
            startActivity(i4);
            finish();
        } else if (id == R.id.pat_profile) {
            Intent i5 = new Intent(PatientNavigation.this, Personal_Info.class);
            i5.putExtras(b);
            startActivity(i5);
            finish();
        } else if (id == R.id.pat_logout) {
            Intent i6 = new Intent(PatientNavigation.this, MainActivity.class);
            i6.putExtras(b);
            startActivity(i6);
            finish();
        }
*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
