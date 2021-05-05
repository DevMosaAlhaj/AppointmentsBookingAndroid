package com.mosaalhaj.appointmentsbooking.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.mosaalhaj.appointmentsbooking.Fragments.RegisterFragments.RegisterOneFragment;
import com.mosaalhaj.appointmentsbooking.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Fragment registerOneFragment = new RegisterOneFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.register_form_fragments_parent,registerOneFragment);
        transaction.commit();


    }
}