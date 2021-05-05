package com.mosaalhaj.appointmentsbooking.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments.AppointmentFragment;
import com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments.ExploreFragment;
import com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments.SettingsFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {


    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment ;


        switch (position){

            case 1 :
                fragment = new ExploreFragment();
                break;


            case 2 :
                fragment = new SettingsFragment();
                break;

            default:
                fragment = new AppointmentFragment();
                break;
        }

        return fragment;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ;


        switch (position){

            case 1 :
                title = "Explore";
                break;


            case 2 :
                title = "Settings";
                break;

            default:
                title = "Appointment";
                break;
        }

        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
