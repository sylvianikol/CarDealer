package com.carDealer.services.impl;

import com.carDealer.models.dtos.seed.SupplierSeedDto;
import com.carDealer.models.dtos.views.LocalSupplierViewDto;
import com.carDealer.models.entitties.Supplier;
import com.carDealer.repositories.SupplierRepository;
import com.carDealer.services.PartService;
import com.carDealer.services.SupplierService;
import com.carDealer.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final PartService partService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository,
                               @Lazy PartService partService,
                               ModelMapper modelMapper,
                               ValidationUtil validationUtil) {
        this.supplierRepository = supplierRepository;
        this.partService = partService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedSuppliers(SupplierSeedDto[] dtos) {

        for (SupplierSeedDto dto : dtos) {
            if (this.supplierRepository.findByName(dto.getName()) != null) {
                continue;
            }

            if (this.validationUtil.isValid(dto)) {
                Supplier supplier = this.modelMapper.map(dto, Supplier.class);

                this.supplierRepository.saveAndFlush(supplier);
            } else {
                this.validationUtil.violations(dto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        }
    }

    @Override
    public Supplier getRandomSupplier() {
        Random random = new Random();
        long id = random.nextInt((int) this.supplierRepository.count()) + 1;

        return this.supplierRepository.getById(id);
    }

    @Override
    public List<LocalSupplierViewDto> getLocalSuppliers() {
        List<Supplier> suppliers =
                this.supplierRepository.findAllByImporterFalse();
        List<LocalSupplierViewDto> supplierViewDtos = new ArrayList<>();

        for (Supplier supplier : suppliers) {
            LocalSupplierViewDto dto =
                    this.modelMapper.map(supplier, LocalSupplierViewDto.class);

            dto.setPartsCount(this.partService.countPartsBySupplier(supplier));

            supplierViewDtos.add(dto);
        }

        return supplierViewDtos;
    }
}
