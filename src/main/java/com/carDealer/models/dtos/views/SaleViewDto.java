package com.carDealer.models.dtos.views;

import com.google.gson.annotations.Expose;

public class SaleViewDto {

    @Expose
    private String car;

    public SaleViewDto() {
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
}
