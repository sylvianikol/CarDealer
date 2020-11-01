package com.carDealer.services;

import com.carDealer.models.dtos.seed.PartSeedDto;
import com.carDealer.models.dtos.views.PartViewDto;
import com.carDealer.models.entitties.Car;
import com.carDealer.models.entitties.Customer;
import com.carDealer.models.entitties.Part;
import com.carDealer.models.entitties.Supplier;

import java.math.BigDecimal;
import java.util.List;

public interface PartService {

    void seedParts(PartSeedDto[] dtos);

    List<Part> getRandomParts();

    int countPartsBySupplier(Supplier supplier);

    BigDecimal sumPriceByCustomer(Long id);
}
