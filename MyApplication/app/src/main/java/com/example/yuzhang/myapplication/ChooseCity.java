package com.example.yuzhang.myapplication;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChooseCity extends AppCompatActivity {
    private String[]data;
    private List<String> list=new ArrayList<String>();
    private TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        CityJSONUtil cityJSONUtil=new CityJSONUtil(ChooseCity.this);
        //cityJSONUtil.getJsonInfoByOKHttpClient();
        SQLiteDatabase db=cityJSONUtil.getDb();
        Cursor cursor=db.query("tb_province1",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("province_name"));
                list.add(name);
                Log.d("ChooseProvince",name);
            }while(cursor.moveToNext());
        }
        data=new String[list.size()];
        list.toArray(data);
      // Log.d("data",data[0]);
        cursor.close();

        ListView mlistView=(ListView)findViewById(R.id.list_view);

        ArrayAdapter<String>adapter=new ArrayAdapter<String>(ChooseCity.this,android.R.layout.simple_list_item_1,data);
        mlistView.setAdapter(adapter);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapterView, View view,int i,long I)
            {
                String cityName=data[i];
                SharedPreferences settings=(SharedPreferences)getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor=settings.edit();
                editor.putString("provinceName",cityName);
                editor.putInt("provinceId",i+1);
                editor.apply();
                finish();
            }
        });
    }
}
