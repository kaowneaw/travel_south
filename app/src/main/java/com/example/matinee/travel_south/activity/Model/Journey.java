package com.example.matinee.travel_south.activity.Model;

/**
 * Created by KaowNeaw on 1/14/2016.
 */
public class Journey {

    private int Journey_id;
    private int Location_id;
    private String JourneyDesc;
    private String carTh,carEng,airplaneTh,airplaneEng,trainTh,trainEng,busTh,busEng,boatTh,boatEng;

    public Journey(int journey_id, int location_id, String journeyDesc, String carTh, String carEng, String airplaneTh, String airplaneEng, String trainTh, String trainEng, String busTh, String busEng, String boatTh, String boatEng) {
        Journey_id = journey_id;
        Location_id = location_id;
        JourneyDesc = journeyDesc;
        this.carTh = carTh;
        this.carEng = carEng;
        this.airplaneTh = airplaneTh;
        this.airplaneEng = airplaneEng;
        this.trainTh = trainTh;
        this.trainEng = trainEng;
        this.busTh = busTh;
        this.busEng = busEng;
        this.boatTh = boatTh;
        this.boatEng = boatEng;
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

    public String getCarTh() {
        return carTh;
    }

    public void setCarTh(String carTh) {
        this.carTh = carTh;
    }

    public String getCarEng() {
        return carEng;
    }

    public void setCarEng(String carEng) {
        this.carEng = carEng;
    }

    public String getAirplaneTh() {
        return airplaneTh;
    }

    public void setAirplaneTh(String airplaneTh) {
        this.airplaneTh = airplaneTh;
    }

    public String getAirplaneEng() {
        return airplaneEng;
    }

    public void setAirplaneEng(String airplaneEng) {
        this.airplaneEng = airplaneEng;
    }

    public String getTrainTh() {
        return trainTh;
    }

    public void setTrainTh(String trainTh) {
        this.trainTh = trainTh;
    }

    public String getTrainEng() {
        return trainEng;
    }

    public void setTrainEng(String trainEng) {
        this.trainEng = trainEng;
    }

    public String getBusTh() {
        return busTh;
    }

    public void setBusTh(String busTh) {
        this.busTh = busTh;
    }

    public String getBusEng() {
        return busEng;
    }

    public void setBusEng(String busEng) {
        this.busEng = busEng;
    }

    public String getBoatTh() {
        return boatTh;
    }

    public void setBoatTh(String boatTh) {
        this.boatTh = boatTh;
    }

    public String getBoatEng() {
        return boatEng;
    }

    public void setBoatEng(String boatEng) {
        this.boatEng = boatEng;
    }
}
