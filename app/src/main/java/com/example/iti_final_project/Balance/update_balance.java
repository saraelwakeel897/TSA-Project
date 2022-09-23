package com.example.iti_final_project.Balance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;

public class update_balance extends AppCompatActivity {

    EditText text_unit_num, text_unit_price;
    Button update_btn, delete_btn;
    String id, category, unit_num, unit_price;

    String[] categories = {"Samsung Mobile Phones", "IPhone Mobiles", "LG TV", "Samsung TV", "HTC VR headset", "HUAWEI smart watch", "Apple smart Watch", "PlayStation 5"};
    AutoCompleteTextView autoComplete_category;
    ArrayAdapter<String> adapter_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_balance);

        autoComplete_category = findViewById(R.id.balance_text_category2);
        adapter_categories = new ArrayAdapter<String>(this, R.layout.select, categories);
        autoComplete_category.setAdapter(adapter_categories);

        text_unit_num = findViewById(R.id.balance_text_unit_num2);
        text_unit_price = findViewById(R.id.balance_text_unit_price2);
        update_btn = findViewById(R.id.balance_update_btn);
        delete_btn = findViewById(R.id.balance_delete_btn);

        balance_getAndSetIntentData();

        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setTitle(category);
        }

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBase balanceDataBase = new DataBase(update_balance.this);
                category = autoComplete_category.getText().toString().trim();
                unit_num = text_unit_num.getText().toString().trim();
                unit_price = text_unit_price.getText().toString().trim();
                balanceDataBase.balance_updateData(id, category, unit_num, unit_price);
                finish();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balance_confirmDialog();
            }
        });

    }

    public void balance_getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("category") &&
                getIntent().hasExtra("unit_num") && getIntent().hasExtra("unit_price") ){
            //get data
            id = getIntent().getStringExtra("id");
            category = getIntent().getStringExtra("category");
            unit_num = getIntent().getStringExtra("unit_num");
            unit_price = getIntent().getStringExtra("unit_price");

            //set data
            autoComplete_category.setText(category);
            text_unit_num.setText(unit_num);
            text_unit_price.setText(unit_price);
        }
        else{
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
        }
    }

    public void balance_confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Sure you want to delete " + category + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataBase db = new DataBase(update_balance.this);
                db.balance_deleteData(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.create().show();
    }

}