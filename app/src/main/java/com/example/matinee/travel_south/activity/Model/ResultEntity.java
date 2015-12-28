package com.example.matinee.travel_south.activity.Model;

import java.util.List;

/**
 * Created by KaowNeaw on 12/27/2015.
 */
public class ResultEntity {

    private List<LocationEntity> resultsLocation;
    private List<String> re;
    boolean status;

    public List<LocationEntity> getResultsLocation() {
        return resultsLocation;
    }

    public void setResultsLocation(List<LocationEntity> resultsLocation) {
        this.resultsLocation = resultsLocation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getRe() {
        return re;
    }

    public void setRe(List<String> re) {
        this.re = re;
    }
}
