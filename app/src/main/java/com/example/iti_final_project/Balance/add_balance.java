package com.example.iti_final_project.Balance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;
import com.example.iti_final_project.MainPages.weekly_balance;

public class add_balance extends AppCompatActivity {

    EditText text_unit_num, text_unit_price;
    Button add_btn;

    String[] categories = {"Samsung Mobile Phones", "IPhone Mobiles", "LG TV", "Samsung TV", "HTC VR headset", "HUAWEI smart watch", "Apple smart Watch", "PlayStation 5"};
    AutoCompleteTextView autoComplete_category;
    ArrayAdapter<String> adapter_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        autoComplete_category = findViewById(R.id.balance_autocomplete_product_type);
        adapter_categories = new ArrayAdapter<String>(this, R.layout.select, categories);
        autoComplete_category.setAdapter(adapter_categories);

        text_unit_num = findViewById(R.id.balance_text_shop_name);
        text_unit_price = findViewById(R.id.balance_text_defect_type);
        add_btn = findViewById(R.id.balance_add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(autoComplete_category.getText().toString())
                        || TextUtils.isEmpty(text_unit_num.getText().toString())
                        || TextUtils.isEmpty(text_unit_price.getText().toString())){
                    Toast.makeText(add_balance.this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBase db = new DataBase(add_balance.this);
                    db.addBalance(autoComplete_category.getText().toString().trim(),
                            Integer.valueOf(text_unit_num.getText().toString().trim()),
                            Double.valueOf(text_unit_price.getText().toString().trim()));

                    Intent intent = new Intent(add_balance.this, weekly_balance.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}