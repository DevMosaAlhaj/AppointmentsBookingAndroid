package com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.mosaalhaj.appointmentsbooking.Activities.AppointmentActivity;
import com.mosaalhaj.appointmentsbooking.Activities.MapActivity;
import com.mosaalhaj.appointmentsbooking.Models.Company;
import com.mosaalhaj.appointmentsbooking.R;

import retrofit2.http.HTTP;

import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.NOT_FOUND;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_ID;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_SHARED_PREFERENCE_FILE;


public class CompanyFragment extends Fragment {


    private ImageView iv_back;
    private TextView tv_companyName;
    private TextView tv_companyDescription;
    private TextView tv_companyEmail;
    private TextView tv_companyPhone;
    private AppCompatButton bt_book;
    private ImageView iv_location;
    private Company company;

    public CompanyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_company, container, false);

        iv_back = view.findViewById(R.id.fragment_company_iv_back);

        tv_companyName = view.findViewById(R.id.fragment_company_tv_name);
        tv_companyEmail = view.findViewById(R.id.fragment_company_tv_email);
        tv_companyDescription = view.findViewById(R.id.fragment_company_tv_description);
        tv_companyPhone = view.findViewById(R.id.fragment_company_tv_phone);

        bt_book = view.findViewById(R.id.fragment_company_bt_book);
        iv_location = view.findViewById(R.id.fragment_company_iv_location);

        Bundle bundle = getArguments();

        if (bundle != null) {
            company = bundle.getParcelable(COMPANY);
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_back.setOnClickListener(lis -> {
            if (getActivity() != null)
                getActivity().onBackPressed();
        });

        tv_companyPhone.setText(company.getPhoneNumber());
        tv_companyName.setText(company.getName());
        tv_companyDescription.setText(company.getDescription());
        tv_companyEmail.setText(company.getEmail());

        tv_companyPhone.setOnClickListener(lis -> {
            Intent intent = new Intent();
            Uri phoneUri = Uri.parse("tel:" + company.getPhoneNumber());
            intent.setData(phoneUri);
            intent.setAction(Intent.ACTION_DIAL);
            startActivity(intent);
        });



        bt_book.setOnClickListener(lis -> {

            String userId = "";
            if (getActivity() != null)
                userId = getActivity().getSharedPreferences(USER_SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).getString(USER_ID, NOT_FOUND);

            Intent intent = new Intent(getContext(), AppointmentActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString(USER_ID, userId);
            bundle.putParcelable(COMPANY, company);
            intent.putExtras(bundle);


            if (getContext() != null)
                startActivity(intent);


        });


        iv_location.setOnClickListener(lis -> {
            Intent intent = new Intent(getContext(), MapActivity.class);
            intent.putExtra(COMPANY, company);
            if (getActivity() != null) {
                getActivity().startActivity(intent);

            }
        });

    }


}