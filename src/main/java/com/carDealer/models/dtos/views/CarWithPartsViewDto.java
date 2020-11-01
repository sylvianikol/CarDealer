package com.carDealer.models.dtos.views;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarWithPartsViewDto {

    @Expose
    private String Make;
    @Expose
    private String Model;
    @Expose
    private Long TravelledDistance;
    @Expose
    private List<PartViewDto> parts;

    public CarWithPartsViewDto() {
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

    public List<PartViewDto> getParts() {
        return parts;
    }

    public void setParts(List<PartViewDto> parts) {
        this.parts = parts;
    }
}
