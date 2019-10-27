package com.example.sejjoh.gslsadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CompPosts extends AppCompatActivity {
    private ImageView MImageSelect;
    private EditText MAirportName;
    private EditText mDesc;
    private Button Msubmit;
    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;
    private StorageReference mStorage;
    Uri downloadUri;
    private ProgressDialog mProgresss;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private EditText mCheckin;
    private EditText mcheckout;
    private EditText mlat;
    private EditText mlong;
    private EditText mlocation;
    private EditText mAmenities;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compposts);
            mAuth=FirebaseAuth.getInstance();
            mCurrentUser= mAuth.getCurrentUser();
            mStorage= FirebaseStorage.getInstance().getReference();
            mDatabase= FirebaseDatabase.getInstance().getReference().child("CompPosts");
            MImageSelect = (ImageView) findViewById(R.id.imageview);
            Msubmit = (Button) findViewById(R.id.Msubmit);
            mCheckin=(EditText)findViewById(R.id.checkin);
            mAmenities=(EditText)findViewById(R.id.amenities);
            mcheckout=(EditText)findViewById(R.id.checkout);
            mlat=(EditText)findViewById(R.id.latitude);
            mlong=(EditText)findViewById(R.id.longitude);
            mlocation=(EditText)findViewById(R.id.location);
            mDesc = (EditText) findViewById(R.id.mDescription);
            MAirportName = (EditText) findViewById(R.id.mTitle);
            mProgresss=new ProgressDialog(this);
            MImageSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, GALLERY_REQUEST);
                }
            });
            Msubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPosting();
                }
            });

        }

        private void startPosting() {
            mProgresss.setMessage("uploading to computer posts...");
            mProgresss.show();
            final String title_val=MAirportName.getText().toString().trim();
            final  String checkin_val=mCheckin.getText().toString().trim();
            final String Desc_val=mDesc.getText().toString().trim();
            final  String checkout_val=mcheckout.getText().toString().trim();
            final String location_val=mlocation.getText().toString().trim();
            final String lat_val=mlat.getText().toString().trim();
            final String long_val=mlong.getText().toString().trim();
            final String Amen_val=mAmenities.getText().toString().trim();
            if (!TextUtils.isEmpty(title_val)&& !TextUtils.isEmpty(Desc_val) && mImageUri !=null);

            {
                final StorageReference filepath=mStorage.child("Comp_Images").child(mImageUri.getLastPathSegment());

                UploadTask uploadTask=filepath.putFile(mImageUri);

                Task<Uri> urlTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){

                            downloadUri=task.getResult();
                            DatabaseReference newPost=mDatabase.push();
                            newPost.child("computername").setValue(title_val);
                            newPost.child("compDesc").setValue(Desc_val);
                            newPost.child("checkin").setValue(checkin_val);
                            newPost.child("location").setValue(location_val);
                            newPost.child("Lat").setValue(lat_val);
                            newPost.child("Long").setValue(long_val);
                            newPost.child("amenities").setValue(Amen_val);
                            newPost.child("checkout").setValue(checkout_val);
                            newPost.child("compImage").setValue(downloadUri.toString());
                            mProgresss.dismiss();
                            startActivity(new Intent(CompPosts.this,Computer.class));

                        }
                    }
                });
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
                mImageUri=data.getData();
                MImageSelect.setImageURI(mImageUri);

            }
        }
    }