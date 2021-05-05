package com.mosaalhaj.appointmentsbooking.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments.AddAppointmentFragment;
import com.mosaalhaj.appointmentsbooking.R;

import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_ID;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_NAME;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_ID;

public class AppointmentActivity extends AppCompatActivity {


    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Bundle bundle =  getIntent().getExtras();

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();


        Fragment fragment = new AddAppointmentFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.appointment_contanair, fragment);
        transaction.commit();

    }
}