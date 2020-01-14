package com.example.yuzhang.myapplication;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
import com.example.yuzhang.myapplication.HTTPRetrieval;
import com.example.yuzhang.myapplication.JSONParser;
import com.example.yuzhang.myapplication.WeatherInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txt5;
    private Handler hd;
    private ImageView image;
    private Calendar calendar;
    private Timer timer;
    private TimerTask task;
    public static final int UPDATE_TEXT=1;
    private WeatherInfo finalWi;
    private int year;
    private int month;
    private int day;
    private int weekday;
    private int drwableId;
    private String cityCode="101011100";
    private String cityName;
    private String weatherId="101011100";
    private String countyName;
    public DrawerLayout drawerLayout;
//    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //定位功能
//        mLocationClient=new LocationClient(getApplicationContext());
//        mLocationClient.registerLocationListener(new MyLocationListener());
//        List<String> permissionList=new ArrayList<>();
//        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
//                PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!=
//                PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.READ_PHONE_STATE);
//        }
//        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
//                PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        }
//        if(!permissionList.isEmpty()){
//            String []permissions=permissionList.toArray(new String[permissionList.size()]);
//            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
//        }
//        else{
//            requestLocation();
//        }



        txt1 = (TextView) findViewById(R.id.textView2);
        txt2=(TextView) findViewById(R.id.wea);
        txt3=(TextView)findViewById(R.id.wind);
        txt4=(TextView)findViewById(R.id.textView);
        image=(ImageView)findViewById(R.id.image_view);
        final String []arr = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        hd=new  Handler(){
            public void handleMessage(Message msg){
                switch(msg.what){
                    case UPDATE_TEXT:
                        txt1.setText( finalWi.getCurrentTemp());
                        txt2.setText(finalWi.getDescription());
                        txt3.setText(finalWi.getWindDir()+finalWi.getWindGrade()+"级");
                        if(drwableId!=0)image.setImageResource(drwableId);
                        else image.setImageResource(R.drawable.p102);
                        txt4.setText(year+"-"+month+"-"+day+" "+arr[weekday-1]);
                      //  txt5.setText(finalWi.getCity());
                        break;
                    default:
                        break;

                }
            }
        };
        Button button1=(Button)findViewById(R.id.chooseCity);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //Intent intent=new Intent(MainActivity.this,ChooseCity.class);
                //startActivity(intent);
                drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        //Intent intent=new Intent(this,UpdateService.class);
        //startService(intent);
        timer=new Timer();
        task= new TimerTask(){
            @Override
            public void run() {
                HttpThread ht=new HttpThread();
                ht.start();
            }
        };
        timer.schedule(task,5,5*60*1000);

    Intent intent=new Intent(MainActivity.this,UpdateService.class);
    startService(intent);




    }
//    private void requestLocation(){
//        //initLocation();
//        mLocationClient.start();
//    }
//    private void initLocation(){
//        LocationClientOption option=new LocationClientOption();
//        option.setScanSpan(5000);
//        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
//        mLocationClient.setLocOption(option);
//    }
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        mLocationClient.stop();
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,String []permissions,int[]grantResults){
//        switch (requestCode){
//            case 1:
//                if(grantResults.length>0){
//                    for(int result:grantResults){
//                        if(result!=PackageManager.PERMISSION_GRANTED){
//                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
//                            finish();
//                            return;
//                        }
//                    }
//                    requestLocation();
//                }else{
//                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
//            default:
//        }
//    }
//    public class MyLocationListener extends BDAbstractLocationListener {
//        @Override
//        public void onReceiveLocation(final BDLocation location){
//
//                    //StringBuilder currentPosition=new StringBuilder();
//                    //currentPosition.append("市：").append(location.getCity()).append("\n");
//                    cityName=location.getCity();
//                    countyName=location.getDistrict();
//                    Log.d("cityName",cityName);
//                    Log.d("countyName",countyName);
//
//        }
//    }
    @Override
    protected void onStart() {
        super.onStart();
        //SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
       // cityName=pref.getString("cityName","大兴区");
       // txt5=(TextView)findViewById(R.id.district);
       // txt5.setText(cityName);
        //Log.d("MAIN_ACTIVITY", cityName);
    }
    public void finishChoose(String weatherId,String name){
        this.weatherId=weatherId.substring(2);
        Log.d("weatherId",weatherId);
        //this.countyName=CN;
        txt5=(TextView)findViewById(R.id.district);
        txt5.setText(name);
        HttpThread ht=new HttpThread();
        ht.start();
        return;

    }
//获取天气网络接口数据
    public class HttpThread extends Thread {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            super.run();
            HTTPRetrieval hr = new HTTPRetrieval();
            // 城市代码
            String weatherString = hr.HTTPWeatherGET(weatherId);

             WeatherInfo wi = new WeatherInfo();
            JSONParser jp = new JSONParser();
            // 调用自定义的 JSON 解析类解析获取的 JSON 数据
            wi = jp.WeatherParse(weatherString);

            finalWi = wi;
            calendar=Calendar.getInstance();
            year=calendar.get(Calendar.YEAR);
            month=calendar.get(Calendar.MONTH)+1;
            day=calendar.get(Calendar.DAY_OF_MONTH);
            weekday=calendar.get(Calendar.DAY_OF_WEEK);
            drwableId=getResources().getIdentifier("p"+finalWi.getWeatherCode(),"drawable",getPackageName());
            new Thread(new Runnable(){
                @Override
                public void run(){
                    Message message=new Message();
                    message.what=UPDATE_TEXT;
                    hd.sendMessage(message);
                }
            }).start();

        }
    }







}

