package com.example.sejjoh.gslsadmin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.example.sejjoh.gslsadmin.Chat.StartChat;

public class MainActivity extends AppCompatActivity {
    private CardView mhospital;
    private CardView mAirport;
    private CardView mPicnics;
    private CardView mMechanic;
    private CardView mBanking;
    private CardView mHotel;
    private  CardView cardGym;
    private  CardView cardLaundry;
    private CardView cardveterinary;
    private CardView cardComp;
    private  CardView cardChatBot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mhospital=(CardView)findViewById(R.id.hospital);
        mhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hos=new Intent(MainActivity.this,Hospital.class);
                startActivity(hos);
            }
        });
        mAirport=(CardView)findViewById(R.id.airport);
        mAirport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent air=new Intent(MainActivity.this,Airport.class);
                startActivity(air);
            }
        });
        mPicnics=(CardView)findViewById(R.id.picnic);
        mPicnics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic=new Intent(MainActivity.this,Picnic.class);
                startActivity(pic);
            }
        });
        mBanking=(CardView)findViewById(R.id.banking);
        mBanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bank=new Intent(MainActivity.this,Banking.class);
                startActivity(bank);
            }
        });
        mHotel=(CardView)findViewById(R.id.hotel);
        mHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotel =new Intent(MainActivity.this,Hotel.class);
                startActivity(hotel);
            }
        });
        mMechanic=(CardView)findViewById(R.id.mechanic);
        mMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mechanic=new Intent(MainActivity.this,Mechanic.class);
                startActivity(mechanic);
            }
        });
        cardGym=(CardView)findViewById(R.id.Gym);
        cardGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gymIntent= new Intent(MainActivity.this,Gym.class);
                startActivity(gymIntent);
            }
        });
        cardLaundry=(CardView)findViewById(R.id.Laundry);
        cardLaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LaundryIntent= new Intent(MainActivity.this,Laundry.class);
                startActivity(LaundryIntent);
            }
        });
        cardveterinary=(CardView)findViewById(R.id.Veterinary);
        cardveterinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vetIntent=new Intent(MainActivity.this,Veterinary.class);
                startActivity(vetIntent);
            }
        });
        cardComp=(CardView)findViewById(R.id.computer);
        cardComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compIntent= new Intent(MainActivity.this,Computer.class);
                startActivity(compIntent);
            }
        });
        cardChatBot=(CardView)findViewById(R.id.response);
        cardChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent=new Intent(MainActivity.this, StartChat.class);
                startActivity(chatIntent);
            }
        });





    }
}
