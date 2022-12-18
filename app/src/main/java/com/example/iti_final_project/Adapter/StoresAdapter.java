package com.example.iti_final_project.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iti_final_project.R;
import com.example.iti_final_project.Stores.update_stores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.MyViewHolder>{

    Context stores_context;
    ArrayList stores_id, store_shop_name, shop_location, shop_phone, last_visit_date;
    Activity stores_activity;

    private static final int PERMISSION_CODE = 1;
    Animation animation;

    public StoresAdapter(Activity stores_activity, Context stores_context, ArrayList stores_id, ArrayList store_shop_name, ArrayList shop_location, ArrayList shop_phone, ArrayList last_visit_date) {
        this.stores_activity = stores_activity;
        this.stores_context = stores_context;
        this.stores_id = stores_id;
        this.store_shop_name = store_shop_name;
        this.shop_location = shop_location;
        this.shop_phone = shop_phone;
        this.last_visit_date = last_visit_date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(stores_context);
        View view = layoutInflater.inflate(R.layout.stores_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.stores_id_txt.setText(String.valueOf(stores_id.get(position)));
        holder.stores_shop_name_txt.setText(String.valueOf(store_shop_name.get(position)));

        holder.shop_location_txt.setText(String.valueOf(shop_location.get(position)));
        holder.shop_location_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder = new Geocoder(stores_context);
                List<Address> addressList;
                try{
                    addressList = geocoder.getFromLocationName(holder.shop_location_txt.getText().toString(), 1);
                    if(addressList != null){
                        double location_lat = addressList.get(0).getLatitude();
                        double location_long = addressList.get(0).getLongitude();
                        Toast.makeText(stores_context, "Latitude: " + String.valueOf(location_lat) +
                                " \nLongitude: " + String.valueOf(location_long), Toast.LENGTH_SHORT).show();
                        //holder.shop_location_txt.setText("Latitude: " + String.valueOf(location_lat) +" \nLongitude: " + String.valueOf(location_long) );
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        holder.shop_phone_txt.setText("0" + String.valueOf(shop_phone.get(position)));
        holder.last_visit_date_txt.setText(Html.fromHtml("<b> Last Visit: </b> " + String.valueOf(last_visit_date.get(position))));

        holder.stores_edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stores_context, update_stores.class);
                intent.putExtra("id", String.valueOf(stores_id.get(position)));
                intent.putExtra("last_visit_date", String.valueOf(last_visit_date.get(position)));
                stores_activity.startActivityForResult(intent, 1);
            }
        });
        holder.stores_phone_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(stores_context, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(stores_activity, new String[] {Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
                }
                Intent call=new Intent();

                String number = holder.shop_phone_txt.getText().toString().trim();
                call.setAction(android.content.Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:"+number));
                stores_activity.startActivity( call);
            }
        });
        holder.stores_location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder = new Geocoder(stores_context);
                List<Address> addressList;
                try{
                    addressList = geocoder.getFromLocationName(holder.shop_location_txt.getText().toString(), 1);
                    if(addressList != null){
                        double location_lat = addressList.get(0).getLatitude();
                        double location_long = addressList.get(0).getLongitude();
                        try {
                            Uri uri = Uri.parse("geo:" + String.valueOf(location_lat) + "," + String.valueOf(location_long));
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            stores_activity.startActivity(mapIntent);
                        }
                        catch (ActivityNotFoundException e){
                            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView stores_id_txt, stores_shop_name_txt, shop_location_txt, shop_phone_txt, last_visit_date_txt;
        ImageView stores_edit_icon, stores_location_icon, stores_phone_icon;
        LinearLayout stores_mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stores_id_txt = itemView.findViewById(R.id.stores_id_row);
            stores_shop_name_txt = itemView.findViewById(R.id.stores_shop_name_row);
            shop_location_txt = itemView.findViewById(R.id.stores_shop_location_row);
            shop_phone_txt = itemView.findViewById(R.id.stores_shop_phone_row);
            last_visit_date_txt = itemView.findViewById(R.id.stores_last_visit_date_row);

            stores_mainLayout = itemView.findViewById(R.id.stores_mainLayout);
            stores_edit_icon = itemView.findViewById(R.id.stores_edit_icon);
            stores_location_icon = itemView.findViewById(R.id.stores_location_icon);
            stores_phone_icon = itemView.findViewById(R.id.stores_phone_icon);

            animation = AnimationUtils.loadAnimation(stores_context, R.anim.animation);
            stores_mainLayout.setAnimation(animation);
        }
    }
}
