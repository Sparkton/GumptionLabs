package com.example.gumptionlabs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoActivity extends AppCompatActivity {


    int year, month, day;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String imei,date;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference dbInfo;
    EditText fnameEt;
    EditText lnameEt;
    EditText mobEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        fnameEt =findViewById(R.id.infoFname);
        mAuth= FirebaseAuth.getInstance();
        lnameEt =findViewById(R.id.infoLname);
        mobEt =findViewById(R.id.infoMob);
        //First time imei
        mDisplayDate=findViewById(R.id.infoDob);
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager!=null)
        imei=telephonyManager.getDeviceId(); //permission requested in splash
        else
        {
            imei="error";
        }

//date-picker
        findViewById(R.id.infoDob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        InfoActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
            //    Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                date = day + "/" + month + "/" + year;
                Toast.makeText(InfoActivity.this, date, Toast.LENGTH_SHORT).show();
                mDisplayDate.setText(date);
                mDisplayDate.setTextColor(getResources().getColor(R.color.black));
            }
        };

        findViewById(R.id.infoProceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });

    }

    void saveInfo()
    {
        String fname = fnameEt.getText().toString();
        String lname = lnameEt.getText().toString();
        String mob = mobEt.getText().toString();

        if(fname.isEmpty())
        {
            fnameEt.setError("Enter Your First Name");
            fnameEt.requestFocus();
            return;
        }

        if(lname.isEmpty())
        {
            lnameEt.setError("Enter Your Last Name");
            lnameEt.requestFocus();
            return;
        }

        if(mob.isEmpty()|| !check_mobNo(mob))
        {
            mobEt.setError("Enter a valid mobile number");
            mobEt.requestFocus();
            return;
        }

        if(date.isEmpty())
        {
            mDisplayDate.setText("Tap to Select your Date of Birth");
            return;
        }

       /* if(year>=2006)
        {
            mDisplayDate.setText("Date of Birth must be prior to January 1, 2006");
            return;
        }*/

        FirebaseUser user = mAuth.getCurrentUser();


        if(user!=null)
        {
            /*RTDB
            com.example.gumptionlabs.infoDatabaseWrite iDb = new com.example.gumptionlabs.infoDatabaseWrite(user.getEmail(),imei,fname,lname,mob,date);
            dbInfo.child("users").child(user.getUid()).setValue(iDb).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(InfoActivity.this, "Details Added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InfoActivity.this, "Unexpected Error", Toast.LENGTH_SHORT).show();
                }
            });*/

            //Firestore

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            DocumentReference uidRef = rootRef.collection("users").document(uid);
            infoDatabaseWrite iDb = new infoDatabaseWrite(user.getEmail(),imei,fname,lname,mob,date);
            uidRef.set(iDb).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(InfoActivity.this, "Data Successfully Added", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), homeActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InfoActivity.this, "Unexpected Error, Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });


        }
        else
        {
            Toast.makeText(this, "Unexpected Error", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public static boolean check_mobNo(String s)
    {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
}
