package com.example.sejjoh.gslsadmin;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText ln_email;
    private TextInputEditText ln_password;
    private  String logemail, logpassword;
    Button logbtn;
    Button tv_register;
    TextView tv_forgot;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        ln_email = findViewById(R.id.mail);
        ln_password =findViewById(R.id.password);

        tv_register = findViewById(R.id.newAccount);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        tv_forgot = findViewById(R.id.forglogin);
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Forgotpassword.class);
                startActivity(intent);
            }
        });
        logbtn = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressbar2);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

                ln_password.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

    }
    public void login(){
        initialize();
        if(!validate()) {

        }
        else {
            onLoginSuccess();
        }
    }

    public void onLoginSuccess(){
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(logemail, logpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public boolean validate(){
        boolean valid = true;
        if(logemail.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(logemail).matches()){
            ln_email.setError("Please Enter Email Address");
            valid = false;
        }
        if(logpassword.isEmpty()){
            ln_password.setError("Please Enter Password");
            valid = false;
        }
        return valid;
    }
    public void initialize(){
        logemail = ln_email.getText().toString().trim();
        logpassword = ln_password.getText().toString().trim();
    }
}