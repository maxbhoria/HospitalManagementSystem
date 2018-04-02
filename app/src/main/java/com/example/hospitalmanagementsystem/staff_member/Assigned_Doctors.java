package com.example.hospitalmanagementsystem.staff_member;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hospitalmanagementsystem.DatabaseHelper;
import com.example.hospitalmanagementsystem.Message;
import com.example.hospitalmanagementsystem.R;

import java.util.ArrayList;


public class Assigned_Doctors extends AppCompatActivity {

    String username, password, user_type;
    DatabaseHelper dbh = new DatabaseHelper(this);
    ArrayList<String> d_name = new ArrayList<>();
    ListView lv_bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assigned_doctors);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("user_type");

        lv_bills = findViewById(R.id.lv_assigned_doctors);

        Cursor y = dbh.checkduplicates_in_user_credentials("", "", getResources().getString(R.string.staff));

        if (y.moveToFirst()) {
            while (true) {
                if (y.getString(4).equals("Y")) {

                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(2), y.getString(3), getResources().getString(R.string.user_credentials));

                    if (z1.moveToNext()) {
                        d_name.add("Dr. " + z1.getString(1) + " " + z1.getString(2));
                    }
                }

                if (y.isLast())
                    break;
                y.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, d_name);
            lv_bills.setAdapter(adapter);
        } else {
            Message.message(Assigned_Doctors.this, "Sorry You have No Bills Right, Now");
            finish();
        }
    }
}
