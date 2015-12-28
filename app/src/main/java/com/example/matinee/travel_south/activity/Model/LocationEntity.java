package com.example.matinee.travel_south.activity.Model;


public class LocationEntity {

    private int id;
    private String location_name;
    private double lat;
    private double lng;
    private int type_id;
    private int province_id;
    private float distance;

    public LocationEntity(int id, String location_name, double lat, double lng, int type_id, int province_id, float distance) {
        this.id = id;
        this.location_name = location_name;
        this.lat = lat;
        this.lng = lng;
        this.type_id = type_id;
        this.province_id = province_id;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
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
}
