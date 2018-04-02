package com.example.hospitalmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Toolbar toolbar;
    Button bLogInRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SET UP THE DATABASE
        databaseHelper = new DatabaseHelper(this);
        //SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        bLogInRegister = findViewById(R.id.bloginregister);
        bLogInRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

    }

    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, Doctors_available.class);
        startActivity(i);
    }
    public void onClick2(View view) {
        Intent i = new Intent(MainActivity.this, Services_offered.class);
        startActivity(i);
    }
}
