package com.example.waterchecker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {

    Button signupbtn;
    EditText name, email, pwd;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        signupbtn = findViewById(R.id.signupbtn);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);

        // Set click listener for signup button
        signupbtn.setOnClickListener(v -> {
            String userName = name.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String password = pwd.getText().toString().trim();

            // Validate inputs
            if (userName.isEmpty()) {
                Toast.makeText(CreateAccountActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mail.isEmpty()) {
                Toast.makeText(CreateAccountActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(CreateAccountActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(CreateAccountActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create user with Firebase Auth
            mAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccountActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                            // Optionally, navigate to another activity
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
