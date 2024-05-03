package com.example.khaata_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SendTransaction extends AppCompatActivity {

    Button btnBackSendTransaction,btnSendTransaction;
    TextView etNameSendTransaction,etAmountSendTransaction;
    int vendor_id,customer_id;
    String customer_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_transaction);

        btnBackSendTransaction=findViewById(R.id.btnBackSendTransaction);
        btnSendTransaction=findViewById(R.id.btnSendTransaction);
        etNameSendTransaction=findViewById(R.id.etNameSendTransaction);
        etAmountSendTransaction=findViewById(R.id.etAmountSendTransaction);
        vendor_id=getIntent().getIntExtra("user_id", -1);
        customer_id=getIntent().getIntExtra("customer_user_id", -1);
        customer_name=getIntent().getStringExtra("customer_name");

        btnBackSendTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSendTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etNameSendTransaction.getText().toString().trim();
                String amount=etAmountSendTransaction.getText().toString().trim();
                if(name.isEmpty()){etNameSendTransaction.setError("Field cannot be empty");}
                if(amount.isEmpty()){etAmountSendTransaction.setError("Field cannot be empty");}
                else{
                    addTransaction();
                    Intent intent = new Intent(SendTransaction.this, SingleKhaataRecord.class);
                    intent.putExtra("customer_user_id", customer_id);
                    intent.putExtra("customer_name",customer_name);
                    startActivity(intent);
                    finish();}
            }
        });
    }

    public void addTransaction(){
        String name=etNameSendTransaction.getText().toString().trim();
        String amount=etAmountSendTransaction.getText().toString().trim();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(currentDate);

        Date currentTime = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(currentTime);

        DatabaseHelperTransaction myDatabaseHelper = new DatabaseHelperTransaction(this);
        myDatabaseHelper.open();
        myDatabaseHelper.insertTransaction(vendor_id,customer_id,name,formattedDate,formattedTime,1,0,Integer.parseInt(amount));
        myDatabaseHelper.close();
    }
}