package com.example.matinee.travel_south.activity.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by KaowNeaw on 1/30/2016.
 */
public class LocationRecomendEnity extends LocationEntity implements Parcelable {

    private int numCheckIn;

    public LocationRecomendEnity(int location_id, String nameTH, String addressTH, String nameEng, String addressEng, String tel, float latitude, float longtitude, float distance, int type_id, int province_id, String imageLocationFile, List<Journey> listJorney, List<ImageLocation> listImage, int numCheckIn) {
        super(location_id, nameTH, addressTH, nameEng, addressEng, tel, latitude, longtitude, distance, type_id, province_id, imageLocationFile, listJorney, listImage);
        this.numCheckIn = numCheckIn;
    }

    public int getNumCheckIn() {
        return numCheckIn;
    }

    public void setNumCheckIn(int numCheckIn) {
        this.numCheckIn = numCheckIn;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.numCheckIn);
    }

    protected LocationRecomendEnity(Parcel in) {
        super(in);
        this.numCheckIn = in.readInt();
    }

    public static final Creator<LocationRecomendEnity> CREATOR = new Creator<LocationRecomendEnity>() {
        public LocationRecomendEnity createFromParcel(Parcel source) {
            return new LocationRecomendEnity(source);
        }

        public LocationRecomendEnity[] newArray(int size) {
            return new LocationRecomendEnity[size];
        }
    };
}
