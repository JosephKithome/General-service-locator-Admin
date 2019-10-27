package com.example.sejjoh.gslsadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sejjoh.gslsadmin.adapters.MessageAdapter;
import com.example.sejjoh.gslsadmin.models.ChatModel;
import com.example.sejjoh.gslsadmin.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Message extends AppCompatActivity {
    private TextView username;
    private CircleImageView profile_image;
    private  TextView email_user;

    private FirebaseUser fire_user;
    DatabaseReference reference;
    Intent intent;
    ValueEventListener seenListener;
    ImageButton btn_send;
    EditText text_send;
    private List<ChatModel> mChat;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        recyclerView= findViewById(R.id.MessageRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(Message.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        profile_image=findViewById(R.id.profile);
        username= findViewById(R.id.username);
        btn_send= findViewById(R.id.btn_send);
        text_send= findViewById(R.id.text_send);

        email_user= findViewById(R.id.email);
        intent=getIntent();
        final String userid=intent.getStringExtra("userid");
        fire_user= FirebaseAuth.getInstance().getCurrentUser();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg= text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(fire_user.getUid(),userid,msg);
                }else{
                    Toast.makeText(Message.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });
        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel=dataSnapshot.getValue(UserModel.class);
                username.setText(userModel.getUsername());
                if (userModel.getImageUrl().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                }else{
                    Glide.with(getApplicationContext()).load(userModel.getImageUrl()).into(profile_image);

                }
                readMessage(fire_user.getUid(),userid,userModel.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        seenMessage(userid);

    }
    private  void  seenMessage(final String userid){
        reference= FirebaseDatabase.getInstance().getReference("Chats");
        seenListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ChatModel chat=snapshot.getValue(ChatModel.class);
                    if (chat.getReceiver().equals(fire_user.getUid())&& chat.getSender().equals(userid)){
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("isseen",true);
                        snapshot.getRef().updateChildren(hashMap);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendMessage(String sender, String receiver,String message){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen",false);
        reference.child("Chats").push().setValue(hashMap);


    }
    private  void readMessage(final String myid, final String userid, final String imageurl){
        mChat=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ChatModel chatModel=snapshot.getValue(ChatModel.class);
                    if (chatModel.getReceiver().equals(myid)&& chatModel.getSender().equals(userid)||
                            chatModel.getReceiver().equals(userid)&& chatModel.getSender().equals(myid)){
                        mChat.add(chatModel);

                    }
                    messageAdapter=new MessageAdapter(Message.this
                    ,mChat,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private  void  status(String status){
        reference= FirebaseDatabase.getInstance().getReference("Users").child(fire_user.getUid());
        HashMap<String,Object> hashMap= new HashMap<>();
        hashMap.put("status",status);
        reference.updateChildren(hashMap);

    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        status("offline");
    }
}

