package com.example.iti_final_project.Sales;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.iti_final_project.R;

import java.util.Calendar;

public class update_sales extends AppCompatActivity {

    EditText text_shop_name, text_total_price, text_date;
    Button sales_update_btn, sales_delete_btn;

    String[] unit_type = {"Samsung Mobile Phones", "IPhone Mobiles", "LG TV", "Samsung TV", "HTC VR headset", "HUAWEI smart watch", "Apple smart Watch", "PlayStation 5"};
    AutoCompleteTextView autoComplete_unit_type;
    ArrayAdapter<String> adapter_unit_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sales);

        autoComplete_unit_type = findViewById(R.id.sales_text_unit_type2);
        adapter_unit_type = new ArrayAdapter<String>(this, R.layout.select, unit_type);
        autoComplete_unit_type.setAdapter(adapter_unit_type);

        text_shop_name = findViewById(R.id.sales_text_shop_name2);
        text_total_price = findViewById(R.id.balance_text_unit_price2);
        text_date = findViewById(R.id.sales_text_date2);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        text_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        update_sales.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        text_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        sales_update_btn = findViewById(R.id.sales_update_btn);
        sales_delete_btn = findViewById(R.id.sales_delete_btn);

        sales_getAndSetIntentData();

        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setTitle("");
        }

        sales_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sales_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sales_confirmDialog();
            }
        });
    }

    public void sales_getAndSetIntentData(){}

    public void sales_confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Sure you want to delete ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*DataBase db = new DataBase(update_balance.this);
                db.deleteData(id);
                finish();*/
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}