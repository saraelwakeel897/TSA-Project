package com.example.iti_final_project.Maintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;

public class add_maintenance extends AppCompatActivity {

    EditText text_defect_description;
    Button maintenance_add_btn;

    AutoCompleteTextView autocomplete_maintenance_shop_name, autoComplete_maintenance_unit_type, autoComplete_defect_type;
    DataBase maintenance_db;
    ArrayList<String> maintenance_shop_name, maintenance_unit_type;
    String[] defect_type = {"Energy", "Abuse", "Packaging", "Other"};
    ArrayAdapter<String> adapter_maintenance_shop_name, adapter_maintenance_unit_type,adapter_defect_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintenance);

        maintenance_db = new DataBase(add_maintenance.this);

        autoComplete_maintenance_unit_type = findViewById(R.id.maintenance_text_unit_type);
        maintenance_unit_type = new ArrayList<>();
        sales_displayUnitsInSpinner();

        adapter_maintenance_unit_type = new ArrayAdapter<String>(this, R.layout.select, maintenance_unit_type);
        adapter_maintenance_unit_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoComplete_maintenance_unit_type.setAdapter(adapter_maintenance_unit_type);

        autoComplete_defect_type = findViewById(R.id.maintenance_text_defect_type);
        adapter_defect_type = new ArrayAdapter<String>(this, R.layout.select, defect_type);
        autoComplete_defect_type.setAdapter(adapter_defect_type);

        autocomplete_maintenance_shop_name = findViewById(R.id.maintenance_autocomplete_shop_name);
        maintenance_shop_name = new ArrayList<>();
        maintenance_displayStoresInSpinner();

        adapter_maintenance_shop_name = new ArrayAdapter<String>(this, R.layout.select, maintenance_shop_name);
        adapter_maintenance_shop_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autocomplete_maintenance_shop_name.setAdapter(adapter_maintenance_shop_name);

        text_defect_description = findViewById(R.id.maintenance_text_defect_description);

        maintenance_add_btn = findViewById(R.id.maintenance_add_btn);
        maintenance_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(autoComplete_maintenance_unit_type.getText().toString())
                        || TextUtils.isEmpty(autocomplete_maintenance_shop_name.getText().toString())
                        || TextUtils.isEmpty(autoComplete_defect_type.getText().toString())
                        || TextUtils.isEmpty(text_defect_description.getText().toString()))
                {
                    Toast.makeText(add_maintenance.this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBase db = new DataBase(add_maintenance.this);
                    db.addMaintenance(autoComplete_maintenance_unit_type.getText().toString().trim(),
                            autocomplete_maintenance_shop_name.getText().toString().trim(),
                            autoComplete_defect_type.getText().toString().trim(),
                            text_defect_description.getText().toString().trim(),
                            false);

                    Intent intent = new Intent(add_maintenance.this, maintenance.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @SuppressLint("Range")
    private void maintenance_displayStoresInSpinner() {
        Cursor cursor = maintenance_db.stores_readAllData();
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            while(true) {
                maintenance_shop_name.add(cursor.getString(cursor.getColumnIndex("stores_shop_name")).toString());
                if(!cursor.moveToNext()) break;
            }
        }
    }


    @SuppressLint("Range")
    public void sales_displayUnitsInSpinner(){
        Cursor cursor = maintenance_db.balance_readAllData();
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            while(true) {
                maintenance_unit_type.add(cursor.getString(cursor.getColumnIndex("category")).toString());
                if(!cursor.moveToNext()) break;
            }
        }
    }

}