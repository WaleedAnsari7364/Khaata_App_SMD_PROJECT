package com.example.khaata_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SingleKhaataRecord extends AppCompatActivity {

    Button btnBackSingleRecordKhaata;
    TextView tvCustomerNameKhaata;
    String customer_name;
    int customer_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_khaata_record);

        customer_name = getIntent().getStringExtra("customer_name");
        customer_id = getIntent().getIntExtra("customer_user_id", -1);
        btnBackSingleRecordKhaata=findViewById(R.id.btnBackSingleRecordKhaata);
        tvCustomerNameKhaata=findViewById(R.id.tvCustomerNameKhaata);

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



    }
}