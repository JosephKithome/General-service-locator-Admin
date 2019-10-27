package com.example.sejjoh.gslsadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private TextInputEditText mConfirmpass;
    private Button Bsignup;
    private TextInputEditText mName;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mName = (TextInputEditText) findViewById(R.id.username);
        mEmail = (TextInputEditText) findViewById(R.id.email);
        mPassword = (TextInputEditText) findViewById(R.id.password);
        mConfirmpass = (TextInputEditText) findViewById(R.id.cpassword);
        Bsignup = (Button) findViewById(R.id.submitBtn);
       Bsignup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String email=mEmail.getText().toString();
               String txt_username=mName.getText().toString();
               String password=mPassword.getText().toString();
               String txt_cPassword=mConfirmpass.getText().toString();
               if (!TextUtils.isEmpty(txt_username) ||!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)
               || !TextUtils.isEmpty(txt_cPassword));{
                   Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
              }if (password.length()<6){
                   Toast.makeText(RegisterActivity.this, "password should not be less than 6 characters", Toast.LENGTH_SHORT).show();
               }else {
                   startRegistering(txt_username, password,email);
               }
           }
       });


    }
    private void startRegistering(final String username, String password, final String email) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser=mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid=firebaseUser.getUid();
                            mDatabase= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String,String> hashMap=new HashMap<>();

                            hashMap.put("id" ,userid);
                            hashMap.put("username", username);
                            hashMap.put("email",email);
                            hashMap.put("imageUrl","default");
                            hashMap.put("status","offline");
                            hashMap.put("search",username.toLowerCase());
                            mDatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent registerIntent= new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(registerIntent);

                                    }
                                }
                            });


                        }else {
                            Toast.makeText(RegisterActivity.this, "You cant register with this email and password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
