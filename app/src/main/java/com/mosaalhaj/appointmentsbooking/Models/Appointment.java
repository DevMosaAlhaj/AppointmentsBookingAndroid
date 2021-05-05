package com.mosaalhaj.appointmentsbooking.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Appointment {

    @SerializedName("id")
    private int id ;
    @SerializedName("campanyName")
    private String companyName;
    @SerializedName("campanyId")
    private int companyId;
    @SerializedName("userId")
    private String userId ;
    @SerializedName("userName")
    private String userName ;
    @SerializedName("bookingDateTime")
    private Date bookingDate ;
    @SerializedName("description")
    private String description ;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
