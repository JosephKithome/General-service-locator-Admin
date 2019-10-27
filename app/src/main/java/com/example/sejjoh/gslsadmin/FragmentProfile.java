package com.example.sejjoh.gslsadmin;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sejjoh.gslsadmin.models.UserModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class FragmentProfile extends Fragment {
    CircleImageView image_profile;
    TextView username;
    DatabaseReference reference;
    FirebaseUser fire_user;
    TextView email;
    StorageReference mStorage;
    private  static  final  int IMAGE_REQUEST=1;
    private Uri imageUri;
    private StorageTask uploadTask;


    int color;

    @SuppressLint("ValidFragment")
    public FragmentProfile(int color) {
        this.color = color;
    }

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        image_profile=view.findViewById(R.id.profile_image);
        username=view.findViewById(R.id.username);
        mStorage= FirebaseStorage.getInstance().getReference("Uploads");
        email=view.findViewById(R.id.email);
        fire_user=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(fire_user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel=dataSnapshot.getValue(UserModel.class);
                username.setText(userModel.getUsername());
                email.setText(userModel.getEmail());

                if (userModel.getImageUrl().equals("default")){
                    image_profile.setImageResource(R.drawable.person);
                }else{
                    Glide.with(getContext()).load(userModel.getImageUrl()).into(image_profile);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
        return  view;
    }

    private void openImage() {
        Intent galleryIntent=new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent,IMAGE_REQUEST);
    }
    private  String getFileExtension(Uri uri){
        ContentResolver contentResolver= getContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }
        private  void  uploadImage(){
        final ProgressDialog  pd= new ProgressDialog(getContext());
        pd.setMessage("Relax we are uploading your Image");
        pd.show();
        if (imageUri !=null){
            final  StorageReference fileReference= mStorage.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));
            uploadTask=fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {                @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw  task.getException();
                }
                return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task <Uri>task) {
                    if (task.isSuccessful()){
                        Uri downloadUri= task.getResult();
                    String mUri=downloadUri.toString();
                    reference=FirebaseDatabase.getInstance().getReference("Users").child(fire_user.getUid());
                        HashMap<String,Object>map=new HashMap<>();
                        map.put("imageUrl",mUri);
                        reference.updateChildren(map);
                        pd.dismiss();
                    }else {
                        Toast.makeText(getContext(), "failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    
                }
            });

        }
        else
        {
            Toast.makeText(getContext(), "No image selected...", Toast.LENGTH_SHORT).show();
        }
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== IMAGE_REQUEST && resultCode== RESULT_OK
                && data !=null  && data.getData() !=null){
            imageUri=data.getData();
            if (uploadTask !=null && uploadTask.isInProgress()){
                Toast.makeText(getContext(), "upload in Progress...", Toast.LENGTH_SHORT).show();
            }
            else{
                uploadImage();
            }

        }
    }
}


