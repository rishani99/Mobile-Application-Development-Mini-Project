package com.example.waterchecker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    Button loginbtn, Newbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        // Match the IDs exactly with XML
        loginbtn = findViewById(R.id.loginbtn);
        Newbtn = findViewById(R.id.Newbtn);

        // Handle button clicks
        loginbtn.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        Newbtn.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });
    }

}
