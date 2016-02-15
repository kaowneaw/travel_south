package com.example.matinee.travel_south.activity.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KaowNeaw on 2/1/2016.
 */
public class FeedEntity implements Parcelable {

    private int Feeds_id;
    private String Member_id;
    private int Location_id;
    private String Member_name;
    private String Content;
    private String Img;
    private String Date_time;
    private String NameTH;
    private String AddressTH;
    private String NameEng;
    private String AddressEng;
    private String Tel;
    private double Latitude;
    private double Longtitude;
    private int Type_id;
    private int Province_id;
    private String Website;

    public FeedEntity(int feeds_id, String member_id, int location_id, String member_name, String content, String img, String date_time, String nameTH, String addressTH, String nameEng, String addressEng, String tel, double latitude, double longtitude, int type_id, int province_id, String website) {
        Feeds_id = feeds_id;
        Member_id = member_id;
        Location_id = location_id;
        Member_name = member_name;
        Content = content;
        Img = img;
        Date_time = date_time;
        NameTH = nameTH;
        AddressTH = addressTH;
        NameEng = nameEng;
        AddressEng = addressEng;
        Tel = tel;
        Latitude = latitude;
        Longtitude = longtitude;
        Type_id = type_id;
        Province_id = province_id;
        Website = website;
    }


    public int getFeeds_id() {
        return Feeds_id;
    }

    public void setFeeds_id(int feeds_id) {
        Feeds_id = feeds_id;
    }

    public String getMember_id() {
        return Member_id;
    }

    public void setMember_id(String member_id) {
        Member_id = member_id;
    }

    public int getLocation_id() {
        return Location_id;
    }

    public void setLocation_id(int location_id) {
        Location_id = location_id;
    }

    public String getMember_name() {
        return Member_name;
    }

    public void setMember_name(String member_name) {
        Member_name = member_name;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getDate_time() {
        return Date_time;
    }

    public void setDate_time(String date_time) {
        Date_time = date_time;
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

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(double longtitude) {
        Longtitude = longtitude;
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

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Feeds_id);
        dest.writeString(this.Member_id);
        dest.writeInt(this.Location_id);
        dest.writeString(this.Member_name);
        dest.writeString(this.Content);
        dest.writeString(this.Img);
        dest.writeString(this.Date_time);
        dest.writeString(this.NameTH);
        dest.writeString(this.AddressTH);
        dest.writeString(this.NameEng);
        dest.writeString(this.AddressEng);
        dest.writeString(this.Tel);
        dest.writeDouble(this.Latitude);
        dest.writeDouble(this.Longtitude);
        dest.writeInt(this.Type_id);
        dest.writeInt(this.Province_id);
        dest.writeString(this.Website);
    }

    protected FeedEntity(Parcel in) {
        this.Feeds_id = in.readInt();
        this.Member_id = in.readString();
        this.Location_id = in.readInt();
        this.Member_name = in.readString();
        this.Content = in.readString();
        this.Img = in.readString();
        this.Date_time = in.readString();
        this.NameTH = in.readString();
        this.AddressTH = in.readString();
        this.NameEng = in.readString();
        this.AddressEng = in.readString();
        this.Tel = in.readString();
        this.Latitude = in.readDouble();
        this.Longtitude = in.readDouble();
        this.Type_id = in.readInt();
        this.Province_id = in.readInt();
        this.Website = in.readString();
    }

    public static final Creator<FeedEntity> CREATOR = new Creator<FeedEntity>() {
        public FeedEntity createFromParcel(Parcel source) {
            return new FeedEntity(source);
        }

        public FeedEntity[] newArray(int size) {
            return new FeedEntity[size];
        }
    };
}
