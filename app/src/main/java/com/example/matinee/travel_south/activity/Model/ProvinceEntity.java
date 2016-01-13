package com.example.matinee.travel_south.activity.Model;

/**
 * Created by KaowNeaw on 1/11/2016.
 */
public class ProvinceEntity {
    int province_id;
    String provinceName;
    String province_active;

    public ProvinceEntity(int province_id, String provinceName, String province_active) {
        this.province_id = province_id;
        this.provinceName = provinceName;
        this.province_active = province_active;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getProvince_name() {
        return provinceName;
    }

    public void setProvince_name(String province_name) {
        this.provinceName = province_name;
    }

    public String getProvince_active() {
        return province_active;
    }

    public void setProvince_active(String province_active) {
        this.province_active = province_active;
    }
}
