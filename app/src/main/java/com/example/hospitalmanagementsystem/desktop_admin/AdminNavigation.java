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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hospitalmanagementsystem.CustomListViewAdapter;
import com.example.hospitalmanagementsystem.DatabaseHelper;
import com.example.hospitalmanagementsystem.MainActivity;
import com.example.hospitalmanagementsystem.Message;
import com.example.hospitalmanagementsystem.Personal_Info;
import com.example.hospitalmanagementsystem.R;
import com.example.hospitalmanagementsystem.RowItem;

import java.util.ArrayList;
import java.util.List;

public class AdminNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent i;
    String username, password, user_type;
    DatabaseHelper dbh;
    ListView lv_appointment;
    List<String> u_p;
    List<String> p_p;
    List<RowItem> rowItems;
    ArrayList<String> doc = new ArrayList<>();
    ArrayList<String> pat = new ArrayList<>();
    ArrayList<String> pro = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv_appointment = (ListView) findViewById(R.id.lv_pending_appontments);
        u_p = new ArrayList<>();
        p_p = new ArrayList<>();
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
            while (true) {

                //pateinet approvl has three mode W - wait, A - approved, F - finished

                if (y.getString(4).equals("W")) {
                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(0), y.getString(1), getResources().getString(R.string.user_credentials));
                    DatabaseHelper dbh2 = new DatabaseHelper(this);
                    Cursor z2 = dbh2.checkduplicates_in_user_credentials(y.getString(2), y.getString(3), getResources().getString(R.string.user_credentials));
                    u_p.add(y.getString(0));
                    p_p.add(y.getString(1));

                    if (z1.moveToNext()) {
                        pat.add(z1.getString(1) + " " + z1.getString(2));
                    }

                    if (z2.moveToNext()) {
                        doc.add(z2.getString(1) + " " + z2.getString(2));
                    }
                    pro.add(y.getString(5));

                    dbh1.close();
                    dbh2.close();
                }

                if (y.isLast())
                    break;

                y.moveToNext();
            }

            rowItems = new ArrayList<>();

            for (int i = 0; i < doc.size(); i++) {
                RowItem item = new RowItem(doc.get(i), pat.get(i), pro.get(i));
                rowItems.add(item);
            }

            CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.custom_adapter, rowItems);
            lv_appointment.setAdapter(adapter);
        } else {
            Message.message(AdminNavigation.this, "No Pending Apppointments");
            finish();
        }
        lv_appointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor x = dbh.checkduplicates_in_user_credentials(u_p.get(position), p_p.get(position), pro.get(position));
                boolean y = false;
                if (x.moveToFirst()) {
                    y = dbh.update_doctor_patient(x.getString(0), x.getString(1), x.getString(2), x.getString(3), "A", x.getString(5), x.getString(6), x.getString(7));
                }

                if (y) {
                    Message.message(AdminNavigation.this, "Application Approved");
                    finish();
                } else {
                    Message.message(AdminNavigation.this, "Not Approved");
                }
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
