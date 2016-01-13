package com.example.matinee.travel_south.activity.Model;


public class LocationEntity {

    private int location_id;
    private String locationNameTH;
    private double lat;
    private double lng;
    private int type_id;
    private int province_id;
    private float distance;
    private String ImageLocationName;
    private String addressTH;

    public LocationEntity(int location_id, String locationNameTH, double lat, double lng, int type_id, int province_id, float distance, String imageLocationName, String addressTH) {
        this.location_id = location_id;
        this.locationNameTH = locationNameTH;
        this.lat = lat;
        this.lng = lng;
        this.type_id = type_id;
        this.province_id = province_id;
        this.distance = distance;
        ImageLocationName = imageLocationName;
        this.addressTH = addressTH;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getLocationNameTH() {
        return locationNameTH;
    }

    public void setLocationNameTH(String locationNameTH) {
        this.locationNameTH = locationNameTH;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getImageLocationName() {
        return ImageLocationName;
    }

    public void setImageLocationName(String imageLocationName) {
        ImageLocationName = imageLocationName;
    }

    public String getAddressTH() {
        return addressTH;
    }

    public void setAddressTH(String addressTH) {
        this.addressTH = addressTH;
    }
}
