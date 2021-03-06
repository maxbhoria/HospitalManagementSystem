package com.example.hospitalmanagementsystem.desktop_admin;

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
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.example.hospitalmanagementsystem.DatabaseHelper;
import com.example.hospitalmanagementsystem.MainActivity;
import com.example.hospitalmanagementsystem.Personal_Info;
import com.example.hospitalmanagementsystem.R;

import java.util.HashMap;

public class AdminNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent i;
    String username, password, user_type;
    DatabaseHelper dbh;
    TextView tv_details;
    SliderLayout sliderLayout;
    HashMap<String, Integer> Hash_file_maps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        tv_details = findViewById(R.id.tv_details);
        tv_details.setText("1). In this admin have to approve the appointment\n"+"2) In this admin can maintain the pending bills\n"
                +"3) In this Admin have to assign the staff to doctors\n"+"4) In this Admin can " +
                "delete any user");
        Bundle bb = getIntent().getExtras();
        assert bb != null;
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");
        dbh = new DatabaseHelper(this);
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.user_credentials));
        if(y.moveToFirst())
        {
            String name = y.getString(1);
            getSupportActionBar().setTitle("Welcome " + name);
        }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.admin_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle b = new Bundle();
        b.putString("username", username);
        b.putString("password", password);
        b.putString("user_type", user_type);

        switch (id) {
            case R.id.nav_admin_pInfo:
                i = new Intent(AdminNavigation.this, Personal_Info.class);
                break;
            case R.id.nav_admin_Remaining_bills:
                i = new Intent(AdminNavigation.this, Remaining_patient_bills.class);
                break;
            case R.id.nav_admin_assign:
                i = new Intent(AdminNavigation.this, Assign_Staff.class);
                break;
            case R.id.nav_admin_delete_user:
                i = new Intent(AdminNavigation.this, Delete_Users.class);
                break;
            case R.id.nav_admin_grant_patient_appointment:
                i = new Intent(AdminNavigation.this, Grant_appointment.class);
                break;
            case R.id.nav_admin_logout:
                i = new Intent(AdminNavigation.this, MainActivity.class);
                Toast.makeText(this, " Log out Successfully ", Toast.LENGTH_SHORT).show();
                break;
        }

        i.putExtras(b);
        startActivity(i);
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
