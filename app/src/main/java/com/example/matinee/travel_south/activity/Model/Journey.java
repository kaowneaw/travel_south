package com.example.matinee.travel_south.activity.Model;

/**
 * Created by KaowNeaw on 1/14/2016.
 */
public class Journey {

    private int Journey_id;
    private int Location_id;
    private String JourneyDesc;

    public Journey(int journey_id, int location_id, String journeyDesc) {
        Journey_id = journey_id;
        Location_id = location_id;
        JourneyDesc = journeyDesc;
    }

    public int getJourney_id() {
        return Journey_id;
    }

    public void setJourney_id(int journey_id) {
        Journey_id = journey_id;
    }

    public int getLocation_id() {
        return Location_id;
    }

    public void setLocation_id(int location_id) {
        Location_id = location_id;
    }

    public String getJourneyDesc() {
        return JourneyDesc;
    }

    public void setJourneyDesc(String journeyDesc) {
        JourneyDesc = journeyDesc;
    }
}
