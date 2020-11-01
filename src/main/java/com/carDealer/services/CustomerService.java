package com.carDealer.services;

import com.carDealer.models.dtos.seed.CustomerSeedDto;
import com.carDealer.models.dtos.views.CustomerTotalSaleViewDto;
import com.carDealer.models.dtos.views.OrderedCustomerViewDto;
import com.carDealer.models.entitties.Customer;

import java.util.List;

public interface CustomerService {

    void seedCustomers(CustomerSeedDto[] dtos);

    Customer getRandomCustomer();

    long getCustomerCount();

    List<OrderedCustomerViewDto> getOrderedCustomers();

    List<CustomerTotalSaleViewDto> getCustomerTotalSales();
}
