package com.example.hospitalmanagementsystem.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hospitalmanagementsystem.DatabaseHelper;
import com.example.hospitalmanagementsystem.Feedback;
import com.example.hospitalmanagementsystem.MainActivity;
import com.example.hospitalmanagementsystem.Personal_Info;
import com.example.hospitalmanagementsystem.R;
import com.example.hospitalmanagementsystem.doctor.doctor_patient.Report_Upload;
import com.example.hospitalmanagementsystem.doctor.leaves.Leaves;

public class DoctorNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent i;
    String username, password, user_type;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bb = getIntent().getExtras();
        assert bb != null;

        dbh = new DatabaseHelper(this);

        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

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
        getMenuInflater().inflate(R.menu.doctor_navigation, menu);
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
        int id = item.getItemId();

        Bundle b = new Bundle();
        b.putString("username", username);
        b.putString("password", password);
        b.putString("user_type", user_type);

        switch (id) {
            case R.id.nav_doc_pInfo:
                i = new Intent(DoctorNavigation.this, Personal_Info.class);
                break;
            case R.id.nav_doc_specialisations:
                i = new Intent(DoctorNavigation.this, Specialization.class);
                break;
            case R.id.nav_doc_leaves:
                i = new Intent(DoctorNavigation.this, Leaves.class);
                break;
            case R.id.nav_doc_uploadReport:
                i = new Intent(DoctorNavigation.this, Report_Upload.class);
                break;
            case R.id.nav_doc_viewAssignedStaff:
                i = new Intent(DoctorNavigation.this, View_Assigned_Staff.class);
                break;
            case R.id.nav_doc_TimingSlot:
                i = new Intent(DoctorNavigation.this, D_Slot.class);
                break;
            case R.id.nav_doc_feedback:
                i = new Intent(DoctorNavigation.this, Feedback.class);
                break;
            case R.id.nav_doc_logout:
                i = new Intent(DoctorNavigation.this, MainActivity.class);
                Toast.makeText(this, " Log out Successfully ", Toast.LENGTH_SHORT).show();
                break;
        }
        i.putExtras(b);
        startActivity(i);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
