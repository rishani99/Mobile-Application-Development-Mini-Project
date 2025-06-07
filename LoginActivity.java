package com.example.waterchecker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    EditText inputmail;
    EditText edpassword;
    TextView Loginpage;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginBtn = findViewById(R.id.loginBtn);
        inputmail = findViewById(R.id.inputmail);
        edpassword = findViewById(R.id.edpassword);
        Loginpage = findViewById(R.id.Loginpage);

        // Button click listener for login
        loginBtn.setOnClickListener(v -> {
            String mail = inputmail.getText().toString();
            String password = edpassword.getText().toString();

            if (TextUtils.isEmpty(mail)) {
                Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else {
                // Sign in using Firebase Authentication
                mAuth.signInWithEmailAndPassword(mail, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                // Redirect to the home screen after successful login
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class); // Replace with your home activity
                                startActivity(intent);
                                finish(); // Close the login screen
                            } else {
                                Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Login Failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
