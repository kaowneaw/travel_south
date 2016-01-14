package com.example.matinee.travel_south.activity.Model;

/**
 * Created by KaowNeaw on 1/11/2016.
 */
public class ProvinceEntity {
    int Province_id;
    String ProvinceName;
    String ProvinceActive;


    public ProvinceEntity(int province_id, String provinceName, String provinceActive) {
        Province_id = province_id;
        ProvinceName = provinceName;
        ProvinceActive = provinceActive;
    }

    public int getProvince_id() {
        return Province_id;
    }

    public void setProvince_id(int province_id) {
        Province_id = province_id;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getProvinceActive() {
        return ProvinceActive;
    }

    public void setProvinceActive(String provinceActive) {
        ProvinceActive = provinceActive;
    }
}
