package com.example.iti_final_project.Sales;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iti_final_project.Adapter.SalesAdapter;
import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class sales extends AppCompatActivity {

    public static RecyclerView sales_recyclerView;
    FloatingActionButton sales_add_fab;

    ImageView sales_empty_imageView;
    TextView sales_no_data;

    DataBase sales_db;
    ArrayList<String> sales_id, shop_name, unit_type, sales_qty, total_price, date;
    SalesAdapter salesAdapter;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.GET_ACCOUNTS}, 1);


        sales_empty_imageView = findViewById(R.id.sales_empty_imageView);
        sales_no_data = findViewById(R.id.sales_no_data);

        sales_recyclerView = findViewById(R.id.sales_recyclerview);
        sales_add_fab = findViewById(R.id.sales_add_fab);
        sales_add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sales.this, add_sales.class);
                startActivity(intent);
            }
        });

        sales_db = new DataBase(sales.this);
        sales_id= new ArrayList<>();
        shop_name = new ArrayList<>();
        unit_type = new ArrayList<>();
        sales_qty = new ArrayList<>();
        total_price = new ArrayList<>();
        date = new ArrayList<>();

        sales_storeDataInArray();

        salesAdapter = new SalesAdapter(sales.this, this, sales_id, shop_name, unit_type, sales_qty, total_price, date);
        sales_recyclerView.setAdapter(salesAdapter);
        sales_recyclerView.setLayoutManager(new LinearLayoutManager(sales.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    public void sales_storeDataInArray(){
        Cursor cursor = sales_db.sales_readAllData();
        if(cursor.getCount() == 0){
            sales_empty_imageView.setVisibility(View.VISIBLE);
            sales_no_data.setVisibility(View.VISIBLE);
        }
        else{
            while (cursor.moveToNext()){
                sales_id.add(cursor.getString(0));
                shop_name.add(cursor.getString(1));
                unit_type.add(cursor.getString(2));
                sales_qty.add(cursor.getString(3));
                total_price.add(cursor.getString(4));
                date.add(cursor.getString(5));
            }
            sales_empty_imageView.setVisibility(View.GONE);
            sales_no_data.setVisibility(View.GONE);
        }
    }

    // action bar
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_sales, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // send screenshot of operations in email
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*bitmap = LoadBitmap(sales_recyclerView, sales_recyclerView.getWidth(), sales_recyclerView.getHeight());
        createPdf();*/
        sales_createPdf();
        File path = new File(
                String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS))
                        + "/", "Sales.pdf");
        Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName()
                + ".provider", path);

        Intent email = new Intent(Intent.ACTION_SEND);
        //email.setType("message/rfc822");
        email.setType("application/pdf");
        email.putExtra(Intent.EXTRA_EMAIL  , new String[]{"smelwakeel333@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Check Out Sales Operation");
        email.putExtra(Intent.EXTRA_TEXT   , "All Detail of Email are here in pdf");
        email.putExtra(Intent.EXTRA_STREAM   , uri );
        try {
            startActivity(Intent.createChooser(email, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(sales.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sales_createPdf() {
        // create a new document
        PdfDocument pdfDocument = new PdfDocument();
        int pageNumber = 0;
        try {
            pageNumber++;

            // crate a page description
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(400, 500, pageNumber).create();

            // start a page
            PdfDocument.Page myPage = pdfDocument.startPage(pageInfo);

            // text content
            Canvas canvas = myPage.getCanvas();
            Paint paint = new Paint();

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(12.0f);
            paint.setColor(Color.BLACK);
            canvas.drawText("Sales Operations", pageInfo.getPageWidth()/2, 30, paint);

            paint.setTextSize(8.0f);
            paint.setColor(Color.rgb(122, 119, 119));
            canvas.drawText("This is the sales operations through this week", pageInfo.getPageWidth()/2, 45, paint);

            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(8.0f);
            paint.setColor(Color.rgb(122, 119, 119));
            canvas.drawText("Sales Information", 10, 70, paint);

            int startX = 10;
            int startY = 100;

            // sales--------sales_id, shop_name, unit_type, sales_qty, total_price, date------------------------------------------------
            for (int i = 0; i < sales_id.size(); i++) {
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("ID", 10, 90, paint);
                paint.setColor(Color.BLUE);
                canvas.drawText(String.valueOf(sales_id.get(i)), startX, startY + 10, paint);
                startY += 20;
            }

            for (int i = 0; i < shop_name.size(); i++) {
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Shop", 40, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText(String.valueOf(shop_name.get(i)), startX + 30, startY - 70 , paint);
                startY += 20;
            }

            for (int i = 0; i < unit_type.size(); i++) {
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Type", 240, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.RIGHT);

                canvas.drawText(String.valueOf(unit_type.get(i)), startX + 230, startY - 150, paint);
                startY += 20;
            }

            for (int i = 0; i < date.size(); i++) {
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Date", 330, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.RIGHT);

                canvas.drawText(String.valueOf(date.get(i)), startX + 320, startY - 230, paint);
                startY += 20;
            }

            for (int i = 0; i < sales_qty.size(); i++) {
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Qty", 380, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.RIGHT);

                canvas.drawText(String.valueOf(sales_qty.get(i)), startX + 370, startY - 310, paint);
                startY += 20;
            }

            //finish the page
            pdfDocument.finishPage(myPage);

            String targetPdf = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
            File file = new File(targetPdf + "/", "Sales.pdf");
            FileOutputStream outputStream = new FileOutputStream(file);
            pdfDocument.writeTo(outputStream);
            Toast.makeText(this, "Sales PDF file generated successfully!", Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close document
        pdfDocument.close();
    }

    /*private Bitmap LoadBitmap(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    private void createPdf() {
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE) ;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels;
        float height = displayMetrics.heightPixels;
        int convertWidth = (int)width;
        int convertHeight = (int)height;

        PdfDocument document = new PdfDocument();
        int pageNumber = 0;
        try {
            pageNumber++;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, pageNumber).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        String targetPdf = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
        File file = new File(targetPdf + "/", "Sales.pdf");

            document.writeTo(new FileOutputStream(file));
        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }*/

}

