package com.example.iti_final_project.Reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iti_final_project.DataBase.DataBase;
import com.example.iti_final_project.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class reports extends AppCompatActivity {

    Button share_type_btn, share_sales_btn, share_defect_btn;
    ArrayList<String> b_id, b_type, b_qty, price;
    ArrayList<String> s_id, s_shop, s_type, s_qty, s_total, s_date;
    ArrayList<String> m_id, m_shop, m_type, defect, fixed;
    DataBase database;
    Activity activity = reports.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        share_type_btn = findViewById(R.id.share_type_btn);
        share_sales_btn = findViewById(R.id.share_sales_btn);
        share_defect_btn = findViewById(R.id.share_defect_btn);

        database = new DataBase(getApplicationContext());
        b_id = new ArrayList<>();
        b_type = new ArrayList<>();
        b_qty = new ArrayList<>();
        price = new ArrayList<>();

        s_id = new ArrayList<>();
        s_shop = new ArrayList<>();
        s_type = new ArrayList<>();
        s_qty = new ArrayList<>();
        s_total = new ArrayList<>();
        s_date = new ArrayList<>();

        m_id = new ArrayList<>();
        m_shop = new ArrayList<>();
        m_type = new ArrayList<>();
        defect = new ArrayList<>();
        fixed = new ArrayList<>();

        display_BalanceInPdf();
        display_SalesInPdf();
        display_DefectInPdf();


        share_type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balance_createPdf();
                File path = new File(
                        String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS))
                                + "/", "Categories.pdf");
                Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName()
                        + ".provider", path);
                Intent email = new Intent();
                email.setAction(Intent.ACTION_SEND);
                //email.setType("message/rfc822");
                email.setType("application/pdf");
                email.putExtra(Intent.EXTRA_EMAIL  , new String[]{"smelwakeel333@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Check Out Weekly Balance");
                email.putExtra(Intent.EXTRA_TEXT   , "All Detail of Email are here in pdf");
                email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                email.putExtra(Intent.EXTRA_STREAM   , uri );
                try {
                    startActivity(Intent.createChooser(email, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(reports.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            };
        });

        share_sales_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sales_createPdf();

                File path = new File(
                        String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS))
                                + "/", "Sales.pdf");
                Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName()
                        + ".provider", path);

                Intent email = new Intent();
                email.setAction(Intent.ACTION_SEND);
                //email.setType("message/rfc822");
                email.setType("application/pdf");
                email.putExtra(Intent.EXTRA_EMAIL  , new String[]{"smelwakeel333@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Check Out Weekly Balance");
                email.putExtra(Intent.EXTRA_TEXT   , "All Detail of Email are here in pdf");
                email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                email.putExtra(Intent.EXTRA_STREAM   , uri );
                try {
                    startActivity(Intent.createChooser(email, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(reports.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            };
        });

        share_defect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defect_createPdf();

                File path = new File(
                        String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS))
                                + "/", "Defects.pdf");
                Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName()
                        + ".provider", path);
                Intent email = new Intent();
                email.setAction(Intent.ACTION_SEND);
                //email.setType("message/rfc822");
                email.setType("application/pdf");
                email.putExtra(Intent.EXTRA_EMAIL  , new String[]{"smelwakeel333@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Check Out Weekly Balance");
                email.putExtra(Intent.EXTRA_TEXT   , "All Detail of Email are here in pdf");
                email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                email.putExtra(Intent.EXTRA_STREAM   , uri );
                try {
                    startActivity(Intent.createChooser(email, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(reports.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            };
        });
    }

    // create a pdf file and store in Documents
    private void balance_createPdf() {
       // create a new document
       PdfDocument pdfDocument = new PdfDocument();
       int pageNumber = 0;
       try {
           pageNumber++;

           // crate a page description
           PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(250, 350, pageNumber).create();

           // start a page
           PdfDocument.Page myPage = pdfDocument.startPage(pageInfo);

           // text content
           Canvas canvas = myPage.getCanvas();
           Paint paint = new Paint();

           paint.setTextAlign(Paint.Align.CENTER);
           paint.setTextSize(12.0f);
           paint.setColor(Color.BLACK);
           canvas.drawText("Product Category", pageInfo.getPageWidth()/2, 30, paint);

           paint.setTextSize(8.0f);
           paint.setColor(Color.rgb(122, 119, 119));
           canvas.drawText("This is the product category soled through this week", pageInfo.getPageWidth()/2, 45, paint);

           paint.setTextAlign(Paint.Align.LEFT);
           paint.setTextSize(8.0f);
           paint.setColor(Color.rgb(122, 119, 119));
           canvas.drawText("Product Information", 10, 70, paint);

           int startX = 10;
           int startY = 100;

           // balance--------------------------------------------------------
           for (int i = 0; i < b_id.size(); i++) {
               paint.setTextAlign(Paint.Align.LEFT);
               paint.setColor(Color.BLACK);
               paint.setTextScaleX(1.5f);
               canvas.drawText("ID", 10, 90, paint);
               paint.setColor(Color.BLUE);
               canvas.drawText(String.valueOf(b_id.get(i)), startX, startY +10, paint);
               startY += 20;
           }

           for (int i = 0; i < b_type.size(); i++) {
               paint.setTextAlign(Paint.Align.LEFT);
               paint.setColor(Color.BLACK);
               paint.setTextScaleX(1.5f);
               canvas.drawText("Type", 40, 90, paint);
               paint.setColor(Color.BLUE);
               paint.setTextAlign(Paint.Align.LEFT);

               canvas.drawText(String.valueOf(b_type.get(i)), startX + 30, startY - 50 , paint);
               startY += 20;
           }

           for (int i = 0; i < b_qty.size(); i++) {
               paint.setTextAlign(Paint.Align.RIGHT);
               paint.setColor(Color.BLACK);
               paint.setTextScaleX(1.5f);
               canvas.drawText("Qty", 220, 90, paint);
               paint.setColor(Color.BLUE);
               paint.setTextAlign(Paint.Align.RIGHT);

               canvas.drawText(String.valueOf(b_qty.get(i)), startX + 210, startY - 110, paint);
               startY += 20;
           }

           //finish the page
           pdfDocument.finishPage(myPage);

           String targetPdf = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
           File file = new File(targetPdf + "/", "Categories.pdf");
           FileOutputStream outputStream = new FileOutputStream(file);
           pdfDocument.writeTo(outputStream);
           Toast.makeText(activity, "PDF file generated successfully!", Toast.LENGTH_LONG).show();
       }
       catch (IOException e) {
           e.printStackTrace();
           Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
       }

       // close document
       pdfDocument.close();
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

            // sales--------s_id, s_shop, s_type, s_qty, s_total, s_date------------------------------------------------
            for (int i = 0; i < s_id.size(); i++) {
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("ID", 10, 90, paint);
                paint.setColor(Color.BLUE);
                canvas.drawText(String.valueOf(s_id.get(i)), startX, startY + 10, paint);
                startY += 20;
            }

            for (int i = 0; i < s_shop.size(); i++) {
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Shop", 40, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText(String.valueOf(s_shop.get(i)), startX + 30, startY - 70 , paint);
                startY += 20;
            }

            for (int i = 0; i < s_type.size(); i++) {
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Type", 240, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.RIGHT);

                canvas.drawText(String.valueOf(s_type.get(i)), startX + 230, startY - 150, paint);
                startY += 20;
            }

            for (int i = 0; i < s_date.size(); i++) {
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Date", 330, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.RIGHT);

                canvas.drawText(String.valueOf(s_date.get(i)), startX + 320, startY - 230, paint);
                startY += 20;
            }

            for (int i = 0; i < s_qty.size(); i++) {
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Qty", 380, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.RIGHT);

                canvas.drawText(String.valueOf(s_qty.get(i)), startX + 370, startY - 310, paint);
                startY += 20;
            }

            //finish the page
            pdfDocument.finishPage(myPage);

            String targetPdf = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
            File file = new File(targetPdf + "/", "Sales.pdf");
            FileOutputStream outputStream = new FileOutputStream(file);
            pdfDocument.writeTo(outputStream);
            Toast.makeText(activity, "PDF file generated successfully!", Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close document
        pdfDocument.close();
    }


    private void defect_createPdf() {
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
            canvas.drawText("Product Defects", pageInfo.getPageWidth()/2, 30, paint);

            paint.setTextSize(8.0f);
            paint.setColor(Color.rgb(122, 119, 119));
            canvas.drawText("This is the product defects through this week", pageInfo.getPageWidth()/2, 45, paint);

            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(8.0f);
            paint.setColor(Color.rgb(122, 119, 119));
            canvas.drawText("Defects Information", 10, 70, paint);

            int startX = 10;
            int startY = 100;

            // defects---------------m_id, m_shop, m_type, defect, fixed;-----------------------------------------
            for (int i = 0; i < m_id.size(); i++) {
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("ID", 10, 90, paint);
                paint.setColor(Color.BLUE);
                canvas.drawText(String.valueOf(m_id.get(i)), startX, startY + 10, paint);
                startY += 20;
            }

            for (int i = 0; i < m_shop.size(); i++) {
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Shop", 40, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText(String.valueOf(m_shop.get(i)), startX + 30, startY - 70 , paint);
                startY += 20;
            }

            for (int i = 0; i < m_type.size(); i++) {
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Type", 240, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.RIGHT);

                canvas.drawText(String.valueOf(m_type.get(i)), startX + 230, startY - 150, paint);
                startY += 20;
            }

            for (int i = 0; i < defect.size(); i++) {
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Defect", 330, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.RIGHT);

                canvas.drawText(String.valueOf(defect.get(i)), startX + 320, startY - 230, paint);
                startY += 20;
            }

            for (int i = 0; i < fixed.size(); i++) {
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextScaleX(1.5f);
                canvas.drawText("Fixed?", 380, 90, paint);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.RIGHT);

                canvas.drawText(String.valueOf(fixed.get(i)), startX + 370, startY - 310, paint);
                startY += 20;
            }

            //finish the page
            pdfDocument.finishPage(myPage);

            String targetPdf = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
            File file = new File(targetPdf + "/", "Defects.pdf");
            FileOutputStream outputStream = new FileOutputStream(file);
            pdfDocument.writeTo(outputStream);
            Toast.makeText(activity, "PDF file generated successfully!", Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close document
        pdfDocument.close();
    }


    //data_from_sqlite---------------------------------------------
    @SuppressLint("Range")
    public void display_BalanceInPdf(){
        Cursor cursor = database.balance_readAllData();
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            while(true) {
                b_id.add(cursor.getString(cursor.getColumnIndex("id")).toString());
                b_type.add(cursor.getString(cursor.getColumnIndex("category")).toString());
                b_qty.add(cursor.getString(cursor.getColumnIndex("unit_num")).toString());
                price.add(cursor.getString(cursor.getColumnIndex("unit_price")).toString());
                if(!cursor.moveToNext()) break;
            }
        }
    }


    @SuppressLint("Range")
    public void display_SalesInPdf(){
        Cursor cursor2 = database.sales_readAllData();
        cursor2.moveToFirst();
        if(cursor2.getCount() > 0) {
            while(true) {
                s_id.add(cursor2.getString(cursor2.getColumnIndex("id")).toString());
                s_shop.add(cursor2.getString(cursor2.getColumnIndex("shop_name")).toString());
                s_type.add(cursor2.getString(cursor2.getColumnIndex("unit_type")).toString());
                s_qty.add(cursor2.getString(cursor2.getColumnIndex("sales_qty")).toString());
                s_total.add(cursor2.getString(cursor2.getColumnIndex("total_price")).toString());
                s_date.add(cursor2.getString(cursor2.getColumnIndex("date")).toString());
                if(!cursor2.moveToNext()) break;
            }
        }
    }


    @SuppressLint("Range")
    public void display_DefectInPdf(){
        Cursor cursor3 = database.maintenance_readAllData();
        cursor3.moveToFirst();
        if(cursor3.getCount() > 0) {
            while(true) {
                m_id.add(cursor3.getString(cursor3.getColumnIndex("id")).toString());
                m_shop.add(cursor3.getString(cursor3.getColumnIndex("maintenance_shop_name")).toString());
                m_type.add(cursor3.getString(cursor3.getColumnIndex("maintenance_unit_type")).toString());
                defect.add(cursor3.getString(cursor3.getColumnIndex("defect_type")).toString());
                fixed.add(cursor3.getString(cursor3.getColumnIndex("is_fixed")).toString());
                if(!cursor3.moveToNext()) break;
            }
        }
    }


}
