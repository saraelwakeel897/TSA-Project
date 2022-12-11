package com.example.iti_final_project.MainPages;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iti_final_project.Adapter.MaintenanceAdapter;
import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.Maintenance.add_maintenance;
import com.example.iti_final_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class maintenance extends AppCompatActivity {

    RecyclerView maintenance_recyclerView;
    FloatingActionButton maintenance_add_fab;

    ImageView maintenance_empty_imageView;
    TextView maintenance_no_data;

    DataBase maintenance_db;
    ArrayList<String> maintenance_id, maintenance_unit_type, maintenance_shop_name, defect_type, defect_description, is_fixed;
    MaintenanceAdapter maintenanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        maintenance_empty_imageView = findViewById(R.id.maintenance_empty_imageView);
        maintenance_no_data = findViewById(R.id.maintenance_no_data);

        maintenance_recyclerView = findViewById(R.id.maintenance_recyclerview);
        maintenance_add_fab = findViewById(R.id.maintenance_add_fab);
        maintenance_add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(maintenance.this, add_maintenance.class);
                startActivity(intent);
            }
        });

        maintenance_db = new DataBase(maintenance.this);
        maintenance_id = new ArrayList<>();
        maintenance_unit_type = new ArrayList<>();
        maintenance_shop_name = new ArrayList<>();
        defect_type = new ArrayList<>();
        defect_description = new ArrayList<>();
        is_fixed = new ArrayList<>();

        maintenance_storeDataInArray();

        maintenanceAdapter = new MaintenanceAdapter(maintenance.this, this, maintenance_id, maintenance_unit_type, maintenance_shop_name, defect_type, defect_description, is_fixed);
        maintenance_recyclerView.setAdapter(maintenanceAdapter);
        maintenance_recyclerView.setLayoutManager(new LinearLayoutManager(maintenance.this));

    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
            recreate();
    }

    private void maintenance_storeDataInArray() {
        Cursor cursor = maintenance_db.maintenance_readAllData();
        if(cursor.getCount() == 0){
            maintenance_empty_imageView.setVisibility(View.VISIBLE);
            maintenance_no_data.setVisibility(View.VISIBLE);
        }
        else{
            while (cursor.moveToNext()){
                maintenance_id.add(cursor.getString(0));
                maintenance_unit_type.add(cursor.getString(1));
                maintenance_shop_name.add(cursor.getString(2));
                defect_type.add(cursor.getString(3));
                defect_description.add(cursor.getString(4));
                is_fixed.add(cursor.getString(5));

            }
            maintenance_empty_imageView.setVisibility(View.GONE);
            maintenance_no_data.setVisibility(View.GONE);
        }
    }
}