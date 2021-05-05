package com.mosaalhaj.appointmentsbooking.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Company implements Parcelable {

    @SerializedName("id")
    private int id ;
    @SerializedName("name")
    private String name ;
    @SerializedName("description")
    private String description ;
    @SerializedName("address")
    private Address address ;
    @SerializedName("phoneNumber")
    private String phoneNumber ;
    @SerializedName("email")
    private String email ;
    @SerializedName("website")
    private String website ;
    @SerializedName("workStart")
    private Date workStart ;
    @SerializedName("workEnd")
    private Date workEnd ;

    public Company(int id, String name, String description, Address address, String phoneNumber, String email, String website, Date workStart, Date workEnd) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.workStart = workStart;
        this.workEnd = workEnd;
    }

    protected Company(Parcel in) {
        address = in.readParcelable(Address.class.getClassLoader());
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        website = in.readString();
        workStart = new Date(in.readLong());
        workEnd = new Date(in.readLong());
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getWorkStart() {
        return workStart;
    }

    public void setWorkStart(Date workStart) {
        this.workStart = workStart;
    }

    public Date getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(Date workEnd) {
        this.workEnd = workEnd;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(address,PARCELABLE_WRITE_RETURN_VALUE);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
        parcel.writeString(website);
        parcel.writeLong(workStart.getTime());
        parcel.writeLong(workEnd.getTime());
    }
}
