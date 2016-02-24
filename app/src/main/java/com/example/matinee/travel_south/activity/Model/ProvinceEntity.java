package com.example.matinee.travel_south.activity.Model;

/**
 * Created by KaowNeaw on 1/11/2016.
 */
public class ProvinceEntity {

    private int Province_id;
    private String ProvinceName;
    private String ProvinceActive;
    private String ProvinceEng;
    private String ProvinceChina;

    public ProvinceEntity(int province_id, String provinceName, String provinceActive, String provinceEng, String provinceChina) {
        Province_id = province_id;
        ProvinceName = provinceName;
        ProvinceActive = provinceActive;
        ProvinceEng = provinceEng;
        ProvinceChina = provinceChina;
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

    public String getProvinceEng() {
        return ProvinceEng;
    }

    public void setProvinceEng(String provinceEng) {
        ProvinceEng = provinceEng;
    }

    public String getProvinceChina() {
        return ProvinceChina;
    }

    public void setProvinceChina(String provinceChina) {
        ProvinceChina = provinceChina;
    }
}
