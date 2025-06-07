package com.example.waterchecker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DrinkWaterActivity extends AppCompatActivity {

    private Spinner spinnerGender, spinnerAge;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_water);

        // Correctly initialize instance variables
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerAge = findViewById(R.id.spinnerAge);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        // Gender options
        String[] genderOptions = {"Select Gender", "Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderOptions);
        spinnerGender.setAdapter(genderAdapter);

        // Age options
        String[] ageOptions = {"Select Age", "0-3", "4-8", "9-13", "14-18", "19-50", "50+"};
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ageOptions);
        spinnerAge.setAdapter(ageAdapter);

        // Button click event
        btnCalculate.setOnClickListener(view -> calculateWaterIntake());
    }

    @SuppressLint("SetTextI18n")
    private void calculateWaterIntake() {
        String gender = spinnerGender.getSelectedItem().toString();
        String age = spinnerAge.getSelectedItem().toString();
        double waterIntake = 0;

        // Water intake logic
        if (gender.equals("Male")) {
            switch (age) {
                case "0-3": waterIntake = 4.0; break;
                case "4-8": waterIntake = 5.5; break;
                case "9-13": waterIntake = 7.0; break;
                case "14-18": waterIntake = 10.0; break;
                case "19-50": waterIntake = 13.0; break;
                case "50+": waterIntake = 12.0; break;
            }
        } else if (gender.equals("Female")) {
            switch (age) {
                case "0-3": waterIntake = 4.0; break;
                case "4-8": waterIntake = 5.0; break;
                case "9-13": waterIntake = 6.5; break;
                case "14-18": waterIntake = 9.0; break;
                case "19-50": waterIntake = 9.0; break;
                case "50+": waterIntake = 8.0; break;
            }
        }

        if (!gender.equals("Select Gender") && !age.equals("Select Age")) {
            tvResult.setText("You Need to Drink " + waterIntake + " glasses of water per day.");
        } else {
            tvResult.setText("Please select both gender and age.");
        }
    }
}
