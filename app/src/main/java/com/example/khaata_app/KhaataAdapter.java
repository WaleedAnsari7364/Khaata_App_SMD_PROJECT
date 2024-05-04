package com.example.khaata_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KhaataAdapter extends RecyclerView.Adapter<KhaataAdapter.ViewHolder>{

    ArrayList<Customer> customers;
    Context context;

    String selectedCurrency;

    ItemSelected parentActivity;

    public interface ItemSelected{
        public void onItemClicked(int index);
    }

    public KhaataAdapter(Context context, ArrayList<Customer> list, String selectedCurrency)
    {

        parentActivity=(ItemSelected) context;
        customers = list;
        this.selectedCurrency = selectedCurrency;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_single_khaata, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(customers.get(position));
        int red= Color.parseColor("#FF0000");
        int green=Color.parseColor("#008000");
        double amount = customers.get(position).getRemaining_amount();
        if(customers.get(position).getRemaining_amount()>=0) {
            holder.tvRemainingAmount.setTextColor(red);
            holder.tvRemainingAmount.setText(getConvertedAmount(amount));
        }
        else{
            holder.tvRemainingAmount.setTextColor(green);
            double showed_value=Math.abs(amount);
            holder.tvRemainingAmount.setText(getConvertedAmount(showed_value));
        }
        holder.tvName.setText(customers.get(position).getName());
        holder.tvDate.setText(customers.get(position).getDate());
        holder.tvTime.setText(customers.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    private String getConvertedAmount(double amount) {
        double convertedAmount = 0;
        String formattedAmount = "";
        switch (selectedCurrency) {
            case "Rupees":
                convertedAmount = amount;
                formattedAmount = "PKR " + String.format("%.2f", convertedAmount);
                break;
            case "Dollar":
                convertedAmount = amount / 278.05;
                formattedAmount = "$ " + String.format("%.2f", convertedAmount);
                break;
            case "Riyal":
                convertedAmount = amount / 74.13;
                formattedAmount = "SAR " + String.format("%.2f", convertedAmount);
                break;
            case "Yen":
                convertedAmount = amount / 1.82;
                formattedAmount = "¥ " + String.format("%.2f", convertedAmount);
                break;
        }
        return formattedAmount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName, tvDate,tvTime,tvRemainingAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime= itemView.findViewById(R.id.tvTime);
            tvRemainingAmount=itemView.findViewById(R.id.tvRemainingAmount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.onItemClicked(customers.indexOf((Customer) itemView.getTag()));
                }
            });
        }
    }

}
