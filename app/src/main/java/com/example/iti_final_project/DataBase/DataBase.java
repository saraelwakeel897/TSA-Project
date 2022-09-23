package com.example.iti_final_project.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Sales_Assistant.db";
    private static final int DATABASE_VERSION = 1;

    // balance
    private static final String BALANCE_TABLE = "balance";
    private static final String BALANCE_ID = "id";
    private static final String CATEGORY = "category";
    private static final String UNIT_NUM = "unit_num";
    private static final String UNIT_PRICE = "unit_price";

    //sales
    private static final String SALES_TABLE = "sales";
    private static final String SALES_ID = "id";
    private static final String SHOP_NAME = "shop_name";
    private static final String UNIT_TYPE = "unit_type";
    private static final String SALES_QTY = "sales_qty";
    private static final String TOTAL_PRICE = "total_price";
    private static final String DATE = "date";

    //stores
    private static final String STORES_TABLE = "stores";
    private static final String STORES_ID = "id";
    private static final String STORES_SHOP_NAME = "stores_shop_name";
    private static final String STORES_SHOP_LOCATION = "shop_location";
    private static final String STORES_SHOP_PHONE = "shop_phone";
    private static final String LAST_VISIT_DATE = "last_visit_date";

    // maintenance
    private static final String MAINTENANCE_TABLE = "maintenance";
    private static final String MAINTENANCE_ID = "id";
    private static final String MAINTENANCE_UNIT_TYPE = "maintenance_unit_type";
    private static final String MAINTENANCE_SHOP_NAME = "maintenance_shop_name";
    private static final String DEFECT_TYPE = "defect_type";
    private static final String DEFECT_DESCRIPTION = "defect_description";
    private static final String IS_FIXED = "is_fixed";

    // additional
    private static final String ADDITIONAL_TABLE = "additional_requests";
    private static final String ADDITIONAL_ID = "id";
    private static final String ADDITIONAL_UNIT_TYPE = "additional_unit_type";
    private static final String ADDITIONAL_SHOP_NAME = "additional_shop_name";
    private static final String ADDITIONAL_QUANTITY = "additional_quantity";
    private static final String ADDITIONAL_DATE = "additional_date";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        // balance
        String query = "CREATE TABLE " + BALANCE_TABLE + " (" + BALANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORY + " TEXT, " + UNIT_NUM + " INTEGER, " + UNIT_PRICE + " DOUBLE);";
        DB.execSQL(query);

        // sales
        String query2 = "CREATE TABLE " + SALES_TABLE + " (" + SALES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SHOP_NAME + " TEXT, " + UNIT_TYPE + " TEXT, "+ SALES_QTY + " INTEGER, " + TOTAL_PRICE + " DOUBLE, " + DATE + " TEXT);";
        DB.execSQL(query2);

        // stores
        String query3 = "CREATE TABLE " + STORES_TABLE + " (" + STORES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STORES_SHOP_NAME + " TEXT, " + STORES_SHOP_LOCATION + " TEXT, " + STORES_SHOP_PHONE + " INTEGER, " + LAST_VISIT_DATE + " TEXT);";
        DB.execSQL(query3);

        // maintenance
        String query4 = "CREATE TABLE " + MAINTENANCE_TABLE + " (" + MAINTENANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MAINTENANCE_UNIT_TYPE + " TEXT, " + MAINTENANCE_SHOP_NAME + " TEXT, " + DEFECT_TYPE + " TEXT, " + DEFECT_DESCRIPTION + " TEXT, " + IS_FIXED + " INTEGER DEFAULT 0);";
        DB.execSQL(query4);

        // additional
        String query5 = "CREATE TABLE " + ADDITIONAL_TABLE + " (" + ADDITIONAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ADDITIONAL_UNIT_TYPE + " TEXT, " + ADDITIONAL_SHOP_NAME + " TEXT, " + ADDITIONAL_QUANTITY + " INTEGER, " + ADDITIONAL_DATE + " TEXT);";
        DB.execSQL(query5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS " + BALANCE_TABLE);
        DB.execSQL("DROP TABLE IF EXISTS " + SALES_TABLE);
        DB.execSQL("DROP TABLE IF EXISTS " + STORES_TABLE);
        DB.execSQL("DROP TABLE IF EXISTS " + MAINTENANCE_TABLE);
        DB.execSQL("DROP TABLE IF EXISTS " + ADDITIONAL_TABLE);
        onCreate(DB);
    }

    // balance function
    public void addBalance(String category, int unit_num, double unit_price){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY, category);
        contentValues.put(UNIT_NUM, unit_num);
        contentValues.put(UNIT_PRICE, unit_price);
        long result = DB.insert(BALANCE_TABLE, null, contentValues);
        if(result == -1){
            Toast.makeText(context, "Failed To Add", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor balance_readAllData(){
        String query = "SELECT * FROM " + BALANCE_TABLE;
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = null;
        if(DB != null){
            cursor = DB.rawQuery(query, null);
        }
        return cursor;
    }

    public void balance_updateData(String id_row, String category, String unit_num, String unit_price){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY, category);
        contentValues.put(UNIT_NUM, unit_num);
        contentValues.put(UNIT_PRICE, unit_price);

        long result = DB.update(BALANCE_TABLE, contentValues, "id = ?", new String[]{id_row});
        if(result == -1){
            Toast.makeText(context, "Failed To Update", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void balance_updateQty(String id_row, String unit_num){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UNIT_NUM, unit_num);

        long result = DB.update(BALANCE_TABLE, contentValues, "id = ?", new String[]{id_row});
        if(result == -1){
            Toast.makeText(context, "Failed To Update", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Quantity Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void balance_deleteData(String id_row){
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete(BALANCE_TABLE, "id = ?", new String[]{id_row});
        if(result == -1){
            Toast.makeText(context, "Failed To Deleted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void balance_resetData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UNIT_NUM, "0");

        long result = DB.update(BALANCE_TABLE, contentValues, null, null);
        if(result == -1){
            Toast.makeText(context, "Failed To Update", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "All Unit Numbers Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void balance_deleteAllData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("DELETE FROM " + BALANCE_TABLE);
    }




    // sales function
    public void addSales(String shop_name, String unit_type, int sales_qty, double total_price, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SHOP_NAME, shop_name);
        contentValues.put(UNIT_TYPE, unit_type);
        contentValues.put(SALES_QTY, sales_qty);
        contentValues.put(TOTAL_PRICE, total_price);
        contentValues.put(DATE, date);
        long result = db.insert(SALES_TABLE, null, contentValues);
        if(result == -1){
            Toast.makeText(context, "Failed To Add", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor sales_readAllData(){
        String query2 = "SELECT * FROM " + SALES_TABLE;
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = null;
        if(DB != null){
            cursor = DB.rawQuery(query2, null);
        }
        return cursor;
    }



    // stores function
    public void addStores(String stores_shop_name, String shop_location, int shop_phone, String last_visit_date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STORES_SHOP_NAME, stores_shop_name);
        contentValues.put(STORES_SHOP_LOCATION, shop_location);
        contentValues.put(STORES_SHOP_PHONE, shop_phone);
        contentValues.put(LAST_VISIT_DATE, last_visit_date);
        long result1 = db.insert(STORES_TABLE, null, contentValues);
        if(result1 == -1){
            Toast.makeText(context, "Failed To Add", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor stores_readAllData(){
        String query3 = "SELECT * FROM " + STORES_TABLE;
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = null;
        if(DB != null){
            cursor = DB.rawQuery(query3, null);
        }
        return cursor;
    }

    public void stores_updateDate(String id_row, String last_visit_date){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LAST_VISIT_DATE, last_visit_date);

        long result = DB.update(STORES_TABLE, contentValues, "id = ?", new String[]{id_row});
        if(result == -1){
            Toast.makeText(context, "Failed To Update", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }



    // maintenance function
    public  void addMaintenance(String maintenance_unit_type, String maintenance_shop_name, String defect_type, String defect_description, boolean is_fixed){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MAINTENANCE_UNIT_TYPE, maintenance_unit_type);
        contentValues.put(MAINTENANCE_SHOP_NAME, maintenance_shop_name);
        contentValues.put(DEFECT_TYPE, defect_type);
        contentValues.put(DEFECT_DESCRIPTION, defect_description);
        contentValues.put(IS_FIXED, is_fixed );

        long result = DB.insert(MAINTENANCE_TABLE, null, contentValues);
        if(result == -1){
            Toast.makeText(context, "Failed To Add", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor maintenance_readAllData() {
        String query4 = "SELECT * FROM " + MAINTENANCE_TABLE;
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = null;
        if(DB != null){
            cursor = DB.rawQuery(query4, null);
        }
        return cursor;
    }

    public void maintenance_update_isFixed(String id_row, boolean is_fixed) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IS_FIXED, is_fixed);
        long result = DB.update(MAINTENANCE_TABLE, contentValues, "id = ?", new String[]{id_row});
        if(result == -1){
            Toast.makeText(context, "Failed To Update", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void maintenance_deleteData(String id_row){
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete(MAINTENANCE_TABLE, "id = ?", new String[]{id_row});
        if(result == -1){
            Toast.makeText(context, "Failed To Deleted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }



    // additional function
    public  void addAdditional(String additional_unit_type, String additional_shop_name, int additional_quantity, String additional_date){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDITIONAL_UNIT_TYPE, additional_unit_type);
        contentValues.put(ADDITIONAL_SHOP_NAME, additional_shop_name);
        contentValues.put(ADDITIONAL_QUANTITY, additional_quantity);
        contentValues.put(ADDITIONAL_DATE, additional_date);
        long result = DB.insert(ADDITIONAL_TABLE, null, contentValues);
        if(result == -1){
            Toast.makeText(context, "Failed To Add", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor additional_readAllData() {
        String query5 = "SELECT * FROM " + ADDITIONAL_TABLE;
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = null;
        if(DB != null){
            cursor = DB.rawQuery(query5, null);
        }
        return cursor;
    }


}
