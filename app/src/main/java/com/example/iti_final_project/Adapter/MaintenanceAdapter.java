package com.example.iti_final_project.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;

import java.util.ArrayList;

public class MaintenanceAdapter extends RecyclerView.Adapter<MaintenanceAdapter.MyViewHolder> {

    Context maintenance_context;
    ArrayList maintenance_id, maintenance_unit_type, maintenance_shop_name, defect_type, defect_description, is_fixed;
    Activity maintenance_activity;
    Animation animation;

    public MaintenanceAdapter(Activity maintenance_activity, Context maintenance_context, ArrayList maintenance_id, ArrayList maintenance_unit_type, ArrayList maintenance_shop_name, ArrayList defect_type, ArrayList defect_description, ArrayList is_fixed) {
        this.maintenance_activity = maintenance_activity;
        this.maintenance_context = maintenance_context;
        this.maintenance_id = maintenance_id;
        this.maintenance_unit_type = maintenance_unit_type;
        this.maintenance_shop_name = maintenance_shop_name;
        this.defect_type = defect_type;
        this.defect_description = defect_description;
        this.is_fixed = is_fixed;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(maintenance_context);
        View view = layoutInflater.inflate(R.layout.maintenance_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.maintenance_id_txt.setText(String.valueOf(maintenance_id.get(position)));
        holder.maintenance_unit_type_txt.setText(String.valueOf(maintenance_unit_type.get(position)));
        holder.maintenance_shop_name_txt.setText(String.valueOf(maintenance_shop_name.get(position)));
        holder.defect_type_txt.setText(Html.fromHtml("<b> Defect: </b> " + String.valueOf(defect_type.get(position))));
        holder.defect_description_txt.setText(String.valueOf(defect_description.get(position)));
        holder.btn_fixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(maintenance_context);
                builder.setTitle("Fixed Confirmation");
                builder.setMessage("Sure that " + holder.maintenance_unit_type_txt.getText().toString() + " Defect is Fixed?\n" +
                        "It will be deleted from the database.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.btn_fixed.setSelected(true);
                        holder.btn_fixed.setVisibility(View.GONE);
                        holder.defect_fixed_txt.setVisibility(View.VISIBLE);
                        holder.maintenance_cardView.setCardBackgroundColor(Color.parseColor("#FFB0BEC5"));

                        DataBase db = new DataBase(maintenance_context);
                        String _id = holder.maintenance_id_txt.getText().toString();
                        db.maintenance_update_isFixed(_id, true);
                        db.maintenance_deleteData(_id);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return maintenance_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView maintenance_id_txt, maintenance_unit_type_txt, maintenance_shop_name_txt, defect_type_txt, defect_description_txt, defect_fixed_txt;
        Button btn_fixed;
        CardView maintenance_cardView;
        LinearLayout maintenance_mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            maintenance_id_txt = itemView.findViewById(R.id.maintenance_id_row);
            maintenance_unit_type_txt = itemView.findViewById(R.id.maintenance_unit_type_row);
            maintenance_shop_name_txt = itemView.findViewById(R.id.maintenance_shop_name_row);
            defect_type_txt = itemView.findViewById(R.id.maintenance_defect_type_row);
            defect_description_txt = itemView.findViewById(R.id.maintenance_defect_description_row);
            defect_fixed_txt = itemView.findViewById(R.id.maintenance_defect_fixed_row);
            btn_fixed = itemView.findViewById(R.id.maintenance_btn_defect_fixed_row);

            maintenance_cardView = itemView.findViewById(R.id.maintenance_cardView);
            maintenance_mainLayout = itemView.findViewById(R.id.maintenance_mainLayout);
            animation = AnimationUtils.loadAnimation(maintenance_context, R.anim.animation);
            maintenance_mainLayout.setAnimation(animation);

        }
    }
}
