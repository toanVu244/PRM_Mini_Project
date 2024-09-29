package com.example.mini_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextNewUsername, editTextNewPassword, editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView textViewSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Ánh xạ các view
        editTextNewUsername = findViewById(R.id.editTextNewUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewSignIn = findViewById(R.id.textViewSignIn);

        // Xử lý sự kiện khi nhấn vào "Already have an account? Sign In"
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình SignInActivity
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish(); // Đóng màn hình đăng ký để quay về màn hình đăng nhập
            }
        });

        // Xử lý sự kiện đăng ký
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = editTextNewUsername.getText().toString().trim();
                String newPassword = editTextNewPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                // Kiểm tra dữ liệu đầu vào
                if (TextUtils.isEmpty(newUsername)) {
                    editTextNewUsername.setError("Username không được để trống");
                } else if (TextUtils.isEmpty(newPassword)) {
                    editTextNewPassword.setError("Password không được để trống");
                } else if (TextUtils.isEmpty(confirmPassword)) {
                    editTextConfirmPassword.setError("Confirm Password không được để trống");
                } else if (!newPassword.equals(confirmPassword)) {
                    editTextConfirmPassword.setError("Password và Confirm Password không khớp");
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", newUsername);
                    editor.putString("password", newPassword);
                    editor.apply();
                    // Xử lý tạo tài khoản thành công (giả định)
                    Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();

                    // gi vào file json máy ảo  
                    JsonUntils.register(v.getContext(),newUsername,newPassword);

                    finish(); // Quay lại màn hình đăng nhập
                }
            }
        });
    }
}