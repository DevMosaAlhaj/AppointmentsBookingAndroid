package com.mosaalhaj.appointmentsbooking.Databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mosaalhaj.appointmentsbooking.Models.Address;
import com.mosaalhaj.appointmentsbooking.Models.Appointment;
import com.mosaalhaj.appointmentsbooking.Models.Company;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Date;

import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_NAME_NOT_FOUND;

@SuppressLint("Recycle")
public class MyCashingDatabase extends SQLiteAssetHelper {

    private static final String COMPANY_TABLE_NAME = "Company";
    private static final String DATABASE_NAME = "appointmentBooking.db";
    private static final int DATABASE_VERSION = 1;
    private static final String APPOINTMENT_TABLE_NAME = "Address";
    @SuppressLint("StaticFieldLeak")
    private static MyCashingDatabase DATABASE;


    private MyCashingDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static MyCashingDatabase getInstance(Context context) {
        if (DATABASE == null)
            DATABASE = new MyCashingDatabase(context);
        return DATABASE;
    }


    public void insertCompanies(ArrayList<Company> companies) {

        SQLiteDatabase database = getWritableDatabase();

        for (Company company : companies) {

            insertAddress(company.getAddress(), company.getId());
            ContentValues values = new ContentValues();
            values.put("name", company.getName());
            values.put("description", company.getDescription());
            values.put("email", company.getEmail());
            values.put("phone", company.getPhoneNumber());
            values.put("website", company.getWebsite());
            values.put("workStart", company.getWorkStart().getTime());
            values.put("workEnd", company.getWorkEnd().getTime());

            database.insert(COMPANY_TABLE_NAME, null, values);

        }


    }

    public ArrayList<Company> getCompanies() {

        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Company> companies = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + COMPANY_TABLE_NAME + ";", null);

        if (cursor.moveToFirst()) {

            do {

                String name, description, email, phone, website;
                int id;
                long workStart, workEnd;

                id = cursor.getInt(0);

                name = cursor.getString(1);
                description = cursor.getString(2);
                email = cursor.getString(3);
                phone = cursor.getString(4);
                website = cursor.getString(5);
                workStart = cursor.getLong(6);
                workEnd = cursor.getLong(7);
                Address address = getAddress(id);

                Company company = new Company(id, name, description, address, phone, email, website, new Date(workStart), new Date(workEnd));
                companies.add(company);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return companies;
    }

    public Address getAddress(int id) {
        SQLiteDatabase database = getReadableDatabase();
        Address address;
        Cursor cursor = database.rawQuery("SELECT * FROM " + APPOINTMENT_TABLE_NAME + " WHERE id == " + id + " ;", null);

        if (cursor.moveToFirst()) {
            String country, city, street, latitude, longitude;

            country = cursor.getString(1);
            city = cursor.getString(2);
            street = cursor.getString(3);
            latitude = cursor.getString(4);
            longitude = cursor.getString(5);

            address = new Address(country, city, street, longitude, latitude);
            cursor.close();
            return address;
        }

        cursor.close();

        return null;

    }

    public String getCompanyName(int id) {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + COMPANY_TABLE_NAME + " WHERE id == " + id + " ;", null);

        if (cursor.moveToFirst()) {


            String name = cursor.getString(1);

            return name;

        }


        cursor.close();
        return COMPANY_NAME_NOT_FOUND;

    }

    public void insertAppointment(ArrayList<Appointment> appointments) {

        SQLiteDatabase database = getWritableDatabase();

        for (Appointment appointment : appointments) {


            ContentValues values = new ContentValues();
            values.put("id", appointment.getId());
            values.put("userId", appointment.getUserId());
            values.put("companyId", appointment.getCompanyId());
            values.put("bookingDate", appointment.getBookingDate().getTime());
            values.put("description", appointment.getDescription());

            database.insert("Appointment", null, values);

        }


    }

    public ArrayList<Appointment> getAppointments() {

        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Appointment> appointments = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + APPOINTMENT_TABLE_NAME + ";", null);

        if (cursor.moveToFirst()) {
            do {

                int id, companyId;
                String userId, description;

                id = cursor.getInt(0);
                userId = cursor.getString(1);
                companyId = cursor.getInt(2);
                long bookingDate = cursor.getLong(3);
                description = cursor.getString(4);

                Appointment appointment = new Appointment();
                appointment.setId(id);
                appointment.setUserId(userId);
                appointment.setCompanyId(companyId);
                appointment.setCompanyName(getCompanyName(companyId));
                appointment.setBookingDate(new Date(bookingDate));
                appointment.setDescription(description);

                appointments.add(appointment);


            } while (cursor.moveToNext());
        }

        cursor.close();

        return appointments;
    }


    public void insertAddress(Address address, int companyId) {

        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id", companyId);
        values.put("country", address.getCountryName());
        values.put("city", address.getCityName());
        values.put("street", address.getStreetName());
        values.put("long", address.getLongitude());
        values.put("lat", address.getLatitude());

        database.insert(APPOINTMENT_TABLE_NAME, null, values);
    }


}
