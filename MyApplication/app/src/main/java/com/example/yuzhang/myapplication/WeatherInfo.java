package com.example.yuzhang.myapplication;

/**
 * Created by yuzhang on 2019/9/27.
 */



public class WeatherInfo {
    // 数据接收
    private String city;
    private String description;
    private String highTemp;
    private String lowTemp;
    private String publishTime;
    private String currentTemp;
    private String windDir;
    private String windGrade;
    private String weatherCode;
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
    public String getCurrentTemp(){
        return currentTemp;
    }
    public void setCurrentTemp(String currentTemp){
        this.currentTemp=currentTemp;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindGrade() {
        return windGrade;
    }

    public void setWindGrade(String windGrade) {
        this.windGrade = windGrade;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }
}
