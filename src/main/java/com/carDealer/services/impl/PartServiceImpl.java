package com.carDealer.services.impl;

import com.carDealer.models.dtos.seed.PartSeedDto;
import com.carDealer.models.dtos.views.PartViewDto;
import com.carDealer.models.entitties.Car;
import com.carDealer.models.entitties.Customer;
import com.carDealer.models.entitties.Part;
import com.carDealer.models.entitties.Supplier;
import com.carDealer.repositories.PartRepository;
import com.carDealer.services.PartService;
import com.carDealer.services.SupplierService;
import com.carDealer.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final SupplierService supplierService;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository,
                           SupplierService supplierService,
                           ValidationUtil validationUtil,
                           ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.supplierService = supplierService;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedParts(PartSeedDto[] dtos) {

        for (PartSeedDto dto : dtos) {
            Part part = this.partRepository.findByNameAndPrice(dto.getName(), dto.getPrice());
            if (part != null) {
                continue;
            }

            if (this.validationUtil.isValid(dto)) {
                part = this.modelMapper.map(dto, Part.class);

                part.setSupplier(this.supplierService.getRandomSupplier());

                this.partRepository.saveAndFlush(part);

            } else {
                this.validationUtil.violations(dto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        }
    }

    @Override
    public List<Part> getRandomParts() {
        Random random = new Random();
        int partsCount = random.nextInt(10) + 10;

        List<Part> randomParts = new ArrayList<>();

        while (partsCount > 0) {
            long id = random.nextInt((int) this.partRepository.count()) + 1;
            Part part = this.partRepository.findById(id).orElse(null);
            randomParts.add(part);
            --partsCount;
        }

        return randomParts;
    }

    @Override
    public int countPartsBySupplier(Supplier supplier) {

        return this.partRepository.countAllBySupplier(supplier);
    }

    @Override
    public BigDecimal sumPriceByCustomer(Long id) {
        BigDecimal total = this.partRepository.sumPriceByCustomer(id);
        return null;
    }

}
