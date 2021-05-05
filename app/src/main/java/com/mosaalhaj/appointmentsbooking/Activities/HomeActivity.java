package com.mosaalhaj.appointmentsbooking.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.mosaalhaj.appointmentsbooking.Adapters.MyPagerAdapter;
import com.mosaalhaj.appointmentsbooking.R;

public class HomeActivity extends AppCompatActivity {

    private TabLayout myTabLayout;
    private ViewPager myViewPager;
    private MyPagerAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();

        adapter = new MyPagerAdapter(getSupportFragmentManager());

        myViewPager.setAdapter(adapter);
        myTabLayout.setupWithViewPager(myViewPager);

        initTabsIcons();


    }

    private void initTabsIcons() {
        myTabLayout.getTabAt(0).setIcon(R.drawable.appointment_tab_icon);
        myTabLayout.getTabAt(1).setIcon(R.drawable.explore_tab_icon);
        myTabLayout.getTabAt(2).setIcon(R.drawable.settings_tab_icon);

        myTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void initView() {

        myTabLayout = findViewById(R.id.home_my_tab_layout);
        myViewPager = findViewById(R.id.home_my_view_pager);
    }


}