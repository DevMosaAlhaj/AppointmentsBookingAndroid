package com.mosaalhaj.appointmentsbooking.Models;

import com.google.gson.annotations.SerializedName;

public class UserLogin {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password ;


    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
