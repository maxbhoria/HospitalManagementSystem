package com.example.hospitalmanagementsystem.staff_member;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hospitalmanagementsystem.DatabaseHelper;
import com.example.hospitalmanagementsystem.Feedback;
import com.example.hospitalmanagementsystem.MainActivity;
import com.example.hospitalmanagementsystem.Message;
import com.example.hospitalmanagementsystem.Personal_Info;
import com.example.hospitalmanagementsystem.R;

import java.util.ArrayList;

public class StaffNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent i;
    DatabaseHelper dbh;
    ArrayList<String> d_name = new ArrayList<>();
    ListView lv_bills;
    String username, password, user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv_bills = findViewById(R.id.lv_assigned_doctors);
        Bundle bb = getIntent().getExtras();
        assert bb != null;
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");
        dbh = new DatabaseHelper(this);
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.user_credentials));
        if (y.moveToFirst()) {
            String name = y.getString(1);
            getSupportActionBar().setTitle("Welcome " + name);
        }
        Cursor y1 = dbh.checkduplicates_in_user_credentials("", "", getResources().getString(R.string.staff));
        if (y1.moveToFirst()) {
            while (true) {
                if (y1.getString(4).equals("Y")) {

                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y1.getString(2), y1.getString(3), getResources().getString(R.string.user_credentials));

                    if (z1.moveToNext()) {
                        d_name.add("Dr. " + z1.getString(1) + " " + z1.getString(2));
                    }
                }

                if (y1.isLast())
                    break;
                y1.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, d_name);
            lv_bills.setAdapter(adapter);
        }
        else {
            Message.message(StaffNavigation.this, "Sorry You have No Assigned Doctor Right, Now");
            finish();
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
        //getMenuInflater().inflate(R.menu.staff_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();
        return true;

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
            case R.id.nav_staff_PersonalInfo:
                i = new Intent(StaffNavigation.this, Personal_Info.class);
                break;
            case R.id.nav_staff_feedback:
                i = new Intent(StaffNavigation.this, Feedback.class);
                break;
            case R.id.nav_staff_logout:
                i = new Intent(StaffNavigation.this, MainActivity.class);
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
