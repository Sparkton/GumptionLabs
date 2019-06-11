package com.example.gumptionlabs;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class nav_Activity extends AppCompatActivity {
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:{
                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                    return true;
                }

                case R.id.navigation_dashboard: {
                    Toast.makeText(getApplicationContext(), "DashBoard", Toast.LENGTH_SHORT).show();

                    return true;
                }
                case R.id.navigation_notifications: {
                    Toast.makeText(getApplicationContext(), "Notifications", Toast.LENGTH_SHORT).show();
                    return true;
                }
                case R.id.navigation_settings: {
                    Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
