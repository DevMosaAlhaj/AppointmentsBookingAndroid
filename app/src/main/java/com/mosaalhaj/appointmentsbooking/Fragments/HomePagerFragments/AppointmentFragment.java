package com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mosaalhaj.appointmentsbooking.APIs.AppointmentApiService;
import com.mosaalhaj.appointmentsbooking.Activities.AppointmentActivity;
import com.mosaalhaj.appointmentsbooking.Models.Appointment;
import com.mosaalhaj.appointmentsbooking.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.mosaalhaj.appointmentsbooking.APIs.RetrofitSingleton.getRetrofit;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_ID;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_SHARED_PREFERENCE_FILE;


public class AppointmentFragment extends Fragment {


    private CompactCalendarView calendarView;
    private TextView tv_calendarDate;
    private SimpleDateFormat dateFormat;
    private ImageView iv_next, iv_before;
    private Retrofit retrofit;
    private List<Appointment> appointments;
    private SharedPreferences preferences;
    private Date currentDate ;
    private FloatingActionButton fab_addAppointment;


    public AppointmentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        calendarView = view.findViewById(R.id.fragment_appointment_calendar_view);
        tv_calendarDate = view.findViewById(R.id.fragment_appointment_tv_calendar_date);

        iv_before = view.findViewById(R.id.fragment_appointment_im_before);
        iv_next = view.findViewById(R.id.fragment_appointment_im_next);

        fab_addAppointment = view.findViewById(R.id.fragment_appointment_fab_add_appointment);


        return view;
    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        currentDate = Calendar.getInstance().getTime();

        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        calendarView.setCurrentDate(currentDate);

        dateFormat = new SimpleDateFormat("yyyy MMM");
        tv_calendarDate.setText(dateFormat.format(currentDate));

        getUserAppointments();


        fab_addAppointment.setOnClickListener(listener -> {

            Intent intent = new Intent(getContext(), AppointmentActivity.class);
            if (getContext() != null)
                getContext().startActivity(intent);

        });


        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tv_calendarDate.setText(dateFormat.format(firstDayOfNewMonth));
            }
        });

        iv_next.setOnClickListener(lis -> {
            calendarView.scrollRight();
        });

        iv_before.setOnClickListener(lis -> {
            calendarView.scrollLeft();
        });

    }


    private void getUserAppointments() {

        preferences = getContext().getSharedPreferences(USER_SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
        String userId = preferences.getString(USER_ID, null);
        retrofit = getRetrofit();


        AppointmentApiService appointmentService = retrofit.create(AppointmentApiService.class);
        Call<List<Appointment>> userAppointmentsCall = appointmentService.getUserAppointments(userId);

        userAppointmentsCall.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {

                appointments = response.body();


                if (appointments != null && !appointments.isEmpty()) {

                    List<Event> events = new ArrayList<>();

                    for (Appointment appointment : appointments) {
                        Event event ;
                        if (currentDate.compareTo(appointment.getBookingDate()) > 0)
                            // That's mean is The Current Date after Booking Date
                             event = new Event(Color.DKGRAY, appointment.getBookingDate().getTime(), appointment);
                        else
                             event = new Event(Color.WHITE, appointment.getBookingDate().getTime(), appointment);


                        events.add(event);
                    }
                    calendarView.addEvents(events);

                }

            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
                Log.e("OnFailure", t.getMessage());
            }
        });
    }

}