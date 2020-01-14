package com.example.yuzhang.myapplication;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;

import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;


public class UpdateService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        updateWeather();//调用更新天气数据接口
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour =  30* 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i=new Intent(this,UpdateService.class);
        PendingIntent pi= PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }

    private void updateWeather() {

                Log.d("UpdateService","HELLLLLO!!!!!!!!!!");

    }






}
