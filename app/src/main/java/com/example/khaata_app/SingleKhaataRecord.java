package com.example.khaata_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SingleKhaataRecord extends AppCompatActivity implements TransactionAdapter.ItemSelected {

    Button btnBackSingleRecordKhaata,btnSend,btnReceive;
    TextView tvCustomerNameKhaata;
    String customer_name;
    int customer_id;
    int vendor_id;

    RecyclerView rvSingleRecordKhaata;
    LinearLayoutManager manager;
    TransactionAdapter adapter;
    ArrayList<Transaction> transactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_khaata_record);
        init();

        btnBackSingleRecordKhaata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (customer_name != null) {
            tvCustomerNameKhaata.setText(customer_name);
        } else {
            tvCustomerNameKhaata.setText("Unknown User");
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleKhaataRecord.this, SendTransaction.class);
                intent.putExtra("user_id", vendor_id);
                intent.putExtra("customer_user_id",customer_id);
                intent.putExtra("customer_name",customer_name);
                startActivity(intent);
                finish();
            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleKhaataRecord.this, ReceiveTransaction.class);
                intent.putExtra("user_id", vendor_id);
                intent.putExtra("customer_user_id",customer_id);
                intent.putExtra("customer_name",customer_name);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        customer_name = getIntent().getStringExtra("customer_name");
        customer_id = getIntent().getIntExtra("customer_user_id", -1);
        vendor_id=getIntent().getIntExtra("user_id", -1);
        btnBackSingleRecordKhaata=findViewById(R.id.btnBackSingleRecordKhaata);
        tvCustomerNameKhaata=findViewById(R.id.tvCustomerNameKhaata);
        btnSend=findViewById(R.id.btnSend);
        btnReceive=findViewById(R.id.btnReceive);

        rvSingleRecordKhaata = findViewById(R.id.rvSingleRecordKhaata);
        rvSingleRecordKhaata.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        rvSingleRecordKhaata.setLayoutManager(manager);

        DatabaseHelperTransaction database = new DatabaseHelperTransaction(this);
        database.open();
        transactions = database.readAllTransactions(customer_id);
        database.close();

        adapter = new TransactionAdapter(this, transactions);
        rvSingleRecordKhaata.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this, String.valueOf(transactions.get(index).getTid()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SingleKhaataRecord.this, EditTransaction.class);
        intent.putExtra("user_id", vendor_id);
        intent.putExtra("customer_user_id",customer_id);
        intent.putExtra("customer_name",customer_name);
        intent.putExtra("transaction_id",transactions.get(index).getTid());
        startActivity(intent);
        finish();
    }

}