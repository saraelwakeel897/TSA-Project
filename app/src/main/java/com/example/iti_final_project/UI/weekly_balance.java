package com.example.iti_final_project.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iti_final_project.Adapter.BalanceAdapter;
import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;
import com.example.iti_final_project.Balance.add_balance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class weekly_balance extends AppCompatActivity {

    public static RecyclerView balance_recyclerView;
    ImageView balance_empty_imageView;
    TextView balance_no_data;

    FloatingActionButton balance_fab, balance_add_fab, balance_reset_fab;
    Animation fab_open, fab_close, rotate_open, rotate_close;
    boolean isOpen = false;

    DataBase balance_db;
    ArrayList<String> balance_id, category, unit_num, unit_price;
    BalanceAdapter balanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_balance);

        balance_empty_imageView = findViewById(R.id.balance_empty_imageView);
        balance_no_data = findViewById(R.id.balance_no_data);
        balance_recyclerView = findViewById(R.id.balance_recyclerview);

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotate_open = AnimationUtils.loadAnimation(this, R.anim.rotate_open);
        rotate_close = AnimationUtils.loadAnimation(this, R.anim.rotate_close);

        balance_fab = findViewById(R.id.balance_fab);
        balance_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationFab();
            }
        });

        balance_add_fab = findViewById(R.id.balance_add_fab);
        balance_add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weekly_balance.this, add_balance.class);
                startActivity(intent);
            }
        });

        balance_reset_fab = findViewById(R.id.balance_reset_fab);
        balance_reset_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_confirmDialog();
            }
        });

        balance_db = new DataBase(weekly_balance.this);
        balance_id = new ArrayList<>();
        category = new ArrayList<>();
        unit_num = new ArrayList<>();
        unit_price = new ArrayList<>();

        balance_storeDataInArray();

        balanceAdapter = new BalanceAdapter(weekly_balance.this, this, balance_id, category, unit_num, unit_price);
        balance_recyclerView.setAdapter(balanceAdapter);
        balance_recyclerView.setLayoutManager(new LinearLayoutManager(weekly_balance.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
            recreate();
    }

    private void animationFab(){
        if(isOpen){
            balance_fab.startAnimation(rotate_open);
            balance_add_fab.startAnimation(fab_close);
            balance_reset_fab.startAnimation(fab_close);
            balance_add_fab.setClickable(false);
            balance_reset_fab.setClickable(false);
            isOpen = false;
        }
        else{
            balance_fab.startAnimation(rotate_close);
            balance_add_fab.startAnimation(fab_open);
            balance_reset_fab.startAnimation(fab_open);
            balance_add_fab.setClickable(true);
            balance_reset_fab.setClickable(true);
            isOpen = true;
        }
    }

    // show database
    public void balance_storeDataInArray(){
        //new FillBalanceRecyclerviewAdapter().execute();
        Cursor cursor = balance_db.balance_readAllData();
        if(cursor.getCount() == 0){
            balance_empty_imageView.setVisibility(View.VISIBLE);
            balance_no_data.setVisibility(View.VISIBLE);
        }
        else{
            while (cursor.moveToNext()){
                balance_id.add(cursor.getString(0));
                category.add(cursor.getString(1));
                unit_num.add(cursor.getString(2));
                unit_price.add(cursor.getString(3));
            }
            balance_empty_imageView.setVisibility(View.GONE);
            balance_no_data.setVisibility(View.GONE);
        }
    }

    // action bar
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_balance, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // action bar function
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            delete_confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    //delete all data confirmation dialog
    public void delete_confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Sure you want to delete all data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(weekly_balance.this, "All Data Deleted Successfully!", Toast.LENGTH_SHORT).show();
                DataBase db = new DataBase(weekly_balance.this);
                db.balance_deleteAllData();
                Intent intent = new Intent(weekly_balance.this, weekly_balance.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    //reset all qty confirmation dialog

    public void reset_confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Confirmation");
        builder.setMessage("Sure you want to reset all products?\nTheir Quantity will be 0");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(weekly_balance.this, "All Data Reset Successfully!", Toast.LENGTH_SHORT).show();
                DataBase balanceDataBase = new DataBase(weekly_balance.this);
                balanceDataBase.balance_resetData();
                Intent intent = new Intent(weekly_balance.this, weekly_balance.class);
                startActivity(intent);
                finish();
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