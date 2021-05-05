package com.mosaalhaj.appointmentsbooking.APIs;

import com.google.gson.GsonBuilder;
import com.mosaalhaj.appointmentsbooking.Items.Settings;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mosaalhaj.appointmentsbooking.Items.Constants.getApiUrl;

public class RetrofitSingleton {

    private static Retrofit RETROFIT;

    private RetrofitSingleton() {}

    public static Retrofit getRetrofit() {
        if (RETROFIT == null)
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(getApiUrl())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))
                    .client(Settings.getOkHttpClient())
                    .build();

        return RETROFIT;
    }

}
