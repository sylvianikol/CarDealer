package com.carDealer.models.dtos.views;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SaleWithDiscountViewDto {

    @Expose
    private CarViewDto car;
    @Expose
    private String customerName;
    @Expose
    private String Discount;
    @Expose
    private BigDecimal price;
    @Expose
    private BigDecimal priceWithDiscount;

    public SaleWithDiscountViewDto() {
    }

    public CarViewDto getCar() {
        return car;
    }

    public void setCar(CarViewDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
