package com.mosaalhaj.appointmentsbooking.Adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments.CompanyFragment;
import com.mosaalhaj.appointmentsbooking.Models.Company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_DESCRIPTION;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_EMAIL;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_ID;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_LATITUDE;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_LONGITUDE;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_NAME;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_PHONE;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.WORK_END;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.WORK_START;

public class CompanyPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Company> companies;

    public CompanyPagerAdapter(@NonNull FragmentManager fm, ArrayList<Company> companies) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.companies = companies;
    }

    @SuppressLint("SimpleDateFormat")
    @NonNull
    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        Company company = companies.get(position);
        Fragment fragment = new CompanyFragment();

        bundle.putParcelable(COMPANY,company);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {

        if (companies != null)
            return companies.size();

        return 0;
    }

}
