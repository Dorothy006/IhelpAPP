package com.example.tayor.servpro.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tayor.servpro.Model.Provider;

public class DatabaseLayer extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Ihelp.db";
    public static final String TABLE_NAME = "providers";
    public static final String  COL_0 = "ID";
    public static final String  COL_1 = "COMPANY_NAME ";
    public static final String  COL_2 = "EMAIL";
    public static final String  COL_3 = "PHONE";
    public static final String  COL_4 = "LINE1";
    public static final String  COL_5 = "LINE2";
    public static final String  COL_6 = "COUNTRY";
    public static final String  COL_7 = "CITY";
    public static final String  COL_8 = "POSTAL";
    public static final String  COL_9 = "PASSWORD";

    public DatabaseLayer(Context context){
        this(context,DATABASE_NAME,null,1);
        //
    }

    public DatabaseLayer( Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db,1,1);
        String query = "CREATE TABLE "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,COMPANY_NAME TEXT,EMAIL TEXT UNIQUE,PHONE TEXT,LINE1 TEXT,LINE2 TEXT,COUNTRY TEXT,CITY TEXT,POSTAL TEXT,PASSWORD TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
    db.execSQL(query);
    onCreate(db);
    }

    public boolean insertProvider(Provider provider){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1,provider.getCompany_name());
        cv.put(COL_2,provider.getEmail());
        cv.put(COL_3,provider.getPhone());
        cv.put(COL_4,provider.getLine1());
        cv.put(COL_5,provider.getLine2());
        cv.put(COL_6,provider.getCountry());
        cv.put(COL_7,provider.getCity());
        cv.put(COL_8,provider.getPostal());
        cv.put(COL_9,provider.getPassword());
        return db.insert(TABLE_NAME,null,cv) >= 0;
    }
}
