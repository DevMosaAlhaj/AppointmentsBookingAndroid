package com.mosaalhaj.appointmentsbooking.Models;



import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("gender")
    private int gender;
    @SerializedName("phone")
    private String phone;
    @SerializedName("password")
    private String password;
    @SerializedName("address")
    private Address address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {

        if (id != null)
            return "";

        return "{" +
                "firstName='" + firstName + '\'' +
                "lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", password=" + phone +
                ", address=" + address.toString() +
                '}';
    }

}
