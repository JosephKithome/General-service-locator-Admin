package com.example.sejjoh.gslsadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sejjoh.gslsadmin.R;
import com.example.sejjoh.gslsadmin.models.ChatModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private  static final int MSG_TYPE_LEFT=0;
    private  static final int MSG_TYPE_RIGHT=1;
    FirebaseUser fire_user;
    private Context mContext;
    private List<ChatModel> mChat;
    private String imageurl;
public MessageAdapter(Context mContext, List<ChatModel>mChat, String imageurl){
        this.mChat=mChat;
        this.mContext=mContext;
        this.imageurl=imageurl;

        }

@NonNull
@Override
public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == MSG_TYPE_RIGHT) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }else {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }
    }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ChatModel chatModel=mChat.get(position);
    holder.show_message.setText(chatModel.getMessage());
    if (imageurl.equals("default")){
        holder.circleImageView.setImageResource(R.drawable.person);

    }else {
        Glide.with(mContext).load(imageurl).into(holder.circleImageView);

    }
    if (position==mChat.size()-1){
        if (chatModel.isIsseen()){
            holder.txt_seen.setText("seen");

        }else {
            holder.txt_seen.setText("Delivered");
        }
    }else {
        holder.txt_seen.setVisibility(View.GONE);
    }

        }

@Override
public int getItemCount() {
        return mChat.size();
        }

public  class ViewHolder extends  RecyclerView.ViewHolder{
    public TextView show_message;
    public TextView email;
    public CircleImageView circleImageView;

    public TextView txt_seen;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        show_message= itemView.findViewById(R.id.show_message);
        email= itemView.findViewById(R.id.email);
        circleImageView= itemView.findViewById(R.id.profile_image);
        txt_seen=itemView.findViewById(R.id.txt_seen);


    }
}

    @Override
    public int getItemViewType(int position) {
        fire_user= FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fire_user.getUid())){
            return MSG_TYPE_RIGHT;

        }
        else {
            return  MSG_TYPE_LEFT;

        }
    }
}