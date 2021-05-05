package com.mosaalhaj.appointmentsbooking.Fragments.RegisterFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.mosaalhaj.appointmentsbooking.APIs.UserApiService;
import com.mosaalhaj.appointmentsbooking.Activities.LoginActivity;
import com.mosaalhaj.appointmentsbooking.Items.Constants;
import com.mosaalhaj.appointmentsbooking.Models.Address;
import com.mosaalhaj.appointmentsbooking.Models.User;
import com.mosaalhaj.appointmentsbooking.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.mosaalhaj.appointmentsbooking.APIs.RetrofitSingleton.getRetrofit;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.PASSWORD;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_NAME;


public class RegisterTwoFragment extends Fragment {


    private Spinner sp_gender;
    private Spinner sp_country;
    private TextInputEditText et_city, et_street, et_password;
    private AppCompatButton bt_register;
    private String[] countryList;
    private boolean countryValid, cityValid, streetValid, passwordValid;
    private Bundle bundle;
    private Retrofit retrofit;
    private String apiUrl;

    public RegisterTwoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_register_two, null);

        sp_country = view.findViewById(R.id.fragment_register_tow_sp_countries);
        sp_gender = view.findViewById(R.id.fragment_register_tow_sp_gender);

        et_city = view.findViewById(R.id.fragment_register_tow_et_city);
        et_street = view.findViewById(R.id.fragment_register_tow_et_street);
        et_password = view.findViewById(R.id.fragment_register_tow_et_password);

        bt_register = view.findViewById(R.id.fragment_register_tow_bt_register);

        countryValid = false;
        cityValid = false;
        streetValid = false;
        passwordValid = false;

        countryList = Constants.getCountersList();

        if (getArguments() != null) {
            bundle = getArguments();
        }

        apiUrl = Constants.getApiUrl();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofit = getRetrofit();

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, countryList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;

                return super.isEnabled(position);
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                if (position == 0 && convertView != null)
                    convertView.setBackgroundColor(Color.GRAY);

                return super.getView(position, convertView, parent);
            }
        };

        sp_country.setAdapter(countryAdapter);

        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                    countryValid = true;

                buttonConfirm();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                countryValid = false;
            }
        });

        et_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence city, int i, int i1, int i2) {

                if (city.length() < 3) {
                    et_city.setError("City is too Short");
                    cityValid = false;
                } else if (city.length() > 15) {
                    et_city.setError("City is too Long");
                    cityValid = false;
                } else
                    cityValid = true;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        et_street.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence city, int i, int i1, int i2) {

                if (city.length() < 8) {
                    et_street.setError("Street Address is too Short");
                    streetValid = false;
                } else if (city.length() > 25) {
                    et_street.setError("Street Address is too Long");
                    streetValid = false;
                } else
                    streetValid = true;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String passwordErrorMessage = "Password Must Be\n";
                String password = et_password.getText().toString();

                if (!password.isEmpty() && password.length() >= 8) {


                    if (password.matches("(.*[0-9].*)")) {

                        if (password.matches("(.*[A-Z].*)")) {

                            if (password.matches("^(?=.*[_.@#$%^&+=()]).*$"))
                                passwordValid = true;
                            else {
                                passwordErrorMessage += Constants.PASSWORD_NO_SPECIAL_CHARACTERS_ERROR;
                                passwordValid = false;
                            }

                        } else {

                            passwordErrorMessage += Constants.PASSWORD_NO_UPPERCASE_CHARACTERS_ERROR;
                            passwordValid = false;

                        }

                    } else {
                        passwordErrorMessage += Constants.PASSWORD_NO_NUMBER_ERROR;
                        passwordValid = false;

                    }


                } else {
                    passwordErrorMessage += Constants.PASSWORD_SHORT_CHARACTERS_ERROR;
                    passwordValid = false;
                }

                if (!passwordValid)
                    et_password.setError(passwordErrorMessage);


            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        bt_register.setOnClickListener(listener -> {

            bt_register.setEnabled(false);
            bt_register.setBackgroundResource(R.drawable.app_button_background_disabled);

            String city, street, firstName, lastName, phone, email, country, password;

            city = et_city.getText().toString();
            street = et_street.getText().toString();
            password = et_password.getText().toString();

            if (!city.isEmpty() && !street.isEmpty() && countryValid) {

                firstName = bundle.getString(RegisterOneFragment.FIRST_NAME);
                lastName = bundle.getString(RegisterOneFragment.LAST_NAME);
                phone = bundle.getString(RegisterOneFragment.PHONE);
                email = bundle.getString(RegisterOneFragment.EMAIL);
                country = countryList[sp_country.getSelectedItemPosition()];

                createUser(city, street, firstName, lastName, phone, email, country, password);

            }

        });


        // ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$


    }

    private synchronized void createUser(String city, String street, String firstName, String lastName, String phone, String email, String country, String password) {

        User user = new User();
        Address userAddress = new Address(country,city,street,"","");

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(userAddress);
        user.setGender(sp_gender.getSelectedItemPosition());
        user.setPassword(password);

        UserApiService apiService = retrofit.create(UserApiService.class);
        Call<Boolean> createUserCall = apiService.createUser(
                user
        );
        createUserCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                bt_register.setEnabled(true);
                bt_register.setBackgroundResource(R.drawable.app_button_background);

                boolean isRegisterSuccess = response.body();

                if (isRegisterSuccess) {
                    Toast.makeText(getContext(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(getContext(), LoginActivity.class);
                    loginIntent.putExtra(USER_NAME, user.getEmail());
                    loginIntent.putExtra(PASSWORD, user.getPassword());
                    startActivity(loginIntent);
                    getActivity().finish();

                } else
                    Toast.makeText(getContext(), "User Created Failure", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

                bt_register.setEnabled(true);
                bt_register.setBackgroundResource(R.drawable.app_button_background);

                Toast.makeText(getContext(), "Error When Create Your Account\nPlease Try Again Later", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void buttonConfirm() {
        if (countryValid && cityValid && streetValid && passwordValid) {
            bt_register.setEnabled(true);
            bt_register.setBackgroundResource(R.drawable.app_button_background);
        } else if (bt_register.isEnabled()) {
            bt_register.setEnabled(false);
            bt_register.setBackgroundResource(R.drawable.app_button_background_disabled);
        }
    }


}