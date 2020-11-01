package com.carDealer.services.impl;

import com.carDealer.models.dtos.seed.CustomerSeedDto;
import com.carDealer.models.dtos.views.CustomerTotalSaleViewDto;
import com.carDealer.models.dtos.views.OrderedCustomerViewDto;
import com.carDealer.models.dtos.views.SaleViewDto;
import com.carDealer.models.entitties.Customer;
import com.carDealer.models.entitties.Part;
import com.carDealer.models.entitties.Sale;
import com.carDealer.repositories.CustomerRepository;
import com.carDealer.services.CarService;
import com.carDealer.services.CustomerService;
import com.carDealer.services.PartService;
import com.carDealer.services.SaleService;
import com.carDealer.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final SaleService saleService;
    private final CarService carService;
    private final PartService partService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               @Lazy SaleService saleService,
                               CarService carService,
                               PartService partService,
                               ModelMapper modelMapper,
                               ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.saleService = saleService;
        this.carService = carService;
        this.partService = partService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCustomers(CustomerSeedDto[] dtos) {

        for (CustomerSeedDto dto : dtos) {

            LocalDateTime birthDate =
                    LocalDateTime.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss"));

            Customer customer = this.customerRepository
                    .findByNameAndBirthDate(dto.getName(), birthDate);

            if (customer != null) {
                continue;
            }

            if (this.validationUtil.isValid(dto)) {
                customer = this.modelMapper.map(dto, Customer.class);
                customer.setBirthDate(birthDate);

                this.customerRepository.saveAndFlush(customer);
            } else {
                this.validationUtil.violations(dto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        }
    }

    @Override
    public Customer getRandomCustomer() {
        Random random = new Random();
        long id = random.nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.findById(id).orElse(null);
    }

    @Override
    public long getCustomerCount() {
        return this.customerRepository.count();
    }

    @Override
    public List<OrderedCustomerViewDto> getOrderedCustomers() {
        List<OrderedCustomerViewDto> orderedCustomers = new ArrayList<>();
        List<Customer> customers = this.customerRepository
                .findAllByOrderByBirthDateAscYoungDriverAsc();

        for (Customer customer : customers) {
            OrderedCustomerViewDto customerViewDto =
                    this.modelMapper.map(customer, OrderedCustomerViewDto.class);

            customerViewDto.setBirthDate(customer.getBirthDate().toString());

            Set<SaleViewDto> saleViewDtos = this.saleService.getSalesByCustomer(customer);
            customerViewDto.setSales(saleViewDtos);

            orderedCustomers.add(customerViewDto);
        }

        return orderedCustomers;
    }

    @Override
    public List<CustomerTotalSaleViewDto> getCustomerTotalSales() {

        List<Customer> customers = this.customerRepository.getCustomerTotalSales();

        List<CustomerTotalSaleViewDto> customerTotalSaleViewDtos = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerTotalSaleViewDto customerDto = new CustomerTotalSaleViewDto();

            customerDto.setFullName(customer.getName());
            customerDto.setBoughtCars(customer.getCars().size());

            BigDecimal total = new BigDecimal(0);

            for (Sale sale : customer.getCars()) {
                List<Part> parts = sale.getCar().getParts();
                BigDecimal sum = new BigDecimal(0);

                for (Part part : parts) {
                    sum = sum.add(part.getPrice());
                }

                total = total.add(sum);
            }

            customerDto.setSpentMoney(total);

            customerTotalSaleViewDtos.add(customerDto);
        }

        return customerTotalSaleViewDtos;
    }

}
