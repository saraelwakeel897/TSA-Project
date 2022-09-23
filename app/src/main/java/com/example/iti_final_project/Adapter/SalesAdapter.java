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

import com.example.iti_final_project.Sales.update_sales;
import com.example.iti_final_project.R;

import java.util.ArrayList;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder> {

    Context sales_context;
    ArrayList sales_id, shop_name, unit_type, sales_qty, total_price, date;
    Activity sales_activity;

    Animation animation;

    public SalesAdapter(Activity sales_activity, Context sales_context, ArrayList sales_id, ArrayList shop_name , ArrayList unit_type,ArrayList sales_qty, ArrayList total_price, ArrayList date){
        this.sales_activity = sales_activity;
        this.sales_context = sales_context;
        this.sales_id = sales_id;
        this.shop_name = shop_name;
        this.unit_type = unit_type;
        this.sales_qty = sales_qty;
        this.total_price = total_price;
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(sales_context);
        View view = layoutInflater.inflate(R.layout.sales_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.sales_id_txt.setText(String.valueOf(sales_id.get(position)));
        holder.shop_name_txt.setText(String.valueOf(shop_name.get(position)));
        holder.unit_type_txt.setText(String.valueOf(unit_type.get(position)));
        holder.sales_qty_txt.setText(Html.fromHtml("<b> Qty: </b> " + String.valueOf(sales_qty.get(position))));
        holder.total_price_txt.setText(Html.fromHtml("<b> Total: $ </b> " + String.valueOf(total_price.get(position))));
        holder.date_txt.setText(String.valueOf(date.get(position)));
        holder.sales_edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sales_context, update_sales.class);
                intent.putExtra("sales_id", String.valueOf(sales_id.get(position)));
                intent.putExtra("shop_name", String.valueOf(shop_name.get(position)));
                intent.putExtra("unit_type", String.valueOf(unit_type.get(position)));
                intent.putExtra("total_price", String.valueOf(total_price.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                sales_activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sales_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sales_id_txt, shop_name_txt, unit_type_txt,sales_qty_txt, total_price_txt, date_txt;
        ImageView sales_edit_icon;
        LinearLayout sales_mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sales_id_txt = itemView.findViewById(R.id.sales_id_row);
            shop_name_txt = itemView.findViewById(R.id.sales_shop_name_row);
            unit_type_txt = itemView.findViewById(R.id.sales_unit_type_row);
            sales_qty_txt = itemView.findViewById(R.id.sales_quantity_row);
            total_price_txt = itemView.findViewById(R.id.sales_total_price_row);
            date_txt = itemView.findViewById(R.id.sales_date_row);
            sales_mainLayout = itemView.findViewById(R.id.sales_mainLayout);
            sales_edit_icon = itemView.findViewById(R.id.sales_edit_icon);

            animation = AnimationUtils.loadAnimation(sales_context, R.anim.animation);
            sales_mainLayout.setAnimation(animation);
        }
    }
}
