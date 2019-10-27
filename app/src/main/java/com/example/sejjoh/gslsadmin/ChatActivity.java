package com.example.sejjoh.gslsadmin;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class ChatActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        //create and set ViewPager adapter
        setupViewPager(viewPager);
        tablayout.setupWithViewPager(viewPager);

        //change selected tab when viewpager changed page
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        //change viewpager page when tab selected
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentChats(
                ContextCompat.getColor(this, R.color.white)), "Chats");
        adapter.addFrag(new FragmentContacts(
                ContextCompat.getColor(this, R.color.white)), "Contacts");
       adapter.addFrag(new  FragmentProfile(
               ContextCompat.getColor(this,R.color.white)),"Profile");
        viewPager.setAdapter(adapter);

    }

}