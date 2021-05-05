package com.mosaalhaj.appointmentsbooking.Fragments.HomePagerFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.mosaalhaj.appointmentsbooking.APIs.AppointmentApiService;
import com.mosaalhaj.appointmentsbooking.APIs.CompanyApiService;
import com.mosaalhaj.appointmentsbooking.Activities.AppointmentActivity;
import com.mosaalhaj.appointmentsbooking.Activities.CompanyActivity;
import com.mosaalhaj.appointmentsbooking.Adapters.CompanyAdapter;
import com.mosaalhaj.appointmentsbooking.Databases.MyCashingDatabase;
import com.mosaalhaj.appointmentsbooking.Items.MyListListener;
import com.mosaalhaj.appointmentsbooking.Items.Settings;
import com.mosaalhaj.appointmentsbooking.Models.Appointment;
import com.mosaalhaj.appointmentsbooking.Models.Company;
import com.mosaalhaj.appointmentsbooking.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.CASHING;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_ID;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_LIST;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_NAME;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.NOT_FOUND;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.POSITION;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_ID;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_SHARED_PREFERENCE_FILE;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.WORK_END;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.WORK_START;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.getApiUrl;


public class ExploreFragment extends Fragment {


    private SearchView sv_companySearch;
    private RecyclerView rv_companyList;
    private CompanyAdapter adapter;
    private Retrofit retrofit;
    private ArrayList<Company> companies, listData;
    private String userId ;
    private SharedPreferences preferences;


    public ExploreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        sv_companySearch = view.findViewById(R.id.fragment_explore_sv_search);
        rv_companyList = view.findViewById(R.id.fragment_explore_rv_companies_list);

        retrofit = new Retrofit.Builder().baseUrl(getApiUrl())
                .addConverterFactory
                        (GsonConverterFactory.create(new GsonBuilder().setDateFormat("hh:mm a")
                                .create()))
                .client(Settings.getOkHttpClient()).build();

        companies = new ArrayList<>();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = getContext().getSharedPreferences(USER_SHARED_PREFERENCE_FILE,MODE_PRIVATE);
        userId = preferences.getString(USER_ID,NOT_FOUND);

        MyListListener listener = new MyListListener() {
            @Override
            public void onItemMoreClick(int position) {

                Intent intent = new Intent(getContext(), CompanyActivity.class) ;
                intent.putExtra(USER_ID,userId);
                intent.putExtra(POSITION,position);
                intent.putExtra(COMPANY_LIST,companies);
                if (getActivity() != null)
                    getActivity().startActivity(intent);

            }
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onItemBookClick(int position) {

                Intent intent = new Intent(getContext(), AppointmentActivity.class);

                Company company = companies.get(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable(COMPANY,company);
                bundle.putString(USER_ID,userId);

                intent.putExtras(bundle);


                if (getContext() != null)
                    startActivity(intent);

            }
        };

        adapter = new CompanyAdapter(getContext(), companies, listener);

        rv_companyList.setAdapter(adapter);

        rv_companyList.setLayoutManager(new LinearLayoutManager(getContext()));


        CompanyApiService service = retrofit.create(CompanyApiService.class);

        Call<ArrayList<Company>> getCompaniesCall = service.getAllCompanies();


        sv_companySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Call<ArrayList<Company>> searchCompanyCall = service.companySearch(query);
                searchCompanyCall.enqueue(new Callback<ArrayList<Company>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Company>> call, Response<ArrayList<Company>> response) {
                        if (response.body() != null) {

                            companies.clear();
                            companies.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<Company>> call, Throwable t) {
                        Toast.makeText(getContext(), "Search Error", Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        sv_companySearch.setOnCloseListener(() -> {

            if (listData != null){
                companies.clear();
                companies.addAll(listData);
                adapter.notifyDataSetChanged();
            }

            return false;
        });


        getCompaniesCall.enqueue(new Callback<ArrayList<Company>>() {
            @Override
            public void onResponse(Call<ArrayList<Company>> call, Response<ArrayList<Company>> response) {

                if (listData == null)
                    listData = response.body();

                if (listData != null){
                    companies.addAll(listData);
                    adapter.notifyDataSetChanged();
                }

                boolean isCashingEnabled = preferences.getBoolean(CASHING,false);

                if (isCashingEnabled){

                    MyCashingDatabase database = MyCashingDatabase.getInstance(getContext());
                    database.insertCompanies(listData);
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Company>> call, Throwable t) {


                boolean isCashingEnabled = preferences.getBoolean(CASHING,false);

                if (isCashingEnabled){

                    MyCashingDatabase database = MyCashingDatabase.getInstance(getContext());
                    listData = database.getCompanies();
                    companies.clear();
                    companies.addAll(listData);
                    adapter.notifyDataSetChanged();
                }


            }
        });

    }
}