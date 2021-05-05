package com.mosaalhaj.appointmentsbooking.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    @SerializedName("countryName")
    private String countryName;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("streetName")
    private String streetName;
    @SerializedName("longtitude")
    private String longitude;
    @SerializedName("latitude")
    private String latitude ;

    public Address(String countryName, String cityName, String streetName, String longitude, String latitude) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.streetName = streetName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected Address(Parcel in) {
        countryName = in.readString();
        cityName = in.readString();
        streetName = in.readString();
        longitude = in.readString();
        latitude = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString (){
        return "User{" +
                "countryName='" + countryName + '\'' +
                "cityName='" + cityName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(countryName);
        parcel.writeString(cityName);
        parcel.writeString(streetName);
        parcel.writeString(longitude);
        parcel.writeString(latitude);
    }
}
