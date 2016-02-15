package com.example.matinee.travel_south.activity.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KaowNeaw on 2/13/2016.
 */
public class FestivalEntity implements Parcelable {

    private int festival_id;
    private String festivalName;
    private String fesDetail;
    private String fesLocate;
    private String fesNameEng;
    private String fesContact;
    private String fesDetailEng;
    private int Type_id;
    private int Province_id;
    private int month_id;
    private String fesNameChi;
    private String fesDetailChi;
    private String ImageFesfile;
    private String monthName;


    public int getFestival_id() {
        return festival_id;
    }

    public void setFestival_id(int festival_id) {
        this.festival_id = festival_id;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public String getFesDetail() {
        return fesDetail;
    }

    public void setFesDetail(String fesDetail) {
        this.fesDetail = fesDetail;
    }

    public String getFesLocate() {
        return fesLocate;
    }

    public void setFesLocate(String fesLocate) {
        this.fesLocate = fesLocate;
    }

    public String getFesNameEng() {
        return fesNameEng;
    }

    public void setFesNameEng(String fesNameEng) {
        this.fesNameEng = fesNameEng;
    }

    public String getFesContact() {
        return fesContact;
    }

    public void setFesContact(String fesContact) {
        this.fesContact = fesContact;
    }

    public String getFesDetailEng() {
        return fesDetailEng;
    }

    public void setFesDetailEng(String fesDetailEng) {
        this.fesDetailEng = fesDetailEng;
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

    public int getMonth_id() {
        return month_id;
    }

    public void setMonth_id(int month_id) {
        this.month_id = month_id;
    }

    public String getFesNameChi() {
        return fesNameChi;
    }

    public void setFesNameChi(String fesNameChi) {
        this.fesNameChi = fesNameChi;
    }

    public String getFesDetailChi() {
        return fesDetailChi;
    }

    public void setFesDetailChi(String fesDetailChi) {
        this.fesDetailChi = fesDetailChi;
    }

    public String getImageFesfile() {
        return ImageFesfile;
    }

    public void setImageFesfile(String imageFesfile) {
        ImageFesfile = imageFesfile;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public FestivalEntity(int festival_id, String festivalName, String fesDetail, String fesLocate, String fesNameEng, String fesContact, String fesDetailEng, int type_id, int province_id, int month_id, String fesNameChi, String fesDetailChi, String imageFesfile, String monthName) {
        this.festival_id = festival_id;
        this.festivalName = festivalName;
        this.fesDetail = fesDetail;
        this.fesLocate = fesLocate;
        this.fesNameEng = fesNameEng;
        this.fesContact = fesContact;
        this.fesDetailEng = fesDetailEng;
        Type_id = type_id;
        Province_id = province_id;
        this.month_id = month_id;
        this.fesNameChi = fesNameChi;
        this.fesDetailChi = fesDetailChi;
        ImageFesfile = imageFesfile;
        this.monthName = monthName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.festival_id);
        dest.writeString(this.festivalName);
        dest.writeString(this.fesDetail);
        dest.writeString(this.fesLocate);
        dest.writeString(this.fesNameEng);
        dest.writeString(this.fesContact);
        dest.writeString(this.fesDetailEng);
        dest.writeInt(this.Type_id);
        dest.writeInt(this.Province_id);
        dest.writeInt(this.month_id);
        dest.writeString(this.fesNameChi);
        dest.writeString(this.fesDetailChi);
        dest.writeString(this.ImageFesfile);
        dest.writeString(this.monthName);
    }

    protected FestivalEntity(Parcel in) {
        this.festival_id = in.readInt();
        this.festivalName = in.readString();
        this.fesDetail = in.readString();
        this.fesLocate = in.readString();
        this.fesNameEng = in.readString();
        this.fesContact = in.readString();
        this.fesDetailEng = in.readString();
        this.Type_id = in.readInt();
        this.Province_id = in.readInt();
        this.month_id = in.readInt();
        this.fesNameChi = in.readString();
        this.fesDetailChi = in.readString();
        this.ImageFesfile = in.readString();
        this.monthName = in.readString();
    }

    public static final Creator<FestivalEntity> CREATOR = new Creator<FestivalEntity>() {
        public FestivalEntity createFromParcel(Parcel source) {
            return new FestivalEntity(source);
        }

        public FestivalEntity[] newArray(int size) {
            return new FestivalEntity[size];
        }
    };
}
