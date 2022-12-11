package com.example.iti_final_project.MainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;

public class register extends AppCompatActivity {

    TextView go_to_login;
    EditText register_username_text, register_email_text, register_password_text;
    Button register_btn;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_username_text = findViewById(R.id.register_username_text);
        register_email_text = findViewById(R.id.register_email_text);
        register_password_text = findViewById(R.id.register_password_text);
        register_btn = findViewById(R.id.register_btn);
        go_to_login = findViewById(R.id.go_to_login);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(register_password_text.getText().toString().trim().length() < 6){
                    Toast.makeText(register.this, "Password must be >= 6 character!", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(register_username_text.getText().toString())
                        || TextUtils.isEmpty(register_password_text.getText().toString())
                        || TextUtils.isEmpty(register_email_text.getText().toString())){
                    Toast.makeText(register.this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBase db = new DataBase(register.this);
                    db.addUser(register_username_text.getText().toString().trim(),
                            register_password_text.getText().toString().trim(),
                            register_email_text.getText().toString().trim());

                    Intent intent = new Intent(register.this, home.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
    }
}