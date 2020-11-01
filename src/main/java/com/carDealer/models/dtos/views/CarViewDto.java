package com.carDealer.models.dtos.views;

import com.google.gson.annotations.Expose;

public class CarViewDto {

    @Expose
    private CarWithPartsViewDto car;

    public CarViewDto() {
    }

    public CarWithPartsViewDto getCar() {
        return car;
    }

    public void setCar(CarWithPartsViewDto car) {
        this.car = car;
    }
}
