package com.carDealer.services;

import com.carDealer.models.dtos.views.SaleViewDto;
import com.carDealer.models.entitties.Customer;

import java.util.Set;

public interface SaleService {

    void seedSales();

    Set<SaleViewDto> getSalesByCustomer(Customer customer);
}
