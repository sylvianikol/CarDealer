package com.carDealer.models.dtos.views;

import com.google.gson.annotations.Expose;

public class CarViewDto {

    @Expose
    private String Make;
    @Expose
    private String Model;
    @Expose
    private Long TravelledDistance;

    public CarViewDto() {
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public Long getTravelledDistance() {
        return TravelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        TravelledDistance = travelledDistance;
    }
}
