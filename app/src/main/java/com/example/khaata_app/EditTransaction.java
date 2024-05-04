package com.example.khaata_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditTransaction extends AppCompatActivity {

    Button btnBackEditTransaction,btnDeleteEditTransaction,btnUpdateEditTransaction;
    EditText etNameEditTransaction,etAmountEditTransaction;
    int vendor_id,customer_id,transaction_id;
    String customer_name;
    String transaction_name;
    int transaction_amount_to_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        btnBackEditTransaction=findViewById(R.id.btnBackEditTransaction);
        btnDeleteEditTransaction=findViewById(R.id.btnDeleteEditTransaction);
        btnUpdateEditTransaction=findViewById(R.id.btnUpdateEditTransaction);
        etNameEditTransaction=findViewById(R.id.etNameEditTransaction);
        etAmountEditTransaction=findViewById(R.id.etAmountEditTransaction);

        customer_name = getIntent().getStringExtra("customer_name");
        customer_id = getIntent().getIntExtra("customer_user_id", -1);
        vendor_id=getIntent().getIntExtra("user_id", -1);
        transaction_id=getIntent().getIntExtra("transaction_id", -1);

        DatabaseHelperTransaction helper=new DatabaseHelperTransaction(EditTransaction.this);
        helper.open();
        transaction_name=helper.getName(transaction_id);
        transaction_amount_to_show=helper.getAmount(transaction_id);
        helper.close();

        etNameEditTransaction.setText(transaction_name);
        etAmountEditTransaction.setText(String.valueOf(transaction_amount_to_show));


        btnBackEditTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDeleteEditTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(EditTransaction.this);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to delete it?");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseHelperTransaction database = new DatabaseHelperTransaction(EditTransaction.this);
                        database.open();

                        int send_value=database.getSendValue(transaction_id);
                        int receive_value=database.getReceiveValue(transaction_id);
                        int transaction_amount=database.getAmount(transaction_id);

                        database.deleteTransaction(transaction_id);
                        database.close();

                        DatabaseHelperCustomer db=new DatabaseHelperCustomer(EditTransaction.this);
                        db.open();
                        int remaining_amount=db.getRemainingAmountForCustomer(customer_id);
                        int actual_amount=0;
                        if(send_value==1){
                            actual_amount=remaining_amount-transaction_amount;
                        }
                        else if(receive_value==1){
                            actual_amount=remaining_amount+transaction_amount;
                        }
                        db.updateCustomerRemainingAmount(customer_id,actual_amount);
                        db.close();

                        Intent intent = new Intent(EditTransaction.this, SingleKhaataRecord.class);
                        intent.putExtra("user_id", vendor_id);
                        intent.putExtra("customer_user_id",customer_id);
                        intent.putExtra("customer_name",customer_name);
                        startActivity(intent);
                        finish();
                    }
                });

                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                deleteDialog.show();
            }
        });

        btnUpdateEditTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int changed_amount=Integer.parseInt(etAmountEditTransaction.getText().toString().trim());
                String changed_name=etNameEditTransaction.getText().toString().trim();
                DatabaseHelperTransaction database = new DatabaseHelperTransaction(EditTransaction.this);
                database.open();

                int send_value=database.getSendValue(transaction_id);
                int receive_value=database.getReceiveValue(transaction_id);
                database.updateTransaction(transaction_id,changed_name,changed_amount);
                database.close();

                DatabaseHelperCustomer db=new DatabaseHelperCustomer(EditTransaction.this);
                db.open();
                int remaining_amount=db.getRemainingAmountForCustomer(customer_id);
                if (send_value==1){
                    int actual_amount_after_subtracting_send_amount=remaining_amount-transaction_amount_to_show;
                    int updated_amount=actual_amount_after_subtracting_send_amount+changed_amount;
                    db.updateCustomerRemainingAmount(customer_id,updated_amount);
                }
                else if (receive_value==1){
                    int actual_amount_after_adding_receive_amount=remaining_amount+transaction_amount_to_show;
                    int updated_amount=actual_amount_after_adding_receive_amount-changed_amount;
                    db.updateCustomerRemainingAmount(customer_id,updated_amount);
                }
                db.close();




                Intent intent = new Intent(EditTransaction.this, SingleKhaataRecord.class);
                intent.putExtra("user_id", vendor_id);
                intent.putExtra("customer_user_id",customer_id);
                intent.putExtra("customer_name",customer_name);
                startActivity(intent);
                finish();
            }
        });

    }
}