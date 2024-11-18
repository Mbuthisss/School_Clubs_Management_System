package com.example.schoolclubsmanagementsystem.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.schoolclubsmanagementsystem.MainActivity;
import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.firestore.Students;
import com.example.schoolclubsmanagementsystem.models.Student;
import com.example.schoolclubsmanagementsystem.user.Authentication;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

public class SignUp3 extends AppCompatActivity {

    private ImageView backBtn;
    private Button next, login;
    private TextView titleText;
    private TextInputEditText phoneInput;
    private CountryCodePicker ccp;
    private Authentication auth;
    private Students studentsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        phoneInput = findViewById(R.id.signup_phone_input);
        ccp = findViewById(R.id.ccp);

        auth = new Authentication();
        studentsDb = new Students();

        login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        // Attach phoneInput to CountryCodePicker
        ccp.registerCarrierNumberEditText(phoneInput);
    }

    public void callNextSignupScreen(View view) {
        if (!ccp.isValidFullNumber()) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = ccp.getFullNumberWithPlus().trim();

        // Collect user data from the previous sign-up screens
        String fullName = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");
        String department = getIntent().getStringExtra("department");
        String gender = getIntent().getStringExtra("gender");
        String dob = getIntent().getStringExtra("dob");

        // Get the authenticated user's UID
        String studentId = auth.getCurrentUser().getUid();

        // Create a new Student object
        Student student = new Student();
        student.setStudentId(studentId);
        student.setName(fullName);
        student.setEmail(email);
        student.setPhone(phone);
        student.setDepartment(department);
        student.setGender(gender);
        student.setDob(dob);

        // Add the student to Firestore
        studentsDb.addStudent(student, new Students.FirestoreCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Navigate to MainActivity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3.this,
                        new Pair<View, String>(backBtn, "transition_back_arrow_btn"),
                        new Pair<View, String>(next, "transition_next_btn"),
                        new Pair<View, String>(login, "transition_login_btn"),
                        new Pair<View, String>(titleText, "transition_title_text"));
                startActivity(intent, options.toBundle());
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                // Handle error
                Toast.makeText(SignUp3.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
