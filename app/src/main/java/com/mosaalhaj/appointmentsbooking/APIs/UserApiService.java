package com.mosaalhaj.appointmentsbooking.APIs;

import com.mosaalhaj.appointmentsbooking.Models.Appointment;
import com.mosaalhaj.appointmentsbooking.Models.User;
import com.mosaalhaj.appointmentsbooking.Models.UserLogin;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApiService {

    @GET("User/GetAllUsers")
    Call<ArrayList<User>> getUsers ();

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("User/CreateUser/")
    Call<Boolean> createUser (
           @Body User user

    );

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("User/SignIn/")
    Call<User> signIn (@Body UserLogin userLogin);



    // Create Method SignOut Required

}
