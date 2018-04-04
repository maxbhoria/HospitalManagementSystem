package com.example.hospitalmanagementsystem.doctor;

import android.content.Intent;
import android.database.Cursor;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hospitalmanagementsystem.DatabaseHelper;
import com.example.hospitalmanagementsystem.Feedback;
import com.example.hospitalmanagementsystem.MainActivity;
import com.example.hospitalmanagementsystem.Message;
import com.example.hospitalmanagementsystem.Personal_Info;
import com.example.hospitalmanagementsystem.R;
import com.example.hospitalmanagementsystem.doctor.doctor_patient.Write_Report;
import com.example.hospitalmanagementsystem.doctor.leaves.Leaves;

import java.util.ArrayList;
import java.util.Map;

public class DoctorNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent i;
    String username, password, user_type;
    ArrayList<String> p_name = new ArrayList<>();
    ArrayList<String> p_u = new ArrayList<>();
    ArrayList<String> p_p = new ArrayList<>();
    ArrayList<String> p_f = new ArrayList<>();
    ArrayList<String> p_problem = new ArrayList<>();
    ArrayList<Map<String,String>> list = new ArrayList<>();
    ListView lv_patients;

    DatabaseHelper dbh = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        lv_patients=findViewById(R.id.lv_current_patients);
        setSupportActionBar(toolbar);
        Bundle bb = getIntent().getExtras();
        assert bb != null;

        dbh = new DatabaseHelper(this);

        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.user_credentials));
        if (y.moveToFirst()) {
            String name = y.getString(1);
            getSupportActionBar().setTitle("Welcome " + name);
        }
        Cursor y1 = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_patient));
        if (y1.moveToFirst()) {
            while (true) {
                if (y1.getString(4).equals("A")) {

                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y1.getString(0), y1.getString(1), getResources().getString(R.string.user_credentials));

                    p_u.add(y1.getString(0));
                    p_p.add(y1.getString(1));
                    p_f.add(y1.getString(6));
                    if (z1.moveToNext()) {
                        p_name.add(z1.getString(1) + " " + z1.getString(2));
                    }

                    p_problem.add(y1.getString(5));

                    dbh1.close();
                }

                if (y1.isLast())
                    break;

                y1.moveToNext();
            }
            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, p_name);
            lv_patients.setAdapter(adapter);
        }
        else {
            Message.message(DoctorNavigation.this, "Sorry You have No Patients Right, Now");
            finish();
        }
        lv_patients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i;
                Bundle b = new Bundle();
                b.putString("username",username);
                b.putString("password",password);
                b.putString("user_type",user_type);
                b.putString("p_username",p_u.get(position));
                b.putString("p_password",p_p.get(position));
                b.putString("problem",p_problem.get(position));
                b.putString("fees",p_f.get(position));

                i = new Intent(DoctorNavigation.this,Write_Report.class);
                i.putExtras(b);
                startActivity(i);
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
        //getMenuInflater().inflate(R.menu.doctor_navigation, menu);
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
