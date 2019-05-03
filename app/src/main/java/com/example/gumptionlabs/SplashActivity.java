package com.example.gumptionlabs;

import android.Manifest;

import android.content.Intent;

import android.content.pm.PackageManager;

import android.os.Handler;

import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;

import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Toast;


import java.security.Permission;

import java.util.ArrayList;

import java.util.List;


public class SplashActivity extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkAndRequestPermissions();
    }
    public void success() {
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Splash screen timer
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                h.removeCallbacksAndMessages(null);
                finish();
            }
        }, 4000);
    }


    private boolean checkAndRequestPermissions() {
       // int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
       // int wstorage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
       // int rstorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int rphoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        /*if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }

        if (wstorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (rstorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }*/
        if (rphoneState != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        success();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int flag = 0;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                flag++;
            }
        }
        if (flag == grantResults.length) {
            success();
        } else {
            Toast.makeText(this, "To Proceed, Please Grant All Permissions", Toast.LENGTH_SHORT).show();
            checkAndRequestPermissions();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
