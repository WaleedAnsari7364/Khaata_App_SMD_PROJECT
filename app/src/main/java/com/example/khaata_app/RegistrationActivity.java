package com.example.khaata_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrationActivity extends AppCompatActivity {

    EditText etEmailAddressRegistration;
    EditText etPasswordRegistration,etUserNameRegistration;
    Button btnBackRegistration;
    Button btnRegistrationPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btnBackRegistration=findViewById(R.id.btnBackRegistration);
        btnRegistrationPage=findViewById(R.id.btnRegistrationPage);
        etEmailAddressRegistration=findViewById(R.id.etEmailAddressRegistration);
        etPasswordRegistration=findViewById(R.id.etPasswordRegistration);
        etUserNameRegistration=findViewById(R.id.etUserNameRegistration);

        btnBackRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegistrationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etEmailAddressRegistration.getText().toString().trim();
                String password=etPasswordRegistration.getText().toString().trim();
                String username=etPasswordRegistration.getText().toString().trim();
                if(email.isEmpty()){etEmailAddressRegistration.setError("Field cannot be empty");}
                if(password.isEmpty()){etPasswordRegistration.setError("Field cannot be empty");}
                if(username.isEmpty()){etUserNameRegistration.setError("Field cannot be empty");}
                else{
                    addVendor();
                    finish();}
            }
        });

    }
    private void addVendor()
    {
        String email = etEmailAddressRegistration.getText().toString().trim();
        String password = etPasswordRegistration.getText().toString().trim();
        String username=etUserNameRegistration.getText().toString().trim();

        DatabaseHelperVendor myDatabaseHelper = new DatabaseHelperVendor(this);
        myDatabaseHelper.open();

        myDatabaseHelper.insert(username,email, password);

        myDatabaseHelper.close();
    }
}