package com.mosaalhaj.appointmentsbooking.APIs;

import com.mosaalhaj.appointmentsbooking.Models.Company;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CompanyApiService {


    @GET("Campany/GetAllCampanies/")
    Call<ArrayList<Company>> getAllCompanies();


    @Headers("Content-type: application/json; charset=utf-8")
    @POST("Campany/CampanySearch/")
    Call<ArrayList<Company>> companySearch(@Body String query);

}
