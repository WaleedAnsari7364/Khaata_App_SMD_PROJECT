package com.example.khaata_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class AddCustomer extends AppCompatActivity {

    Button btnBackAddCustomer,btnAddAddCustomer;
    EditText etNameAddCustomer;
    int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        btnBackAddCustomer=findViewById(R.id.btnBackAddCustomer);
        btnAddAddCustomer=findViewById(R.id.btnAddAddCustomer);
        etNameAddCustomer=findViewById(R.id.etNameAddCustomer);
        Id = getIntent().getIntExtra("user_id", -1);

        btnBackAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etNameAddCustomer.getText().toString().trim();
                if(name.isEmpty()){etNameAddCustomer.setError("Field cannot be empty");}
                else{
                    addCustomer();
                    Intent intent = new Intent(AddCustomer.this, KhaataManager.class);
                    intent.putExtra("user_id", Id);
                    startActivity(intent);
                    finish();}
            }
        });
    }

    public void addCustomer(){
        String name=etNameAddCustomer.getText().toString().trim();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(currentDate);

        Date currentTime = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(currentTime);

        DatabaseHelperCustomer myDatabaseHelper = new DatabaseHelperCustomer(this);
        myDatabaseHelper.open();
        myDatabaseHelper.insertCustomer(Id,name, formattedDate,formattedTime);
        myDatabaseHelper.close();

    }
}