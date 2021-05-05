package com.mosaalhaj.appointmentsbooking.APIs;

import com.mosaalhaj.appointmentsbooking.Models.Appointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AppointmentApiService {


    @Headers("Content-type: application/json; charset=utf-8")
    @POST("Appointment/GetUserAppointments/")
    Call<List<Appointment>> getUserAppointments (@Body String userId);

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("Appointment/CreateAppointment/")
    Call<Boolean> createAppointment (@Body Appointment appointment);

}
