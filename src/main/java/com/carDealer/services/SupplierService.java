package com.carDealer.services;

import com.carDealer.models.dtos.seed.SupplierSeedDto;
import com.carDealer.models.dtos.views.LocalSupplierViewDto;
import com.carDealer.models.entitties.Supplier;

import java.util.List;

public interface SupplierService {

    void seedSuppliers(SupplierSeedDto[] dtos);

    Supplier getRandomSupplier();

    List<LocalSupplierViewDto> getLocalSuppliers();
}
