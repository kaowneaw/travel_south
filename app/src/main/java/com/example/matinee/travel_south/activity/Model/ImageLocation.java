package com.example.matinee.travel_south.activity.Model;

/**
 * Created by KaowNeaw on 1/23/2016.
 */
public class ImageLocation {
    int ImageLocation_id;
    String ImageLocationFile;
    int Location_id;

    public ImageLocation(int imageLocation_id, String imageLocationFile, int location_id) {
        ImageLocation_id = imageLocation_id;
        ImageLocationFile = imageLocationFile;
        Location_id = location_id;
    }

    public int getImageLocation_id() {
        return ImageLocation_id;
    }

    public void setImageLocation_id(int imageLocation_id) {
        ImageLocation_id = imageLocation_id;
    }

    public String getImageLocationFile() {
        return ImageLocationFile;
    }

    public void setImageLocationFile(String imageLocationFile) {
        ImageLocationFile = imageLocationFile;
    }

    public int getLocation_id() {
        return Location_id;
    }

    public void setLocation_id(int location_id) {
        Location_id = location_id;
    }
}
