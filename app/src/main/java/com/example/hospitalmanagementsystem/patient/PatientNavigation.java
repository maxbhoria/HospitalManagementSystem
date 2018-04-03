package com.example.hospitalmanagementsystem.patient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.hospitalmanagementsystem.DatabaseHelper;
import com.example.hospitalmanagementsystem.Doctors_available;
import com.example.hospitalmanagementsystem.Feedback;
import com.example.hospitalmanagementsystem.Login;
import com.example.hospitalmanagementsystem.NearHospitals;
import com.example.hospitalmanagementsystem.Personal_Info;
import com.example.hospitalmanagementsystem.R;
import com.example.hospitalmanagementsystem.patient.view_report.View_Report;

import java.util.HashMap;

public class PatientNavigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {
    String username, password, user_type;
    DatabaseHelper dbh;
    SliderLayout sliderLayout;
    HashMap<String, Integer> Hash_file_maps;
    Button btn_help;
    TextView tv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        btn_help = findViewById(R.id.bt_help);
        tv_address = findViewById(R.id.tv_details);
        tv_address.setText("H-33, Sector- 27, Noida, \n" +
                "U.P. - 201 301\n" + "Phone: 120-2444444 / 120-2445566\n" + "Email: kailash.noida@kailashhealthcare.com");
        btn_help.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 198));
                if (ActivityCompat.checkSelfPermission(PatientNavigation.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
        Hash_file_maps = new HashMap<String, Integer>();

        Hash_file_maps.put("HMS", R.drawable.slider_one);
        Hash_file_maps.put("HMS", R.drawable.slider_two);
        Hash_file_maps.put("HMS", R.drawable.slider_three);
        Hash_file_maps.put("HMS", R.drawable.slider_four);
        Hash_file_maps.put("HMS", R.drawable.slider_five);

        for (String name : Hash_file_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(PatientNavigation.this);
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(3000);

        sliderLayout.addOnPageChangeListener(PatientNavigation.this);
        dbh = new DatabaseHelper(this);
        Bundle bb = getIntent().getExtras();
        assert bb != null;
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.user_credentials));

        if (y.moveToFirst()) {
            String name = y.getString(1);
            getSupportActionBar().setTitle("Welcome " + name);
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PatientNavigation.this, NearHospitals.class);
                startActivity(intent);
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
        //getMenuInflater().inflate(R.menu.patient_navigation, menu);
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

        Intent i = new Intent();

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
            case R.id.pat_d_availabe:
                i = new Intent(PatientNavigation.this, Doctors_available.class);
                break;
            case R.id.pat_logout:
                i = new Intent(PatientNavigation.this, Login.class);
                break;
        }

        i.putExtras(b);
        startActivity(i);
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

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
