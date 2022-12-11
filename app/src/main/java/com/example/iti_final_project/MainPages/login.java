package com.example.iti_final_project.MainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.iti_final_project.R;

public class login extends AppCompatActivity {

    TextView go_to_register;
    EditText login_email_text, login_password_text;
    Button login_btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email_text = findViewById(R.id.login_email_text);
        login_password_text = findViewById(R.id.login_password_text);
        login_btn = findViewById(R.id.login_btn);
        go_to_register = findViewById(R.id.go_to_register);

        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }
}