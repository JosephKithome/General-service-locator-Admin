package com.example.sejjoh.gslsadmin;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sejjoh.gslsadmin.adapters.UserAdapter;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ValidFragment")
public class FragmentChats extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UserModel> mUsers;
    FirebaseUser fire_user;
    DatabaseReference reference;
    private List<String>usersList;

    int color;

    @SuppressLint("ValidFragment")
    public FragmentChats(int color) {
        this.color = color;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_fragment_chats, container, false);
        recyclerView=view.findViewById(R.id.Messagerecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fire_user= FirebaseAuth.getInstance().getCurrentUser();
        usersList= new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ChatModel chatModel=snapshot.getValue(ChatModel.class);
                    if (chatModel.getSender().equals(fire_user.getUid())){
                        usersList.add(chatModel.getReceiver());
                    }
                    if (chatModel.getReceiver().equals(fire_user.getUid())){
                        usersList.add(chatModel.getSender());
                    }

                }
                readChats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    private void readChats(){
        mUsers=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    UserModel userModel=snapshot.getValue(UserModel.class);
                    for (String id:usersList){
                        if (userModel.getId().equals(id)){
                            if (mUsers.size()!=0){
                                for (UserModel userModel1 :mUsers){
                                    if (!userModel.getId().equals(userModel1.getId())){

                                    }
                                }
                                }
                            else{
                                mUsers.add(userModel);
                            }
                        }
                    }
                }
                userAdapter=new UserAdapter(getContext(),mUsers,true);
                recyclerView.setAdapter(userAdapter);
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
    public void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    public void onPause() {
        super.onPause();
        status("offline");
    }
}

