package com.example.sejjoh.gslsadmin.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sejjoh.gslsadmin.ChatActivity;
import com.example.sejjoh.gslsadmin.R;

import androidx.appcompat.app.AppCompatActivity;

public class StartChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_chat);

        ImageView imageView=(ImageView)findViewById(R.id.rotate);
        TextView textView=(TextView)findViewById(R.id.gsls);
        TextView textView1=(TextView)findViewById(R.id.start);


        ImageView image = (ImageView) findViewById(R.id.image);
        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.fade_in_fade_out);
        imageView.startAnimation(hyperspaceJump);
        Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.downanime);
        textView.startAnimation(animation1);
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startHomepage();
                }

            }
        };
        timer.start();
    }

    private void startHomepage() {
        Intent welcome= new Intent(StartChat.this, ChatActivity.class);
        startActivity(welcome);
    }
}
