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
import android.telephony.SmsManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ReceiveTransaction extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1001;
    Button btnBackReceiveTransaction,btnReceiveTransaction;
    TextView etNameReceiveTransaction,etAmountReceiveTransaction;
    int vendor_id,customer_id;
    String customer_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_transaction);

        btnBackReceiveTransaction=findViewById(R.id.btnBackReceiveTransaction);
        btnReceiveTransaction=findViewById(R.id.btnReceiveTransaction);
        etNameReceiveTransaction=findViewById(R.id.etNameReceiveTransaction);
        etAmountReceiveTransaction=findViewById(R.id.etAmountReceiveTransaction);
        vendor_id=getIntent().getIntExtra("user_id", -1);
        customer_id=getIntent().getIntExtra("customer_user_id", -1);
        customer_name=getIntent().getStringExtra("customer_name");

        btnBackReceiveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReceiveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etNameReceiveTransaction.getText().toString().trim();
                String amount=etAmountReceiveTransaction.getText().toString().trim();
                if(name.isEmpty()){etNameReceiveTransaction.setError("Field cannot be empty");}
                if(amount.isEmpty()){etAmountReceiveTransaction.setError("Field cannot be empty");}
                else{
                    addTransaction();
                    updateRemainingAmount();
                    DatabaseHelperCustomer db = new DatabaseHelperCustomer(ReceiveTransaction.this);
                    db.open();
                    String phoneNumber = db.getPhoneNumber(customer_id);
                    db.close();
                    if (phoneNumber != null) {
                        sendSMS(phoneNumber, "Your transaction with Name : " + name + " and Amount : " + amount+" has been added to khata which has been received by us");
                    }
                    Intent intent = new Intent(ReceiveTransaction.this, SingleKhaataRecord.class);
                    intent.putExtra("customer_user_id", customer_id);
                    intent.putExtra("customer_name",customer_name);
                    startActivity(intent);
                    finish();}
            }
        });
    }

    public void addTransaction(){
        String name=etNameReceiveTransaction.getText().toString().trim();
        String amount=etAmountReceiveTransaction.getText().toString().trim();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(currentDate);

        Date currentTime = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(currentTime);

        DatabaseHelperTransaction myDatabaseHelper = new DatabaseHelperTransaction(this);
        myDatabaseHelper.open();
        myDatabaseHelper.insertTransaction(vendor_id,customer_id,name,formattedDate,formattedTime,0,1,Integer.parseInt(amount));
        myDatabaseHelper.close();
    }

    public void updateRemainingAmount(){
        String amount =etAmountReceiveTransaction.getText().toString().trim();
        int subtracting_amount=Integer.parseInt(amount);

        DatabaseHelperCustomer db=new DatabaseHelperCustomer(this);
        db.open();
        int remaining_amount=db.getRemainingAmountForCustomer(customer_id);
        db.close();

        int new_amount=remaining_amount-subtracting_amount;

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