package com.example.hospitalmanagementsystem.Patient.View_Report;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hospitalmanagementsystem.R;

public class Final_View_Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_view_report);
        Bundle bb = getIntent().getExtras();
        String report = bb.getString("report");
        TextView final_report = (TextView) findViewById(R.id.tv_report);
        final_report.setText(report);


    }
}
