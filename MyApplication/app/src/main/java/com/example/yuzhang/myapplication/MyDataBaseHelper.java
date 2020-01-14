package com.example.yuzhang.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yuzhang on 2019/10/21.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {
    //创建省份表
    private static final String CREATE_PROVINCE="create table tb_province1("+
            "id integer primary key autoincrement,"+
            "province_name varchar(20),"+
            "province_code integer)";
    //创建城市表
    private static final String CREATE_CITY="create table tb_city1("+
            "id integer primary key autoincrement,"+
            "city_name varchar(20),"+
            "city_code integer,"+
            "province_id integer)";
    //创建县城表
    private static final String CREATE_COUNTY="create table tb_county1("+
            "id integer primary key autoincrement,"+
            "county_name varchar(20),"+
            "county_code integer,"+
            "weather_id varchar(20),"+
            "city_id integer)";
    //构造函数
    public MyDataBaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tb_province");
        db.execSQL("drop table if exists tb_city");
        db.execSQL("drop table if exists tb_county");
        onCreate(db);
    }
}
