package com.example.matinee.travel_south.activity.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class LocationEntity implements Parcelable {

    private int Location_id;
    private String NameTH;
    private String AddressTH;
    private String NameEng;
    private String AddressEng;
    private String Tel;
    private String AttDetails_Th;
    private String AttDetails_Eng;
    private String AttDetails_Chi;
    private String Website;
    private float Latitude;
    private float Longtitude;
    private float distance;
    private int Type_id;
    private int Province_id;
    private String ImageLocationFile;
    private List<Journey> listJorney;
    private List<ImageLocation> listImage;
    private String NameChi;
    private String AddressChi;

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getAttDetails_Th() {
        return AttDetails_Th;
    }

    public void setAttDetails_Th(String attDetails_Th) {
        AttDetails_Th = attDetails_Th;
    }

    public String getAttDetails_Eng() {
        return AttDetails_Eng;
    }

    public void setAttDetails_Eng(String attDetails_Eng) {
        AttDetails_Eng = attDetails_Eng;
    }

    public String getAttDetails_Chi() {
        return AttDetails_Chi;
    }

    public void setAttDetails_Chi(String attDetails_Chi) {
        AttDetails_Chi = attDetails_Chi;
    }

    public int getLocation_id() {
        return Location_id;
    }

    public void setLocation_id(int location_id) {
        Location_id = location_id;
    }

    public String getNameTH() {
        return NameTH;
    }

    public void setNameTH(String nameTH) {
        NameTH = nameTH;
    }

    public String getAddressTH() {
        return AddressTH;
    }

    public void setAddressTH(String addressTH) {
        AddressTH = addressTH;
    }

    public String getNameEng() {
        return NameEng;
    }

    public void setNameEng(String nameEng) {
        NameEng = nameEng;
    }

    public String getAddressEng() {
        return AddressEng;
    }

    public void setAddressEng(String addressEng) {
        AddressEng = addressEng;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(float longtitude) {
        Longtitude = longtitude;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getType_id() {
        return Type_id;
    }

    public void setType_id(int type_id) {
        Type_id = type_id;
    }

    public int getProvince_id() {
        return Province_id;
    }

    public void setProvince_id(int province_id) {
        Province_id = province_id;
    }

    public String getImageLocationFile() {
        return ImageLocationFile;
    }

    public void setImageLocationFile(String imageLocationFile) {
        ImageLocationFile = imageLocationFile;
    }

    public List<Journey> getListJorney() {
        return listJorney;
    }

    public void setListJorney(List<Journey> listJorney) {
        this.listJorney = listJorney;
    }

    public List<ImageLocation> getListImage() {
        return listImage;
    }

    public void setListImage(List<ImageLocation> listImage) {
        this.listImage = listImage;
    }

    public String getNameChi() {
        return NameChi;
    }

    public void setNameChi(String nameChi) {
        NameChi = nameChi;
    }

    public String getAddressChi() {
        return AddressChi;
    }

    public void setAddressChi(String addressChi) {
        AddressChi = addressChi;
    }

    public LocationEntity(int location_id, String nameTH, String addressTH, String nameEng, String addressEng, String tel, float latitude, float longtitude, float distance, int type_id, int province_id, String imageLocationFile, List<Journey> listJorney, List<ImageLocation> listImage, String nameChi, String addressChi) {
        Location_id = location_id;
        NameTH = nameTH;
        AddressTH = addressTH;
        NameEng = nameEng;
        AddressEng = addressEng;
        Tel = tel;
        Latitude = latitude;
        Longtitude = longtitude;
        this.distance = distance;
        Type_id = type_id;
        Province_id = province_id;
        ImageLocationFile = imageLocationFile;
        this.listJorney = listJorney;
        this.listImage = listImage;
        NameChi = nameChi;
        AddressChi = addressChi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Location_id);
        dest.writeString(this.NameTH);
        dest.writeString(this.AddressTH);
        dest.writeString(this.NameEng);
        dest.writeString(this.AddressEng);
        dest.writeString(this.Tel);
        dest.writeFloat(this.Latitude);
        dest.writeFloat(this.Longtitude);
        dest.writeFloat(this.distance);
        dest.writeInt(this.Type_id);
        dest.writeInt(this.Province_id);
        dest.writeString(this.ImageLocationFile);
        dest.writeString(this.NameChi);
        dest.writeString(this.AddressChi);
    }

    protected LocationEntity(Parcel in) {
        this.Location_id = in.readInt();
        this.NameTH = in.readString();
        this.AddressTH = in.readString();
        this.NameEng = in.readString();
        this.AddressEng = in.readString();
        this.Tel = in.readString();
        this.Latitude = in.readFloat();
        this.Longtitude = in.readFloat();
        this.distance = in.readFloat();
        this.Type_id = in.readInt();
        this.Province_id = in.readInt();
        this.ImageLocationFile = in.readString();
        this.NameChi = in.readString();
        this.AddressChi = in.readString();
    }

    public static final Creator<LocationEntity> CREATOR = new Creator<LocationEntity>() {
        public LocationEntity createFromParcel(Parcel source) {
            return new LocationEntity(source);
        }

        public LocationEntity[] newArray(int size) {
            return new LocationEntity[size];
        }
    };
}
