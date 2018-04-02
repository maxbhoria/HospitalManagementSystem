package com.example.hospitalmanagementsystem.patient.view_report;

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
        assert bb != null;
        String report = bb.getString("report");
        TextView final_report = findViewById(R.id.tv_report);
        final_report.setText(report);

    }
}
