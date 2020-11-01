package com.carDealer.services.impl;

import com.carDealer.models.dtos.views.SaleViewDto;
import com.carDealer.models.dtos.views.SaleWithDiscountViewDto;
import com.carDealer.models.entitties.Car;
import com.carDealer.models.entitties.Customer;
import com.carDealer.models.entitties.Part;
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
import java.util.*;

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

    @Override
    public List<SaleWithDiscountViewDto> getSalesWithDiscount() {
        List<Sale> sales = this.saleRepository.findAll();
        List<SaleWithDiscountViewDto> saleWithDiscountViewDtos = new ArrayList<>();

        for (Sale sale : sales) {
            SaleWithDiscountViewDto saleDto =
                    this.modelMapper.map(sale, SaleWithDiscountViewDto.class);

            BigDecimal price = calculatePrice(sale.getCar().getParts());
            BigDecimal discount = sale.getDiscount();

            saleDto.setPrice(price);
            saleDto.setPriceWithDiscount(price.subtract(price.multiply(discount)));

            saleWithDiscountViewDtos.add(saleDto);
        }

        return saleWithDiscountViewDtos;
    }

    private BigDecimal calculatePrice(List<Part> parts) {
        BigDecimal price = new BigDecimal(0);
        for (Part part : parts) {
            price = price.add(part.getPrice());
        }

        return price;
    }

    private double getRandomDiscount() {
        double[] discounts = { 0.00, 0.05, 0.10, 0.20, 0.30, 0.40, 0.50 };

        Random random = new Random();
        int index = random.nextInt(discounts.length);

        return discounts[index];
    }
}
