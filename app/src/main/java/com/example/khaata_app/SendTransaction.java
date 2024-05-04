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

import java.text.SimpleDateFormat;
import java.util.Date;
import android.telephony.SmsManager;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



public class SendTransaction extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1001;

    Button btnBackSendTransaction,btnSendTransaction;
    TextView etNameSendTransaction,etAmountSendTransaction;
    int vendor_id,customer_id;
    String customer_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_transaction);
        requestSMSPermission();

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
                    updateRemainingAmount();
                    DatabaseHelperCustomer db = new DatabaseHelperCustomer(SendTransaction.this);
                    db.open();
                    String phoneNumber = db.getPhoneNumber(customer_id);
                    db.close();
                    if (phoneNumber != null) {
                        sendSMS(phoneNumber, "Your transaction with Name : " + name + " and Amount : " + amount+" has been added to khata which has been sent to you");
                    }
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

    public void updateRemainingAmount(){
        String amount =etAmountSendTransaction.getText().toString().trim();
        int adding_amount=Integer.parseInt(amount);

        DatabaseHelperCustomer db=new DatabaseHelperCustomer(this);
        db.open();
        int remaining_amount=db.getRemainingAmountForCustomer(customer_id);
        db.close();

        int new_amount=remaining_amount+adding_amount;

        DatabaseHelperCustomer db_update=new DatabaseHelperCustomer(this);
        db_update.open();
        db_update.updateCustomerRemainingAmount(customer_id,new_amount);
        db.close();
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void requestSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }
}