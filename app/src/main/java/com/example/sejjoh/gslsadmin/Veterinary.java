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
import com.example.sejjoh.gslsadmin.models.VeterinaryModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Veterinary extends AppCompatActivity {
    private RecyclerView mBloglist;

    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinary);


            mBloglist=(RecyclerView)findViewById(R.id.blog_list);
            mBloglist.setHasFixedSize(true);
            mBloglist.setLayoutManager(new LinearLayoutManager(this));

            Query query = FirebaseDatabase.getInstance()
                    .getReference().child("Veterinary");

            FirebaseRecyclerOptions<VeterinaryModel> options = new FirebaseRecyclerOptions.Builder<VeterinaryModel>()
                    .setQuery(query, VeterinaryModel.class)
                    .build();

            adapter = new FirebaseRecyclerAdapter<VeterinaryModel, Veterinary.VetVIewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull Veterinary.VetVIewHolder holder, int position, @NonNull VeterinaryModel model) {

                    holder.post_Desc.setText(model.getVetDesc());
                    holder.post_name.setText(model.getVetImage());

                    Glide.with(getBaseContext())
                            .load(model.getVetImage())
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
                public Veterinary.VetVIewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.airport_row, parent, false);

                    return new Veterinary.VetVIewHolder(view);
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

        public static class VetVIewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView post_name;
            public TextView post_Desc;
            public ImageView imageView;

            private ItemClickListener itemClickListener;

            public VetVIewHolder(@NonNull View itemView) {
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
                startActivity(new Intent(Veterinary.this,VetPosts.class));
            }
            return super.onOptionsItemSelected(item);
        }
    }
