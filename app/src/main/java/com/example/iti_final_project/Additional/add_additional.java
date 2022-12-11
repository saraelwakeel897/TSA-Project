package com.example.iti_final_project.Additional;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;
import java.util.Calendar;

public class add_additional extends AppCompatActivity {

    EditText text_additional_quantity, text_additional_date;
    Button additional_add_btn;

    DataBase additional_db;
    AutoCompleteTextView autocomplete_additional_shop_name, autoComplete_additional_unit_type;
    ArrayList<String> additional_shop_name;
    String[] additional_unit_type = {"Samsung Mobile Phones", "IPhone Mobiles", "LG TV", "Samsung TV", "HTC VR headset", "HUAWEI smart watch", "Apple smart Watch", "PlayStation 5"};
    ArrayAdapter<String> adapter_additional_shop_name, adapter_additional_unit_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_additional);

        autoComplete_additional_unit_type = findViewById(R.id.additional_text_unit_type);
        adapter_additional_unit_type = new ArrayAdapter<String>(this, R.layout.select, additional_unit_type);
        autoComplete_additional_unit_type.setAdapter(adapter_additional_unit_type);

        autocomplete_additional_shop_name = findViewById(R.id.additional_autocomplete_shop_name);
        additional_db = new DataBase(add_additional.this);
        additional_shop_name = new ArrayList<>();
        additional_displayDataInSpinner();
        adapter_additional_shop_name = new ArrayAdapter<String>(this, R.layout.select, additional_shop_name);
        adapter_additional_shop_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autocomplete_additional_shop_name.setAdapter(adapter_additional_shop_name);

        text_additional_quantity = findViewById(R.id.additional_text_quantity);
        text_additional_date = findViewById(R.id.additional_text_date);

        additional_add_btn = findViewById(R.id.additional_add_btn);
        additional_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(autoComplete_additional_unit_type.getText().toString())
                        || TextUtils.isEmpty(autocomplete_additional_shop_name.getText().toString())
                        || TextUtils.isEmpty(text_additional_quantity.getText().toString())
                        || TextUtils.isEmpty(text_additional_date.getText().toString()))
                {
                    Toast.makeText(add_additional.this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBase db = new DataBase(add_additional.this);
                    db.addAdditional(autoComplete_additional_unit_type.getText().toString().trim(),
                            autocomplete_additional_shop_name.getText().toString().trim(),
                            Integer.valueOf(text_additional_quantity.getText().toString().trim()),
                            text_additional_date.getText().toString().trim());
                    Intent intent = new Intent(add_additional.this, additional_requests.class);
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

        text_additional_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        add_additional.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        text_additional_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }


    @SuppressLint("Range")
    private void additional_displayDataInSpinner() {
        Cursor cursor = additional_db.stores_readAllData();
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            while(true) {
                additional_shop_name.add(cursor.getString(cursor.getColumnIndex("stores_shop_name")).toString());
                if(!cursor.moveToNext()) break;
            }
        }
    }

}