package com.example.waterchecker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AboutusActivity extends AppCompatActivity {

    private EditText edName, edEmail, edPassword;
    private FirebaseFirestore db;
    private FirebaseUser user;

    Button btnSave , btnOut;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        // Initialize UI components
        edName = findViewById(R.id.edname);
        edEmail = findViewById(R.id.edemail);
        edPassword = findViewById(R.id.edpassword);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnOut = findViewById(R.id.btnOut);

        btnOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });

        // Initialize Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        // Load user details if logged in
        if (user != null) {
            edEmail.setText(user.getEmail());
            loadUserData();
        }

        // Save button click event
        btnSave.setOnClickListener(v -> saveUserData());

        Button btnread = findViewById(R.id.btnread);

// Read Button Click
        btnread.setOnClickListener(v -> {
            String filePath = "file:///C:/Users/USER/Documents/home.html";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(filePath));
            startActivity(intent);
        });
    }

    private void loadUserData() {
        DocumentReference docRef = db.collection("Users").document(user.getUid());
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                edName.setText(documentSnapshot.getString("name"));
            }
        }).addOnFailureListener(e ->
                Toast.makeText(AboutusActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show());
    }

    private void saveUserData() {
        String name = edName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);

        db.collection("Users").document(user.getUid()).set(userData)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(AboutusActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(AboutusActivity.this, "Failed to update", Toast.LENGTH_SHORT).show());

        if (!password.isEmpty()) {
            user.updatePassword(password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AboutusActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AboutusActivity.this, "Error Updating Password", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
