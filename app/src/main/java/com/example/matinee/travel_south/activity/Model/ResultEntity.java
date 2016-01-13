package com.example.matinee.travel_south.activity.Model;

import java.util.List;

/**
 * Created by KaowNeaw on 12/27/2015.
 */
public class ResultEntity {

    private List<LocationEntity> resultsLocation;
    private List<ProvinceEntity> resultsProvince;
    private List<String> results;
    boolean status;

    public List<LocationEntity> getResultsLocation() {
        return resultsLocation;
    }

    public void setResultsLocation(List<LocationEntity> resultsLocation) {
        this.resultsLocation = resultsLocation;
    }

    public List<ProvinceEntity> getResultsProvince() {
        return resultsProvince;
    }

    public void setResultsProvince(List<ProvinceEntity> resultsProvince) {
        this.resultsProvince = resultsProvince;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
