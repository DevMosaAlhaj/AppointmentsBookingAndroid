package com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mosaalhaj.appointmentsbooking.Items.Constants;
import com.mosaalhaj.appointmentsbooking.R;

import static com.mosaalhaj.appointmentsbooking.Items.Constants.CASHING;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_SHARED_PREFERENCE_FILE;


public class SettingsFragment extends Fragment {

    private Switch sw_cashing;
    private SharedPreferences preferences;
    private Editor editor;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sw_cashing = view.findViewById(R.id.fragment_settings_sw_cashing);

        if (getActivity() != null)
            preferences = getActivity().getSharedPreferences(USER_SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);


        return view;

    }


    @SuppressLint("ApplySharedPref")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        boolean cashingEnabled = preferences.getBoolean(CASHING,false);
        sw_cashing.setChecked(cashingEnabled);

        sw_cashing.setOnCheckedChangeListener((compoundButton, b) -> {

            editor = preferences.edit();
            editor.putBoolean(CASHING,b);
            editor.commit();

        });

    }
}