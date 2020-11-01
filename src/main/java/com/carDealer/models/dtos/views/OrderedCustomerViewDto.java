package com.carDealer.models.dtos.views;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderedCustomerViewDto {

    @Expose
    private Long Id;
    @Expose
    private String Name;
    @Expose
    private String BirthDate;
    @Expose
    private boolean IsYoungerDriver;
    @Expose
    private Set<SaleViewDto> Sales;

    public OrderedCustomerViewDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public boolean isYoungerDriver() {
        return IsYoungerDriver;
    }

    public void setYoungerDriver(boolean youngerDriver) {
        IsYoungerDriver = youngerDriver;
    }

    public Set<SaleViewDto> getSales() {
        return Sales;
    }

    public void setSales(Set<SaleViewDto> sales) {
        Sales = sales;
    }
}
