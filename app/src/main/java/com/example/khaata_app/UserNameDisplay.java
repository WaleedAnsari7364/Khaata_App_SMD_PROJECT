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

public class UserNameDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name_display);

        String username = getIntent().getStringExtra("user_name");
        int userId = getIntent().getIntExtra("user_id", -1);
        Button btnMoveToKhaata=findViewById(R.id.btnMoveToKhaata);
        TextView tvUserNameShow = findViewById(R.id.tvUserNameShow);
        Button btnBackUSerNameDisplay=findViewById(R.id.btnBackUSerNameDisplay);

        if (username != null) {
            tvUserNameShow.setText(username);
        } else {
            tvUserNameShow.setText("Unknown User");
        }

        btnBackUSerNameDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnMoveToKhaata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserNameDisplay.this, KhaataManager.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });
    }
}