package com.example.yuzhang.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;




public class cityFragment extends Fragment {
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList=new ArrayList<>();
    //省列表
    private List<Province> provinceList=new ArrayList<Province>() ;
    //城市列表
    private List<City> cityList=new ArrayList<City>();
    //县区列表
    private List<County> countyList=new ArrayList<County>();
    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;
    private int currentLevel;
    private static boolean hasData= false;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_choose_city,container,false);
        titleText=(TextView) view.findViewById(R.id.title_text);
        backButton=(Button) view.findViewById(R.id.back_button);
        listView=(ListView) view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>parent,View view,int position,long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    for (City city : cityList) {
                        if (city.getCityName() == dataList.get(position)) {
                            selectedCity = city;
                            break;
                        }

                    }

                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    selectedCounty = countyList.get(position);
                    String weatherId = selectedCounty.getWeatherId();
                    MainActivity activity=(MainActivity) getActivity();
                    activity.finishChoose(weatherId,selectedCounty.getCountyName());
                    activity.drawerLayout.closeDrawers();

                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(currentLevel==LEVEL_COUNTY){
                    queryCities();
                }
                else if(currentLevel==LEVEL_CITY){
                    queryProvinces();
                }
                else{
                    MainActivity activity=(MainActivity) getActivity();
                    activity.drawerLayout.closeDrawers();
                }
            }

        });
        queryProvinces();


    }
    private void queryProvinces(){
        provinceList.clear();
        backButton.setVisibility(View.VISIBLE);
        titleText.setText("中国");
        CityJSONUtil cityJSONUtil=new CityJSONUtil(this.getActivity());
        if(hasData=false) {cityJSONUtil.getJsonInfoByOKHttpClient();hasData=true;}
        SQLiteDatabase db=cityJSONUtil.getDb();
        Cursor cursor=db.query("tb_province1",null,null,null,null,null,null);
        int i=1;
        if(cursor.moveToFirst()){
            do{
                Province temp=new Province();
                temp.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                temp.setId(i++);
                provinceList.add(temp);
                Log.d("ChooseProvince",temp.getProvinceName());
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        dataList.clear();
        for(Province province:provinceList){
            dataList.add(province.getProvinceName());

        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel=LEVEL_PROVINCE;
        // Log.d("data",data[0]);

    }
    private void queryCities(){
        cityList.clear();
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        CityJSONUtil cityJSONUtil=new CityJSONUtil(this.getActivity());
        SQLiteDatabase db=cityJSONUtil.getDb();
        //String [] arg={String.valueOf(selectedProvince.getId())};
        Cursor cursor=db.query("tb_city1",null,null,null,null,null,null);
        int i=1;
        if(cursor.moveToFirst()){
            do{
                City temp=new City();
                temp.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                temp.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                temp.setId(i++);
                cityList.add(temp);
                Log.d("ChooseProvince",temp.getCityName());
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dataList.clear();
        for(City city:cityList){
            if(city.getProvinceId()==selectedProvince.getId()){
                dataList.add(city.getCityName());
            }
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel=LEVEL_CITY;

    }
    private void queryCounties(){
        countyList.clear();
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        CityJSONUtil cityJSONUtil=new CityJSONUtil(this.getActivity());
        SQLiteDatabase db=cityJSONUtil.getDb();
        String [] arg={String.valueOf(selectedCity.getId())};
        Log.d("cityid",arg[0]);
        Cursor cursor=db.query("tb_county1",null,"city_id=?",arg,null,null,null);
        if(cursor.moveToFirst()){
            do{
                County temp=new County();
                temp.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                temp.setWeatherId(cursor.getString(cursor.getColumnIndex("weather_id")));
                //temp.setId(i++);
                countyList.add(temp);
                Log.d("ChooseProvince",temp.getCountyName());
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dataList.clear();
        for(County county:countyList){
            dataList.add(county.getCountyName());
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel=LEVEL_COUNTY;
    }
}
