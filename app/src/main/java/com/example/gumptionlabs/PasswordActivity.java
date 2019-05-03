package com.example.gumptionlabs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordActivity extends AppCompatActivity {

    private EditText etp;
    private Button bn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        etp=(EditText)findViewById(R.id.passEmail);
        bn=(Button)findViewById(R.id.passBtn);
        mAuth=FirebaseAuth.getInstance();

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usermail = etp.getText().toString().trim();  //trim removes blank spaces at beginning and end
                if(usermail.equals(""))
                {
                    etp.setError("Please enter your registered e-mail");
                }
                else
                {
                    mAuth.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(PasswordActivity.this, "Password reset e-mail sent", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this,LoginActivity.class));
                            }
                            else
                            {
                                etp.setError("Entered e-mail not registered");
                            }
                        }
                    });
                }
            }
        });

    }
}