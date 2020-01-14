package com.example.yuzhang.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by yuzhang on 2019/10/21.
 */

public class CityJSONUtil {
    //获取全国各省市县的服务器
    private String mUrl="http://guolin.tech/api/china";
    private JSONArray mJsonInfoProvince;
    private JSONArray mJsonInfoCity;
    private JSONArray mJsonInfoCounty;
    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;

    public CityJSONUtil(Context context){
        myDataBaseHelper=new MyDataBaseHelper(context,"cityInfo.db",null,16);
        db=myDataBaseHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    //通过网络获取json数据
    public void getJsonInfoByOKHttpClient(){
        new Thread(new Runnable(){
            ;
            @Override
            public void run(){

                mJsonInfoProvince=HttpCityInformation(mUrl);
                parseProvinceWithJSON(mJsonInfoProvince);

                int j=1;
                for(int i=1;i<=mJsonInfoProvince.length();++i){
                    String s=mUrl+"/"+i;
                    mJsonInfoCity=HttpCityInformation(s);
                    parseCityInfoWithJSON(mJsonInfoCity,i);
                    int k=j;
                    for(;j<k+mJsonInfoCity.length();j++){
                        String t=s+"/"+j;
                        mJsonInfoCounty=HttpCityInformation(t);
                        parseCountyInfoWithJSON(mJsonInfoCounty,j);
                    }
                }
                db.close();






            }

        }).start();
    }
    //解析省份数据
    private void parseProvinceWithJSON(JSONArray jsonArrayProvince)  {
        ContentValues values=new ContentValues();

            try {
                for (int i = 0; i < jsonArrayProvince.length(); ++i) {
                    JSONObject jsonObject = (JSONObject) jsonArrayProvince.get(i);
                    int id = Integer.valueOf(jsonObject.get("id").toString());
                    String provinceName = jsonObject.get("name").toString();
                    db.execSQL("insert into tb_province1(id,province_name)values(" + id + ",'" + provinceName + "')");
                    values.put("id", jsonObject.get("id").toString());
                    values.put("province_name", jsonObject.get("name").toString());

                    db.insert("tb_province1", null, values);
                    //监测
                    Log.d("data_", "CityJSON:jsonObjectProvince-->" + jsonObject.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

    }
    //解析城市数据
    private void parseCityInfoWithJSON(JSONArray jsonArrayCity,int provinceId)  {
        ContentValues values=new ContentValues();
            try {
                for (int i = 0; i < jsonArrayCity.length(); ++i) {
                    JSONObject jsonObject = (JSONObject) jsonArrayCity.get(i);
                    values.put("id", jsonObject.get("id").toString());
                    values.put("city_name", jsonObject.get("name").toString());
                    values.put("province_id",provinceId);
                    db.insert("tb_city1", null, values);
                    //监测
                    Log.d("data_", "CityJSON:jsonObjectCity-->" + jsonObject.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

    }
    //解析县城数据
    private void parseCountyInfoWithJSON(JSONArray jsonArrayCounty,int cityId) {
        ContentValues values=new ContentValues();
       try {
           for (int i = 0; i < jsonArrayCounty.length(); ++i) {
               JSONObject jsonObject = (JSONObject) jsonArrayCounty.get(i);
               values.put("id", jsonObject.get("id").toString());
               values.put("county_name", jsonObject.get("name").toString());
               values.put("weather_id", jsonObject.get("weather_id").toString());
               values.put("city_id", cityId);
               db.insert("tb_county1", null, values);
               //监测
               Log.d("data_", "CityJSON:jsonObjectCounty-->" + jsonObject.toString());
           }
       }catch (Exception e){
           e.printStackTrace();
       }

    }
    private JSONArray HttpCityInformation(String s) {
        String mUrl = s;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String mJsonInfoProvince;
        JSONArray jsonArray=null;
        try {
            URL url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            //对输入流进行读取
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            mJsonInfoProvince = response.toString();
            try{
                jsonArray=new JSONArray(mJsonInfoProvince);

            }catch (Exception e){
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return jsonArray;

    }
}

