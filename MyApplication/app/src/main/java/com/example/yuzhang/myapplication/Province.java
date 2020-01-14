package com.example.yuzhang.myapplication;

import java.util.List;

/**
 * Created by yuzhang on 2019/10/21.
 */

public class Province {
    //城市名集合
    private List<City> cityList;
    //省份名
    private String provinceName;
    private int id;
    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
