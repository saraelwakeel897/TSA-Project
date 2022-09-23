package com.example.iti_final_project.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iti_final_project.Balance.update_balance;
import com.example.iti_final_project.R;

import java.util.ArrayList;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.MyViewHolder> {

    Context balance_context;
    ArrayList balance_id, category, unit_num, unit_price;
    Activity balance_activity;
    Animation animation;

    public BalanceAdapter(Activity activity, Context context, ArrayList balance_id, ArrayList category , ArrayList unit_num, ArrayList unit_price){
        this.balance_activity = activity;
        this.balance_context = context;
        this.balance_id = balance_id;
        this.category = category;
        this.unit_num = unit_num;
        this.unit_price = unit_price;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(balance_context);
        View view = layoutInflater.inflate(R.layout.balance_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.balance_id_txt.setText(String.valueOf(balance_id.get(position)));
        holder.category_txt.setText(String.valueOf(category.get(position)));
        holder.unit_num_txt.setText(Html.fromHtml("<b> Qty: </b> " + String.valueOf(unit_num.get(position))));
        holder.unit_price_txt.setText(Html.fromHtml("<b> $ </b> " + String.valueOf(unit_price.get(position))));
        holder.balance_edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(balance_context, update_balance.class);
                intent.putExtra("id", String.valueOf(balance_id.get(position)));
                intent.putExtra("category", String.valueOf(category.get(position)));
                intent.putExtra("unit_num", String.valueOf(unit_num.get(position)));
                intent.putExtra("unit_price", String.valueOf(unit_price.get(position)));
                balance_activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return balance_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView balance_id_txt, category_txt, unit_num_txt, unit_price_txt;
        ImageView balance_edit_icon;
        LinearLayout balance_mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            balance_id_txt = itemView.findViewById(R.id.balance_id_row);
            category_txt = itemView.findViewById(R.id.balance_category_row);
            unit_num_txt = itemView.findViewById(R.id.balance_unit_num_row);
            unit_price_txt = itemView.findViewById(R.id.balance_unit_price_row);
            balance_mainLayout = itemView.findViewById(R.id.balance_mainLayout);
            balance_edit_icon = itemView.findViewById(R.id.balance_edit_icon);

            animation = AnimationUtils.loadAnimation(balance_context, R.anim.animation);
            balance_mainLayout.setAnimation(animation);

        }
    }
}
