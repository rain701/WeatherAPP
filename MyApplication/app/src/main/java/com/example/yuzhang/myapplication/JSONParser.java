package com.example.yuzhang.myapplication;

/**
 * Created by yuzhang on 2019/9/27.
 */

import org.json.JSONArray;
import org.json.JSONObject;



        import org.json.JSONException;
        import org.json.JSONObject;

public class JSONParser {
    public WeatherInfo WeatherParse (String weatherString) {
        WeatherInfo wi = new WeatherInfo();
        try {
            JSONObject jo = new JSONObject(weatherString);
            JSONArray joWeather = jo.getJSONArray("HeWeather6");
            JSONObject wc=joWeather.getJSONObject(0);
            JSONObject now=wc.getJSONObject("now");
            JSONObject state=wc.getJSONObject("update");
            wi.setCurrentTemp(now.getString("tmp"));
            wi.setDescription(now.getString("cond_txt"));
            wi.setWindDir(now.getString("wind_dir"));
            wi.setWindGrade(now.getString("wind_sc"));
            wi.setPublishTime(state.getString("loc"));
            wi.setWeatherCode(now.getString("cond_code"));

            //wi.setLowTemp(joWeather.getString("temp2"));
            //wi.setHighTemp(joWeather.getString("temp1"));
           // wi.setDescription(joWeather.getString("weather"));
            //wi.setPublishTime(joWeather.getString("ptime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wi;
    }
}
