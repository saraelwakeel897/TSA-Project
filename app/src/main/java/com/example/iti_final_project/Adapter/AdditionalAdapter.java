package com.example.iti_final_project.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iti_final_project.R;

import java.util.ArrayList;

public class AdditionalAdapter extends RecyclerView.Adapter<AdditionalAdapter.MyViewHolder> {

    Activity additional_activity;
    Context additional_context;
    ArrayList additional_id, additional_unit_type, additional_shop_name, additional_quantity, additional_date;
    Animation animation;

    public AdditionalAdapter(Activity additional_activity, Context additional_context, ArrayList additional_id, ArrayList additional_unit_type, ArrayList additional_shop_name, ArrayList additional_quantity, ArrayList additional_date) {
        this.additional_activity = additional_activity;
        this.additional_context = additional_context;
        this.additional_id = additional_id;
        this.additional_unit_type = additional_unit_type;
        this.additional_shop_name = additional_shop_name;
        this.additional_quantity = additional_quantity;
        this.additional_date = additional_date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(additional_context);
        View view = layoutInflater.inflate(R.layout.additional_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.additional_id_txt.setText(String.valueOf(additional_id.get(position)));
        holder.additional_unit_type_txt.setText(String.valueOf(additional_unit_type.get(position)));
        holder.additional_shop_name_txt.setText(String.valueOf(additional_shop_name.get(position)));
        holder.additional_quantity_txt.setText(Html.fromHtml("<b> Qty: </b> " + String.valueOf(additional_quantity.get(position))));
        holder.additional_date_txt.setText(String.valueOf(additional_date.get(position)));
    }

    @Override
    public int getItemCount() {
        return additional_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        TextView additional_id_txt, additional_unit_type_txt, additional_shop_name_txt, additional_quantity_txt, additional_date_txt;
        LinearLayout additional_mainLayout;
        
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            additional_id_txt = itemView.findViewById(R.id.additional_id_row);
            additional_unit_type_txt = itemView.findViewById(R.id.additional_unit_type_row);
            additional_shop_name_txt = itemView.findViewById(R.id.additional_shop_name_row);
            additional_quantity_txt = itemView.findViewById(R.id.additional_quantity_row);
            additional_date_txt = itemView.findViewById(R.id.additional_date_row);

            additional_mainLayout = itemView.findViewById(R.id.additional_mainLayout);
            animation = AnimationUtils.loadAnimation(additional_context, R.anim.animation);
            additional_mainLayout.setAnimation(animation);
        }
    }
}
