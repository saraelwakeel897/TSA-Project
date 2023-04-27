package com.example.iti_final_project.MainPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.iti_final_project.Additional.additional_requests;
import com.example.iti_final_project.Balance.weekly_balance;
import com.example.iti_final_project.Maintenance.maintenance;
import com.example.iti_final_project.R;
import com.example.iti_final_project.Reports.reports;
import com.example.iti_final_project.Sales.sales;
import com.example.iti_final_project.Stores.stores;

public class home extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6;
    ScrollView main_scroll;
    CardView card1, card2, card3, card4, card5, card6;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        main_scroll = findViewById(R.id.main_scroll);
        main_scroll.setScaleX(getWindow().getDecorView().getScaleX());
        main_scroll.setScaleY(getWindow().getDecorView().getScaleY());


        btn1 = findViewById(R.id.share_type_btn);
        btn2 = findViewById(R.id.share_sales_btn);
        btn3 = findViewById(R.id.share_defect_btn);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(home.this, weekly_balance.class);
                startActivity(intent1);
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(home.this, weekly_balance.class);
                startActivity(intent1);
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(home.this, sales.class);
                startActivity(intent2);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(home.this, sales.class);
                startActivity(intent2);
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(home.this, stores.class);
                startActivity(intent3);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(home.this, stores.class);
                startActivity(intent3);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(home.this, maintenance.class);
                startActivity(intent4);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(home.this, maintenance.class);
                startActivity(intent4);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(home.this, additional_requests.class);
                startActivity(intent5);
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(home.this, additional_requests.class);
                startActivity(intent5);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(home.this, reports.class);
                startActivity(intent6);
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(home.this, reports.class);
                startActivity(intent6);
            }
        });

    }
}