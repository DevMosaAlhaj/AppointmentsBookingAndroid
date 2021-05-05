package com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.mosaalhaj.appointmentsbooking.APIs.AppointmentApiService;
import com.mosaalhaj.appointmentsbooking.Activities.HomeActivity;
import com.mosaalhaj.appointmentsbooking.Models.Appointment;
import com.mosaalhaj.appointmentsbooking.Models.Company;
import com.mosaalhaj.appointmentsbooking.R;

import java.text.ChoiceFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.mosaalhaj.appointmentsbooking.APIs.RetrofitSingleton.getRetrofit;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_ID;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_NAME;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_NAME_NOT_FOUND;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.NOT_FOUND;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_ID;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.WORK_END;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.WORK_START;


public class AddAppointmentFragment extends Fragment {

    private TextInputEditText et_date, et_time, et_companyName, et_appointmentDescription;
    private AppCompatButton bt_addAppointment;
    private boolean dateValid, timeValid, descriptionValid;
    private String userId;
    private Date currentDate;
    private Calendar calendar;
    private Company company ;
    private SimpleDateFormat myFormatter;


    public AddAppointmentFragment() {
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_appointment, container, false);

        et_date = view.findViewById(R.id.fragment_add_appointment_et_date);
        et_time = view.findViewById(R.id.fragment_add_appointment_et_time);
        et_companyName = view.findViewById(R.id.fragment_add_appointment_et_company_name);
        et_appointmentDescription = view.findViewById(R.id.fragment_add_appointment_et_description);

        bt_addAppointment = view.findViewById(R.id.fragment_add_appointment_bt_add_appointment);

        Bundle fragmentData = getArguments();

        if (fragmentData != null) {

            company = fragmentData.getParcelable(COMPANY);
            userId = fragmentData.getString(USER_ID);
            et_companyName.setText(company.getName());
        }


        dateValid = false;
        timeValid = false;
        descriptionValid = false;

        myFormatter = new SimpleDateFormat("hh:mm a");

        return view;
    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = Calendar.getInstance();

        currentDate = calendar.getTime();

        int year, month, day, hour, minute;

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);


        et_date.setOnFocusChangeListener((view1, b) -> {
            if (view1.getId() == R.id.fragment_add_appointment_et_date && b) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.MyPickerDialogTheme, (datePicker, i, i1, i2) -> {

                    String date = datePicker.getYear() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth();

                    et_date.setText(date);
                }, year, month, day);

                dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Set Date", dialog);
                dialog.show();
            }
        });

        et_time.setOnFocusChangeListener(((view1, b) -> {

            if (view1.getId() == R.id.fragment_add_appointment_et_time && b) {
                TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.MyPickerDialogTheme, ((timePicker, i, i1) -> {
                    String time = timePicker.getHour() + ":" + timePicker.getMinute();
                    et_time.setText(time);
                }), hour, minute, false);

                dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Set Time", dialog);
                dialog.show();
            }
        }));

        et_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence date, int i, int i1, int i2) {

                if (date != null && !date.toString().isEmpty())
                    dateValid = true;
                else {
                    et_date.setError("Date Can't Be Empty");
                    dateValid = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        et_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence time, int i, int i1, int i2) {

                if (time != null && !time.toString().isEmpty())
                    timeValid = true;
                else {
                    et_time.setError("Time Can't Be Empty");
                    timeValid = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        et_appointmentDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence description, int i, int i1, int i2) {

                if (description.length() >= 10 && description.length() <= 150)
                    descriptionValid = true;
                else if (description.length() > 150) {
                    et_appointmentDescription.setError(getString(R.string.description_long));
                    descriptionValid = false;
                } else {
                    et_appointmentDescription.setError(getString(R.string.description_short));
                    descriptionValid = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        bt_addAppointment.setOnClickListener(listener -> {

            try {
                String time, date;
                time = et_time.getText().toString();
                date = et_date.getText().toString();
                Date bookingDate = new SimpleDateFormat("yyyy/MM/dd hh:mm").parse((date + " " + time));
                Date bookingDateFormat = myFormatter.parse(myFormatter.format(bookingDate));

                if (currentDate.compareTo(bookingDate) < 0) {

                    if (bookingDateFormat.compareTo(company.getWorkEnd()) < 0){

                        if (bookingDateFormat.compareTo(company.getWorkStart()) > 0){

                            bt_addAppointment.setEnabled(false);
                            bt_addAppointment.setBackgroundResource(R.drawable.app_button_background_disabled);

                            Appointment appointment = new Appointment();
                            String description = et_appointmentDescription.getText().toString();
                            appointment.setUserId(userId);
                            appointment.setCompanyId(company.getId());
                            appointment.setDescription(description);
                            appointment.setBookingDate(bookingDate);

                            addAppointment(appointment);
                        } else
                            Toast.makeText(getContext(), "Booking Date Must be After Work Starts", Toast.LENGTH_LONG).show();

                    } else
                        Toast.makeText(getContext(), "Booking Date Must be Before Work Ends", Toast.LENGTH_LONG).show();


                } else
                    Toast.makeText(getContext(), "Booking Date Must Not be Before Now", Toast.LENGTH_LONG).show();


            } catch (ParseException e) {
                e.printStackTrace();
            }


        });

    }

    private void buttonConfirm() {

        if (dateValid && timeValid && descriptionValid) {
            bt_addAppointment.setEnabled(true);
            bt_addAppointment.setBackgroundResource(R.drawable.app_button_background);
        } else {
            bt_addAppointment.setEnabled(false);
            bt_addAppointment.setBackgroundResource(R.drawable.app_button_background_disabled);
        }

    }

    private void addAppointment(Appointment appointment) {

        String companyName = et_companyName.getText().toString();

        if (!companyName.isEmpty() && !companyName.equals(COMPANY_NAME_NOT_FOUND)) {


            Retrofit retrofit = getRetrofit();

            AppointmentApiService service = retrofit.create(AppointmentApiService.class);

            Call<Boolean> createAppointmentCall = service.createAppointment(appointment);

            createAppointmentCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                    bt_addAppointment.setEnabled(true);
                    bt_addAppointment.setBackgroundResource(R.drawable.app_button_background);
                    boolean succeeded = false;

                    if (response.body() != null)
                        succeeded = response.body();

                    if (succeeded) {
                        Toast.makeText(getContext(), "Appointment Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), HomeActivity.class);

                        if (getActivity() != null) {
                            startActivity(intent);
                            getActivity().finish();
                        }
                    } else
                        Toast.makeText(getContext(), "Error When Add Appointment", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    bt_addAppointment.setEnabled(true);
                    bt_addAppointment.setBackgroundResource(R.drawable.app_button_background);
                    Toast.makeText(getContext(), "Can't Access to Server", Toast.LENGTH_SHORT).show();
                }
            });


        } else
            et_companyName.setError(COMPANY_NAME_NOT_FOUND);


    }
}