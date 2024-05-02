package com.example.khaata_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    ItemSelected parentActivity;

    public interface ItemSelected{
        public void onItemClicked(int index);
    }

    public KhaataAdapter(Context context, ArrayList<Customer> list)
    {

        parentActivity=(ItemSelected) context;
        customers = list;
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
        holder.tvName.setText(customers.get(position).getName());
        holder.tvDate.setText(customers.get(position).getDate());
        holder.tvTime.setText(customers.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName, tvDate,tvTime;
        Button btnDeleteKhaata;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime= itemView.findViewById(R.id.tvTime);
            btnDeleteKhaata=itemView.findViewById(R.id.btnDeleteKhaata);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.onItemClicked(customers.indexOf((Customer) itemView.getTag()));
                }
            });
        }
    }

}
