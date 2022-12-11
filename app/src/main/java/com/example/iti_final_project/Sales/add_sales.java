package com.example.iti_final_project.Sales;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;

import java.util.ArrayList;
import java.util.Calendar;

public class add_sales extends AppCompatActivity {

    EditText text_sales_qty, text_total_price, text_date;
    Button sales_add_btn;
    TextView name_unit_data, unit_qty_data, price_unit_data;
    DataBase sales_db;

    AutoCompleteTextView autocomplete_sales_shop_name;
    Spinner autoComplete_unit_type;

    ArrayList<String> sales_shop_name, sales_unit_id, sales_unit_type, sales_quantity, sales_unit_price;
    ArrayAdapter<String> adapter_sales_shop_name, adapter_unit_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);

        sales_db = new DataBase(add_sales.this);
        sales_shop_name = new ArrayList<>();
        sales_displayStoresInSpinner();

        sales_unit_id = new ArrayList<>();
        sales_unit_type = new ArrayList<>();
        sales_quantity = new ArrayList<>();
        sales_unit_price = new ArrayList<>();
        sales_displayUnitsInSpinner();

        autoComplete_unit_type = findViewById(R.id.text_unit_type);
        adapter_unit_type = new ArrayAdapter<String>(this, R.layout.select, sales_unit_type);
        adapter_unit_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoComplete_unit_type.setAdapter(adapter_unit_type);

        unit_qty_data = findViewById(R.id.unit_qty_data);
        price_unit_data = findViewById(R.id.unit_price_data);
        name_unit_data = findViewById(R.id.unit_name_data);
        autoComplete_unit_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position , long id) {
                name_unit_data.setText(String.valueOf(sales_unit_id.get(position)));
                unit_qty_data.setText(String.valueOf(sales_quantity.get(position)));
                price_unit_data.setText(String.valueOf(sales_unit_price.get(position)));
                text_sales_qty.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        autocomplete_sales_shop_name = findViewById(R.id.autoComplete_sales_shop_name);
        adapter_sales_shop_name = new ArrayAdapter<String>(this, R.layout.select, sales_shop_name);
        adapter_sales_shop_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autocomplete_sales_shop_name.setAdapter(adapter_sales_shop_name);

        text_sales_qty = findViewById(R.id.text_sales_quantity);
        text_total_price = findViewById(R.id.text_total_price);
        text_sales_qty.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(text_sales_qty.getText().toString())) {
                    text_total_price.setText("");
                }
                else{
                    int stored_total_price = Integer.valueOf(price_unit_data.getText().toString());
                    int current_qty = Integer.valueOf(text_sales_qty.getText().toString());
                    text_total_price.setText(String.valueOf(current_qty * stored_total_price));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(text_sales_qty.getText().toString())) {
                    text_total_price.setText("");
                }
                else{
                    int current_qty = Integer.valueOf(text_sales_qty.getText().toString());
                    int stored_qty = Integer.valueOf(unit_qty_data.getText().toString());
                    if(current_qty > stored_qty){
                        Toast.makeText(add_sales.this, "Quantity Not Enough!", Toast.LENGTH_SHORT).show();
                        text_total_price.setText("");
                    }
                }
            }
        });


        sales_add_btn = findViewById(R.id.sales_add_btn);
        sales_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(autocomplete_sales_shop_name.getText().toString())
                        || TextUtils.isEmpty(autoComplete_unit_type.getSelectedItem().toString())
                        || TextUtils.isEmpty(text_sales_qty.getText().toString())
                        || TextUtils.isEmpty(text_total_price.getText().toString())
                        || TextUtils.isEmpty(text_date.getText().toString()))
                {
                    Toast.makeText(add_sales.this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBase db = new DataBase(add_sales.this);

                    db.addSales(autocomplete_sales_shop_name.getText().toString().trim(),
                            autoComplete_unit_type.getSelectedItem().toString().trim(),
                            Integer.valueOf(text_sales_qty.getText().toString().trim()),
                            Double.valueOf(text_total_price.getText().toString().trim()),
                            text_date.getText().toString().trim());

                    String id = name_unit_data.getText().toString();
                    int current_qty = Integer.valueOf(text_sales_qty.getText().toString());
                    int stored_qty = Integer.valueOf(unit_qty_data.getText().toString());
                    String new_qty = String.valueOf(stored_qty - current_qty);
                    db.balance_updateQty(id, new_qty.trim());

                    Intent intent = new Intent(add_sales.this, sales.class);
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

        text_date = findViewById(R.id.text_date);
        text_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        add_sales.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        text_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    @SuppressLint("Range")
    public void sales_displayStoresInSpinner(){
        Cursor cursor = sales_db.stores_readAllData();
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            while(true) {
                sales_shop_name.add(cursor.getString(cursor.getColumnIndex("stores_shop_name")).toString());
                if(!cursor.moveToNext()) break;
            }
        }
    }

    @SuppressLint("Range")
    public void sales_displayUnitsInSpinner(){
        Cursor cursor = sales_db.balance_readAllData();
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            while(true) {
                sales_unit_id.add(cursor.getString(cursor.getColumnIndex("id")).toString());
                sales_unit_type.add(cursor.getString(cursor.getColumnIndex("category")).toString());
                sales_quantity.add(cursor.getString(cursor.getColumnIndex("unit_num")).toString());
                sales_unit_price.add(cursor.getString(cursor.getColumnIndex("unit_price")).toString());
                if(!cursor.moveToNext()) break;
            }
        }
    }

}