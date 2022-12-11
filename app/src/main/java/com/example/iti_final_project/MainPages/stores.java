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

import com.example.iti_final_project.Adapter.StoresAdapter;
import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;
import com.example.iti_final_project.Stores.add_stores;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class stores extends AppCompatActivity {

    RecyclerView stores_recyclerView;
    FloatingActionButton stores_add_fab;

    ImageView stores_empty_imageView;
    TextView stores_no_data;

    DataBase stores_db;
    ArrayList<String> stores_id, stores_shop_name, shop_location, shop_phone, last_visit_date;
    StoresAdapter storesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        stores_empty_imageView = findViewById(R.id.stores_empty_imageView);
        stores_no_data = findViewById(R.id.stores_no_data);

        stores_recyclerView = findViewById(R.id.stores_recyclerview);
        stores_add_fab = findViewById(R.id.stores_add_fab);
        stores_add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stores.this, add_stores.class);
                startActivity(intent);
            }
        });

        stores_db = new DataBase(stores.this);
        stores_id= new ArrayList<>();
        stores_shop_name = new ArrayList<>();
        shop_location = new ArrayList<>();
        shop_phone = new ArrayList<>();
        last_visit_date = new ArrayList<>();

        stores_storeDataInArray();

        storesAdapter = new StoresAdapter(stores.this, this, stores_id, stores_shop_name, shop_location, shop_phone, last_visit_date);
        stores_recyclerView.setAdapter(storesAdapter);
        stores_recyclerView.setLayoutManager(new LinearLayoutManager(stores.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
            recreate();
    }


    private void stores_storeDataInArray() {
        Cursor cursor = stores_db.stores_readAllData();
        if(cursor.getCount() == 0){
            stores_empty_imageView.setVisibility(View.VISIBLE);
            stores_no_data.setVisibility(View.VISIBLE);
        }
        else {
            while (cursor.moveToNext()) {
                stores_id.add(cursor.getString(0));
                stores_shop_name.add(cursor.getString(1));
                shop_location.add(cursor.getString(2));
                shop_phone.add(cursor.getString(3));
                last_visit_date.add(cursor.getString(4));
            }

            stores_empty_imageView.setVisibility(View.GONE);
            stores_no_data.setVisibility(View.GONE);
        }
    }
}