package com.example.iti_final_project.Additional;

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

import com.example.iti_final_project.Adapter.AdditionalAdapter;
import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class additional_requests extends AppCompatActivity {

    RecyclerView additional_recyclerView;
    FloatingActionButton additional_add_fab;

    ImageView additional_empty_imageView;
    TextView additional_no_data;

    DataBase additional_db;
    ArrayList<String> additional_id, additional_unit_type, additional_shop_name, additional_quantity, additional_date;
    AdditionalAdapter additionalAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_requests);

        additional_empty_imageView = findViewById(R.id.additional_empty_imageView);
        additional_no_data = findViewById(R.id.additional_no_data);

        additional_recyclerView = findViewById(R.id.additional_recyclerview);
        additional_add_fab = findViewById(R.id.additional_add_fab);
        additional_add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(additional_requests.this, add_additional.class);
                startActivity(intent);
            }
        });

        additional_db = new DataBase(additional_requests.this);
        additional_id = new ArrayList<>();
        additional_unit_type = new ArrayList<>();
        additional_shop_name = new ArrayList<>();
        additional_quantity = new ArrayList<>();
        additional_date = new ArrayList<>();

        additional_storeDataInArray();

        additionalAdapter = new AdditionalAdapter(additional_requests.this, this, additional_id, additional_unit_type, additional_shop_name, additional_quantity, additional_date);
        additional_recyclerView.setAdapter(additionalAdapter);
        additional_recyclerView.setLayoutManager(new LinearLayoutManager(additional_requests.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
            recreate();
    }

    
    private void additional_storeDataInArray() {
        Cursor cursor = additional_db.additional_readAllData();
        if(cursor.getCount() == 0){
            additional_empty_imageView.setVisibility(View.VISIBLE);
            additional_no_data.setVisibility(View.VISIBLE);
        }
        else{
            while (cursor.moveToNext()){
                additional_id.add(cursor.getString(0));
                additional_unit_type.add(cursor.getString(1));
                additional_shop_name.add(cursor.getString(2));
                additional_quantity.add(cursor.getString(3));
                additional_date.add(cursor.getString(4));
            }
            additional_empty_imageView.setVisibility(View.GONE);
            additional_no_data.setVisibility(View.GONE);
        }
    }
}