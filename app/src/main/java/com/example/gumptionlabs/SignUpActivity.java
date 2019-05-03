package com.example.gumptionlabs;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText upEmailEt, upPasswordEt;
    ProgressBar upPbar;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_sign_up);
        upEmailEt = (EditText) findViewById(R.id.upEmail);
        upPasswordEt = (EditText) findViewById(R.id.upPassword);
      //  upPbar=(ProgressBar)findViewById(R.id.upProgressBar) ;
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.upSignup).setOnClickListener(this);
        findViewById(R.id.upSignin).setOnClickListener(this);
    }

    private void registerUser() {
        String email = upEmailEt.getText().toString();
        String password = upPasswordEt.getText().toString();

        if (email.isEmpty()) {
            upEmailEt.setError("Email is required");
            upEmailEt.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            upEmailEt.setError("Please enter a valid email");
            upEmailEt.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            upPasswordEt.setError("Password is required");
            upPasswordEt.requestFocus();
            return;
        }

        if (password.length() < 6) {
            upPasswordEt.setError("Password must contain at least 6 characters");
            upPasswordEt.requestFocus();
            return;
        }

//        upPbar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
//                upPbar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    //finish();
                    //Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //So that we dont return to login screen after pressing back button
                    //startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Registration successful, verification mail sent", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();

                        }
                    });

                    /*if(user.isEmailVerified())
            {
                tv.setText("Email has been verified");
                tv.setTextColor(Color.BLACK);
            }
            else
            {
                tv.setText("Click to verify your email");
                tv.setTextColor(Color.RED);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Verification mail sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }*/

                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "ERROR: This email has already been registered", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upSignup:
                registerUser();
                break;

            case R.id.upSignin:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}