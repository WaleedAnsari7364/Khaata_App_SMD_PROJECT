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

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{
    ArrayList<Transaction> transactions;
    Context context;

    public TransactionAdapter(Context c, ArrayList<Transaction> list)
    {
        context=c;
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


        }
    }

}
