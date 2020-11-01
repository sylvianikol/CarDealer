package com.carDealer.models.dtos.views;

import com.google.gson.annotations.Expose;

public class CarWithListOfPartsViewDto {

    @Expose
    private CarWithPartsViewDto car;

    public CarWithListOfPartsViewDto() {
    }

    public CarWithPartsViewDto getCar() {
        return car;
    }

    public void setCar(CarWithPartsViewDto car) {
        this.car = car;
    }
}
