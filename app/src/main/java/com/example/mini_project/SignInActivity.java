package com.example.mini_project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends Activity {
    EditText editTextUsername, editTextPassword;
    Button buttonSignIn;
    TextView textViewCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        textViewCreateAccount = findViewById(R.id.textViewCreateAccount);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    editTextUsername.setError("Username không được để trống");
                } else if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password không được để trống");
                } else {
                    // Nếu không bị trống, kiểm tra thông tin tài khoản trong SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
                    String savedUsername = sharedPreferences.getString("username", null);
                    String savedPassword = sharedPreferences.getString("password", null);

                    if (username.equals(savedUsername) && password.equals(savedPassword)) {
                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        // Tiếp tục điều hướng đến trang chính của ứng dụng hoặc thực hiện các hành động khác
                    } else {
                        Toast.makeText(SignInActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Chuyển sang giao diện SignUp
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }
}
