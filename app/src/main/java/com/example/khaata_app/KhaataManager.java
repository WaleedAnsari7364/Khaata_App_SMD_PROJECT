package com.example.khaata_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KhaataManager extends AppCompatActivity {

    int Id;
    Button btnBackKhaataManager,btnaddCustomer;

    RecyclerView rvKhaata;
    LinearLayoutManager manager;
    KhaataAdapter adapter;
    ArrayList<Customer> customers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khaata_manager);
        init();

        btnBackKhaataManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnaddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhaataManager.this, AddCustomer.class);
                intent.putExtra("user_id", Id);
                startActivity(intent);
                finish();
            }
        });

    }

    private void init()
    {
        Id = getIntent().getIntExtra("user_id", -1);
        btnBackKhaataManager=findViewById(R.id.btnBackKhaataManager);
        btnaddCustomer=findViewById(R.id.btnaddCustomer);
        rvKhaata = findViewById(R.id.rvKhaata);
        rvKhaata.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        rvKhaata.setLayoutManager(manager);

        DatabaseHelperCustomer database = new DatabaseHelperCustomer(this);
        database.open();
        customers = database.readAllCustomers(Id);
        database.close();

        adapter = new KhaataAdapter(this, customers);
        rvKhaata.setAdapter(adapter);
    }
}