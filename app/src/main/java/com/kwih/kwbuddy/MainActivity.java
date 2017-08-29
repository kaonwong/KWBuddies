package com.kwih.kwbuddy;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content_container);

        tabHost.addTab(tabHost.newTabSpec("News")
                        .setIndicator("News"),
                NewsFragment.class,
                null);

        tabHost.addTab(tabHost.newTabSpec("Album")
                        .setIndicator("Album"),
                AlbumFragment.class,
                null);
//
//        tabHost.addTab(tabHost.newTabSpec("Home")
//                        .setIndicator("Home"),
//                HomeFragment.class,
//                null);

        tabHost.addTab(tabHost.newTabSpec("Event")
                        .setIndicator("Event"),
                EventFragment.class,
                null);

        tabHost.addTab(tabHost.newTabSpec("Setting")
                        .setIndicator("Setting"),
                SettingFragment.class,
                null);
    }
}
