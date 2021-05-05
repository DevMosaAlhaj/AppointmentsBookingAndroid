package com.mosaalhaj.appointmentsbooking.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.mosaalhaj.appointmentsbooking.APIs.UserApiService;
import com.mosaalhaj.appointmentsbooking.Models.User;
import com.mosaalhaj.appointmentsbooking.Models.UserLogin;
import com.mosaalhaj.appointmentsbooking.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.mosaalhaj.appointmentsbooking.APIs.RetrofitSingleton.getRetrofit;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.EMAIL_NOT_VALID_ERROR;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.NOT_FOUND;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.PASSWORD;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.PASSWORD_SHORT_CHARACTERS_ERROR;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.REMEMBER;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_ID;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_NAME;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_SHARED_PREFERENCE_FILE;
import static com.mosaalhaj.appointmentsbooking.Items.Settings.isEmailValid;

public class LoginActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private TextInputEditText et_email, et_password;
    private AppCompatButton bt_login;
    private View ll_register;
    private TextView tv_forgotPassword;
    private CheckBox cb_rememberMe;
    private Retrofit retrofit;
    private UserApiService userApiService;
    private SharedPreferences preferences;
    private String email, password;
    private Editor preferencesEditor;
    private boolean emailValid, passwordValid;


    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inflateViews();
        setSupportActionBar(myToolbar);

        retrofit = getRetrofit();
        userApiService = retrofit.create(UserApiService.class);

        checkUserIsRemembered();
        checkFromRegisterActivity();


        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence email, int i, int i1, int i2) {

                if (!isEmailValid(email.toString())) {
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

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence password, int i, int i1, int i2) {

                if (password.length() < 8) {
                    et_password.setError(PASSWORD_SHORT_CHARACTERS_ERROR);
                    passwordValid = false;
                } else
                    passwordValid = true;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonConfirm();
            }
        });

        bt_login.setOnClickListener(listener -> {

            bt_login.setEnabled(false);
            bt_login.setBackgroundResource(R.drawable.app_button_background_disabled);

            email = et_email.getText().toString();
            password = et_password.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {

                if (isEmailValid(email)) {

                    // Remember me actions

                    if (cb_rememberMe.isChecked()) {

                        preferencesEditor.putString(USER_NAME, email);
                        preferencesEditor.putString(PASSWORD, password);
                        preferencesEditor.putBoolean(REMEMBER, true);
                        preferencesEditor.commit();
                    }

                    // Try User Sign in

                    userSignIn(email, password);


                } else
                    et_email.setError("Email is Not Valid");

            } else {

                if (email.isEmpty())
                    et_email.setError("Email is Empty");
                else
                    et_password.setError("Password is Empty");

            }


        });

        tv_forgotPassword.setOnClickListener(lis -> {


        });

        ll_register.setOnClickListener(listener -> {
            Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
            startActivity(intent);

        });


    }

    @SuppressLint("CommitPrefEdits")
    private void checkUserIsRemembered() {
        preferences = getSharedPreferences(USER_SHARED_PREFERENCE_FILE,MODE_PRIVATE);
        preferencesEditor = preferences.edit();

        boolean isRemembered = preferences.getBoolean(REMEMBER, false);

        if (isRemembered) {
            email = preferences.getString(USER_NAME, NOT_FOUND);
            password = preferences.getString(PASSWORD, NOT_FOUND);

            if (email.equals(NOT_FOUND) || password.equals(NOT_FOUND)) {
                preferencesEditor.putBoolean(REMEMBER, false);
            } else
                userSignIn(email, password);

        }
    }

    private void userSignIn(String email, String password) {
        UserLogin userLogin = new UserLogin(email, password);
        Call<User> signInCall = userApiService.signIn(userLogin);

        signInCall.enqueue(new Callback<User>() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                bt_login.setEnabled(true);
                bt_login.setBackgroundResource(R.drawable.app_button_background);

                try {

                    User user = response.body();
                    Editor editor = preferences.edit();
                    editor.putString(USER_ID,user.getId());
                    editor.commit();
                    Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();

                } catch (Exception exception) {
                    Toast.makeText(getBaseContext(), "Email or Password is Incorrect", Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                bt_login.setEnabled(true);
                bt_login.setBackgroundResource(R.drawable.app_button_background);

                Toast.makeText(getBaseContext(), "Can't Login Right Now\nPlease Try Again Later", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkFromRegisterActivity() {
        Intent intent = getIntent();
        String username = intent.getStringExtra(USER_NAME);
        String password = intent.getStringExtra(PASSWORD);
        if (username != null && password != null) {
            et_email.setText(username);
            et_password.setText(password);
        }
    }

    private void inflateViews() {
        myToolbar = findViewById(R.id.main_tool_bar);
        et_email = findViewById(R.id.login_et_email);
        et_password = findViewById(R.id.login_et_password);
        tv_forgotPassword = findViewById(R.id.login_tv_forget_password);
        ll_register = findViewById(R.id.login_ll_register);
        bt_login = findViewById(R.id.login_bt_login);
        cb_rememberMe = findViewById(R.id.login_cb_remember_me);
    }

    private void buttonConfirm() {
        if (emailValid && passwordValid) {
            bt_login.setEnabled(true);
            bt_login.setBackgroundResource(R.drawable.app_button_background);
        } else if (bt_login.isEnabled()) {
            bt_login.setEnabled(false);
            bt_login.setBackgroundResource(R.drawable.app_button_background_disabled);
        }
    }

}