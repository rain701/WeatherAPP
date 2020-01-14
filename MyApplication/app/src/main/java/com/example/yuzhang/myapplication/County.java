package com.example.yuzhang.myapplication;

/**
 * Created by yuzhang on 2019/10/21.
 */

public class County {
    //县城id
    private int id;
    //县城名
    private String countyName;
    //城市id
    private int cityId;

    private String weatherId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    public void setWeatherId(String wid){
        this.weatherId=wid;
    }
    public String getWeatherId(){
        return weatherId;
    }
}
