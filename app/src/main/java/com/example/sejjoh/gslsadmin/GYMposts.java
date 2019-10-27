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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class GYMposts extends AppCompatActivity {
    private ImageView MImageSelect;
    private EditText MAirportName;
    private EditText mDesc;
    private Button Msubmit;
    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;
    Uri downloadUri;
    private StorageReference mStorage;
    private ProgressDialog mProgresss;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymposts);
        mStorage= FirebaseStorage.getInstance().getReference();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Gym");
        MImageSelect = (ImageView) findViewById(R.id.imageview);
        Msubmit = (Button) findViewById(R.id.Msubmit);
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
        mProgresss.setMessage("please wait Posting to Gym...");
        mProgresss.show();
        final String title_val=MAirportName.getText().toString().trim();
        final String Desc_val=mDesc.getText().toString().trim();
        if (!TextUtils.isEmpty(title_val)&& !TextUtils.isEmpty(Desc_val) && mImageUri !=null);

        {
            final StorageReference filepath=mStorage.child("Gym_Images").child(mImageUri.getLastPathSegment());

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
                        newPost.child("gymName").setValue(title_val);
                        newPost.child("gymdescription").setValue(Desc_val);
                        newPost.child("gymimage").setValue(downloadUri.toString());
                        mProgresss.dismiss();
                        startActivity(new Intent(GYMposts.this,Gym.class));

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

