package com.example.iti_final_project.Stores;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;

import java.util.Calendar;

public class update_stores extends AppCompatActivity {

    EditText text_last_visit_date2;
    Button stores_update_btn;
    String id, last_visit_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stores);

        text_last_visit_date2 = findViewById(R.id.stores_last_visit_date2);
        stores_update_btn = findViewById(R.id.stores_update_btn);

        stores_getAndSetIntentData();

        stores_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBase stores_db = new DataBase(update_stores.this);
                last_visit_date = text_last_visit_date2.getText().toString().trim();
                stores_db.stores_updateDate(id, last_visit_date);
                finish();
            }
        });

        // date picker
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        text_last_visit_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        update_stores.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        text_last_visit_date2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void stores_getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("last_visit_date") ){
            //get data
            id = getIntent().getStringExtra("id");
            last_visit_date = getIntent().getStringExtra("last_visit_date");

            //set data
            text_last_visit_date2.setText(last_visit_date);
        }
        else{
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
        }
    }
}