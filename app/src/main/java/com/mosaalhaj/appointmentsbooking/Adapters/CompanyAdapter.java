package com.mosaalhaj.appointmentsbooking.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mosaalhaj.appointmentsbooking.Items.MyListListener;
import com.mosaalhaj.appointmentsbooking.Models.Address;
import com.mosaalhaj.appointmentsbooking.Models.Company;
import com.mosaalhaj.appointmentsbooking.R;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private Context context;
    private ArrayList<Company> companies;
    private MyListListener listener;


    public CompanyAdapter(Context context, ArrayList<Company> companies, MyListListener listener) {
        this.context = context;
        this.companies = companies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.company_list_item, null);

        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {

        Company company = companies.get(position);

        Address address = company.getAddress();

        holder.tv_company.setText(company.getName());
        holder.tv_country.setText(address != null ? address.getCountryName() : "-----");
        holder.tv_city.setText(address != null ? address.getCityName() : "-----");

        holder.tv_more.setOnClickListener(lis -> {
            listener.onItemMoreClick(position);
        });

        holder.bt_book.setOnClickListener(lis -> {
            listener.onItemBookClick(position);
        });


    }

    @Override
    public int getItemCount() {
        if (companies != null)
            return companies.size();
        else return 0;
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_company, tv_country, tv_city, tv_more;
        AppCompatButton bt_book;


        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_company = itemView.findViewById(R.id.company_list_item_tv_company_name);
            tv_country = itemView.findViewById(R.id.company_list_item_tv_country);
            tv_city = itemView.findViewById(R.id.company_list_item_tv_city);
            tv_more = itemView.findViewById(R.id.company_list_item_tv_more);

            bt_book = itemView.findViewById(R.id.company_list_item_bt_book);


        }


    }

}
