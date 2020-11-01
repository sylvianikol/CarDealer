package com.carDealer.services.impl;

import com.carDealer.models.dtos.views.SaleViewDto;
import com.carDealer.models.entitties.Car;
import com.carDealer.models.entitties.Customer;
import com.carDealer.models.entitties.Sale;
import com.carDealer.repositories.SaleRepository;
import com.carDealer.services.CarService;
import com.carDealer.services.CustomerService;
import com.carDealer.services.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository,
                           CarService carService,
                           CustomerService customerService,
                           ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSales() {

        long customerCount = this.customerService.getCustomerCount();

        while (customerCount > 0) {

           Set<Car> randomCars = this.carService.getRandomCars();

            for (Car car : randomCars) {

                Sale sale = new Sale();
                sale.setCar(car);
                sale.setCustomer(this.customerService.getRandomCustomer());

                BigDecimal discount = BigDecimal.valueOf(this.getRandomDiscount());

                sale.setDiscount(sale.getCustomer().isYoungDriver()
                        ? discount.add(BigDecimal.valueOf(0.05))
                        : discount);

                this.saleRepository.saveAndFlush(sale);
            }
            --customerCount;
        }
    }

    @Override
    public Set<SaleViewDto> getSalesByCustomer(Customer customer) {
        Set<Sale> sales = this.saleRepository.findAllByCustomer(customer);
        Set<SaleViewDto> saleViewDtos = new HashSet<>();
        for (Sale sale : sales) {
            SaleViewDto saleViewDto = this.modelMapper.map(sale, SaleViewDto.class);
            saleViewDto.setCar(sale.getCar().getMake() + " " + sale.getCar().getModel());
            saleViewDtos.add(saleViewDto);
        }
        return saleViewDtos;
    }

    private double getRandomDiscount() {
        double[] discounts = { 0.00, 0.05, 0.10, 0.20, 0.30, 0.40, 0.50 };

        Random random = new Random();
        int index = random.nextInt(discounts.length);

        return discounts[index];
    }
}
