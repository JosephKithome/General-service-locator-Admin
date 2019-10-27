package com.example.sejjoh.gslsadmin;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sejjoh.gslsadmin.Interface.ItemClickListener;
import com.example.sejjoh.gslsadmin.models.GymModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Gym extends AppCompatActivity {
    private RecyclerView mBloglist;

    FirebaseRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        mBloglist=(RecyclerView)findViewById(R.id.blog_list);
        mBloglist.setHasFixedSize(true);
        mBloglist.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseDatabase.getInstance()
                .getReference().child("Gym");

        FirebaseRecyclerOptions<GymModel> options = new FirebaseRecyclerOptions.Builder<GymModel>()
                .setQuery(query, GymModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<GymModel, Gym.GymVIewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Gym.GymVIewHolder holder, int position, @NonNull GymModel model) {

                holder.post_Desc.setText(model.getGymdescription());
                holder.post_name.setText(model.getGymimage());

                Glide.with(getBaseContext())
                        .load(model.getGymimage())
                        .into(holder.imageView);



                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

//TODO:do smthng
//                        startActivity(new Intent(Gym.this,AirportDetails.class));

                    }
                });
            }

            @NonNull
            @Override
            public Gym.GymVIewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.airport_row, parent, false);

                return new Gym.GymVIewHolder(view);
            }


        };

        mBloglist.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

    public static class GymVIewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView post_name;
        public TextView post_Desc;
        public ImageView imageView;

        private ItemClickListener itemClickListener;

        public GymVIewHolder(@NonNull View itemView) {
            super(itemView);

            post_name=(TextView)itemView.findViewById(R.id.post_title);
            post_Desc=(TextView)itemView.findViewById(R.id.post_description);
            imageView=itemView.findViewById(R.id.post_image);

            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_add){
            startActivity(new Intent(Gym.this,GYMposts.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
