package com.example.waterchecker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;



public class DashboardActivity extends AppCompatActivity {

    Button map, calculate, report, tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        map = findViewById(R.id.map);
        calculate = findViewById(R.id.calculate);
        report = findViewById(R.id.report);
        tip = findViewById(R.id.tip);

        map.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MapActivity.class);
            startActivity(intent);
        });

        calculate.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, CalculateActivity.class);
            startActivity(intent);
        });

        report.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, DrinkWaterActivity.class);
            startActivity(intent);
        });

        tip.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AboutusActivity.class);
            startActivity(intent);
        });

    }

}
