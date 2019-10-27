package com.example.sejjoh.gslsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Forgotpassword extends AppCompatActivity {

    private TextInputEditText fgemail;
    private  String forgemail;
   private Button fgforgot;
    ProgressBar forgotprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);


        fgemail = findViewById(R.id.forgemail);
        forgotprogress = findViewById(R.id.forgotProgress);
        fgforgot = findViewById(R.id.resetbtn);
        fgforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();

                fgemail.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });


    }
    public void reset(){
        initialize();
        if(!validate()) {

        }
        else {
            onResetSuccess();
        }
    }
    public void onResetSuccess(){
        forgotprogress.setVisibility(View.VISIBLE);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(forgemail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    forgotprogress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please check your email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Forgotpassword.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public boolean validate(){
        boolean valid = true;
        if(forgemail.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(forgemail).matches()){
            fgemail.setError("Please Enter valid Email Address");
            valid = false;
        }
        return valid;
    }
    public void initialize(){
        forgemail = fgemail.getText().toString().trim();
    }

}
