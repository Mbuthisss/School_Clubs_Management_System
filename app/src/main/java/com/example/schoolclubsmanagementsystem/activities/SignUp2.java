package com.example.schoolclubsmanagementsystem.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.schoolclubsmanagementsystem.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUp2 extends AppCompatActivity {

    private Button next;
    private TextInputEditText fullNameInput, studentIdInput, departmentInput;
    private RadioGroup genderInput;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Hooks
        next = findViewById(R.id.signup_next_button);
        fullNameInput = findViewById(R.id.full_name_input);
        studentIdInput = findViewById(R.id.student_id_input);
        departmentInput = findViewById(R.id.department_input);
        genderInput = findViewById(R.id.gender_input);
        datePicker = findViewById(R.id.date_picker);

        next.setOnClickListener(this::callNextSignupScreen);
    }

    public void callNextSignupScreen(View view) {
        String fullName = fullNameInput.getText().toString().trim();
        String studentId = studentIdInput.getText().toString().trim();
        String department = departmentInput.getText().toString().trim();
        int selectedGenderId = genderInput.getCheckedRadioButtonId();
        String gender = selectedGenderId != -1 ? ((RadioButton) findViewById(selectedGenderId)).getText().toString() : null;
        String dob = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();

        if (fullName.isEmpty() || studentId.isEmpty() || department.isEmpty() || gender == null) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUp3.class);
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("password", getIntent().getStringExtra("password"));
        intent.putExtra("fullName", fullName);
        intent.putExtra("studentId", studentId);
        intent.putExtra("department", department);
        intent.putExtra("gender", gender);
        intent.putExtra("dob", dob);

        // Add Transition
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(next, "transition_next_btn");
        pairs[1] = new Pair<View, String>(fullNameInput, "transition_title_text");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2.this, pairs);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onBackPressed() {
        // Disable going back to SignUp1
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
