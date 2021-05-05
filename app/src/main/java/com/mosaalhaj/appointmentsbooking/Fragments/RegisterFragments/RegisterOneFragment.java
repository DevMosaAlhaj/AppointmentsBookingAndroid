package com.mosaalhaj.appointmentsbooking.Fragments.RegisterFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.mosaalhaj.appointmentsbooking.Items.Settings;
import com.mosaalhaj.appointmentsbooking.R;

import static com.mosaalhaj.appointmentsbooking.Items.Constants.EMAIL_NOT_VALID_ERROR;


public class RegisterOneFragment extends Fragment {

    public static String FIRST_NAME = "fistName";
    public static String LAST_NAME = "lastName";
    public static String EMAIL = "email";
    public static String PHONE = "phone";
    private TextInputEditText et_firstName, et_lastName, et_email, et_phone;
    private AppCompatButton bt_next;
    private boolean firstNameValid, lastNameValid, emailValid, phoneValid;

    public RegisterOneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_register_one, null);

        et_firstName = view.findViewById(R.id.fragment_register_one_et_first_name);
        et_lastName = view.findViewById(R.id.fragment_register_one_et_last_name);
        et_email = view.findViewById(R.id.fragment_register_one_et_email);
        et_phone = view.findViewById(R.id.fragment_register_one_et_phone);

        bt_next = view.findViewById(R.id.fragment_register_one_bt_next);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstNameValid = false;
        lastNameValid = false;
        emailValid = false;
        phoneValid = false;

        bt_next.setOnClickListener(listener -> {

            String firstName, lastName, email, phone;

            firstName = et_firstName.getText().toString();
            lastName = et_lastName.getText().toString();
            email = et_email.getText().toString();
            phone = et_phone.getText().toString();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {

                if (Settings.isEmailValid(email)) {

                    Bundle bundle = new Bundle();

                    bundle.putString(EMAIL, email);
                    bundle.putString(PHONE, phone);
                    bundle.putString(FIRST_NAME, firstName);
                    bundle.putString(LAST_NAME, lastName);

                    Fragment fragmentRegisterTow = new RegisterTwoFragment();
                    fragmentRegisterTow.setArguments(bundle);


                    FragmentTransaction transaction;
                    if (getFragmentManager() != null) {
                        transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.register_form_fragments_parent, fragmentRegisterTow);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }


                }

            }

        });

        et_firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence name, int i, int i1, int i2) {

                if (name.length() < 3){
                    et_firstName.setError("Your First Name is too Short");
                    firstNameValid = false;
                } else if (name.length() > 10){
                    et_firstName.setError("Your Fist Name is too Long");
                    firstNameValid = false;
                } else
                    firstNameValid = true;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        et_lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence name, int i, int i1, int i2) {

                if (name.length() < 4){
                    et_lastName.setError("Your Last Name is too Short");
                    lastNameValid = false;
                } else if (name.length() > 25){
                    et_lastName.setError("Your Last Name is too Long");
                    lastNameValid = false;
                } else
                    lastNameValid = true;


            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence email, int i, int i1, int i2) {

                if (!Settings.isEmailValid(email.toString())){
                    et_email.setError(EMAIL_NOT_VALID_ERROR);
                    emailValid = false;
                } else
                    emailValid = true;


            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence phone, int i, int i1, int i2) {

                if (phone.length() == 10) {
                    if (!phone.toString().startsWith("059") && !phone.toString().startsWith("056")){
                        et_phone.setError("Phone Number Must Start With 059 or 056");
                        phoneValid = false;
                    }
                    else
                        phoneValid = true;
                } else{
                    et_phone.setError("Phone Number Must be 10 Numbers");
                    phoneValid = false;
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

    }

    private void buttonConfirm() {
        if (firstNameValid && lastNameValid && emailValid && phoneValid) {
            bt_next.setEnabled(true);
            bt_next.setBackgroundResource(R.drawable.app_button_background);
        } else if (bt_next.isEnabled()) {
            bt_next.setEnabled(false);
            bt_next.setBackgroundResource(R.drawable.app_button_background_disabled);
        }
    }

}

