package com.example.khaata_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.graphics.Color;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{
    ArrayList<Transaction> transactions;
    Context context;

    TransactionAdapter.ItemSelected parentActivity;

    public interface ItemSelected{
        public void onItemClicked(int index);
    }
    public TransactionAdapter(Context c, ArrayList<Transaction> list)
    {
        context=c;
        parentActivity=(TransactionAdapter.ItemSelected) context;
        transactions = list;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_single_transaction, parent, false);
        return new TransactionAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(transactions.get(position));
        int red=Color.parseColor("#FF0000");
        int green=Color.parseColor("#008000");
        if(transactions.get(position).getSend()==1) {
            holder.tvAmountTransaction.setTextColor(red);
        }
        else if(transactions.get(position).getReceive()==1) {
            holder.tvAmountTransaction.setTextColor(green);
            int remaining_amount=transactions.get(position).getAmount();
            int showed_value=Math.abs(remaining_amount);
            holder.tvAmountTransaction.setText(String.valueOf(showed_value));
        }
        holder.tvNameTransaction.setText(transactions.get(position).getName());
        holder.tvDateTransaction.setText(transactions.get(position).getDate());
        holder.tvTimeTransaction.setText(transactions.get(position).getTime());
        holder.tvAmountTransaction.setText(String.valueOf(transactions.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvNameTransaction, tvDateTransaction,tvTimeTransaction,tvAmountTransaction;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameTransaction = itemView.findViewById(R.id.tvNameTransaction);
            tvDateTransaction = itemView.findViewById(R.id.tvDateTransaction);
            tvTimeTransaction= itemView.findViewById(R.id.tvTimeTransaction);
            tvAmountTransaction=itemView.findViewById(R.id.tvAmountTransaction);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.onItemClicked(transactions.indexOf((Transaction) itemView.getTag()));
                }
            });

        }
    }

}
