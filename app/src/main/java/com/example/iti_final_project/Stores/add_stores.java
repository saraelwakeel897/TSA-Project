package com.example.iti_final_project.Stores;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;
import com.example.iti_final_project.MainPages.stores;

import java.util.Calendar;

public class add_stores extends AppCompatActivity {

    EditText text_stores_shop_name, text_shop_phone, text_last_visit_date;
    Button stores_add_btn;

    String[] location = {"Dakahlia", "Sharkia", "Gharbia", "Damietta"};
    AutoCompleteTextView autoComplete_location;
    ArrayAdapter<String> adapter_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stores);

        autoComplete_location = findViewById(R.id.store_autocomplete_shop_location);
        adapter_location = new ArrayAdapter<String>(this, R.layout.select, location);
        autoComplete_location.setAdapter(adapter_location);

        text_stores_shop_name = findViewById(R.id.text_stores_shop_name);
        autoComplete_location = findViewById(R.id.store_autocomplete_shop_location);
        text_shop_phone = findViewById(R.id.text_shop_phone);
        text_last_visit_date = findViewById(R.id.text_last_visit_date);
        stores_add_btn = findViewById(R.id.stores_add_btn);
        stores_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(text_stores_shop_name.getText().toString())
                        || TextUtils.isEmpty(autoComplete_location.getText().toString())
                        || TextUtils.isEmpty(text_shop_phone.getText().toString())
                        || TextUtils.isEmpty(text_last_visit_date.getText().toString())){
                    Toast.makeText(add_stores.this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
                }
                else{
                DataBase db = new DataBase(add_stores.this);
                db.addStores(text_stores_shop_name.getText().toString().trim(),
                        autoComplete_location.getText().toString().trim(),
                        Integer.valueOf(text_shop_phone.getText().toString().trim()),
                        text_last_visit_date.getText().toString().trim());
                Intent intent = new Intent(add_stores.this, stores.class);
                startActivity(intent);
                finish();
                }
            }
        });

        // date picker
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        text_last_visit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        add_stores.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        text_last_visit_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}