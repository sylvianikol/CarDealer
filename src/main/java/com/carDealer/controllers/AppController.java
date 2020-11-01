package com.carDealer.controllers;

import com.carDealer.models.dtos.seed.CarSeedDto;
import com.carDealer.models.dtos.seed.CustomerSeedDto;
import com.carDealer.models.dtos.seed.PartSeedDto;
import com.carDealer.models.dtos.seed.SupplierSeedDto;
import com.carDealer.models.dtos.views.*;
import com.carDealer.services.*;
import com.carDealer.utils.FileIOUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.carDealer.constants.GlobalConstants.*;

@Component
public class AppController implements CommandLineRunner {

    private final Gson gson;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final BufferedReader bufferedReader;
    private final FileIOUtil fileIOUtil;

    @Autowired
    public AppController(Gson gson,
                         SupplierService supplierService,
                         PartService partService,
                         CarService carService,
                         CustomerService customerService,
                         SaleService saleService,
                         BufferedReader bufferedReader, FileIOUtil fileIOUtil) {
        this.gson = gson;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.bufferedReader = bufferedReader;
        this.fileIOUtil = fileIOUtil;
    }

    @Override
    public void run(String... args) throws Exception {

        // Seed Database
//        this.seedDatabase();

        // Query and Export Data

        // 1. Ordered Customers
//        this.getOrderedCustomers();

        // 2. Cars from Make Toyota
//        this.getCarsWithMake();

        // 3. Local Suppliers
//        this.getLocalSuppliers();

        // 4. Cars with Their List of Parts
//        this.getCarsWithParts();
        
        // 5. Total Sales by Customer
//        this.getTotalSalesByCustomer();
    }

    private void getTotalSalesByCustomer() throws IOException {
        List<CustomerTotalSaleViewDto> customerViewDtos =
                this.customerService.getCustomerTotalSales();

        String dtosToJson = this.gson.toJson(customerViewDtos);

        this.fileIOUtil.write(dtosToJson, TOTAL_SALES_BY_CUSTOMER_FILE_PATH);
        System.out.printf("Total Sales by Customer successfully written to '%s'%n",
                TOTAL_SALES_BY_CUSTOMER_FILE_PATH);
    }

    private void getCarsWithParts() throws IOException {
        List<CarViewDto> carViewDtos =
                this.carService.getCarsWithParts();

        String dtosToJson = this.gson.toJson(carViewDtos);

        this.fileIOUtil.write(dtosToJson, CARS_WITH_PARTS_FILE_PATH);
        System.out.printf("Cars with Parts successfully written to '%s'%n", CARS_WITH_PARTS_FILE_PATH);
    }

    private void getLocalSuppliers() throws IOException {
        List<LocalSupplierViewDto> localSupplierViewDtos =
                this.supplierService.getLocalSuppliers();

        String dtoToJson = this.gson.toJson(localSupplierViewDtos);

        this.fileIOUtil.write(dtoToJson, LOCAL_SUPPLIERS_FILE_PATH);
        System.out.printf("Local Suppliers successfully written to '%s'%n", LOCAL_SUPPLIERS_FILE_PATH);
    }

    private void getCarsWithMake() throws IOException {
        List<CarWithMakeViewDto> carsByMake =
                this.carService.getCarsWithMake("Toyota");

        String dtoToJson = this.gson.toJson(carsByMake);

        this.fileIOUtil.write(dtoToJson, CARS_WITH_MAKE_FILE_PATH);
        System.out.printf("Cars with Make successfully written to '%s'%n", CARS_WITH_MAKE_FILE_PATH);
    }

    private void getOrderedCustomers() throws IOException {
        List<OrderedCustomerViewDto> orderedCustomers =
                this.customerService.getOrderedCustomers();

        String dtosToJson = this.gson.toJson(orderedCustomers);

        this.fileIOUtil.write(dtosToJson, ORDERED_CUSTOMERS_FILE_PATH);
        System.out.printf("Ordered Customers successfully written to '%s'%n", ORDERED_CUSTOMERS_FILE_PATH);
    }

    private void seedSales() {
        this.saleService.seedSales();
        System.out.println("Sales data seeded to database.");
    }

    private void seedCustomers() throws FileNotFoundException {
        CustomerSeedDto[] dtos = this.gson
                .fromJson(new FileReader(CUSTOMERS_FILE_PATH), CustomerSeedDto[].class);

        this.customerService.seedCustomers(dtos);
        System.out.println("Customers data seeded to database.");
    }

    private void seedCars() throws FileNotFoundException {
        CarSeedDto[] dtos = this.gson
                .fromJson(new FileReader(CARS_FILE_PATH), CarSeedDto[].class);

        this.carService.seedCars(dtos);
        System.out.println("Cars data seeded to database.");
    }

    private void seedParts() throws FileNotFoundException {
        PartSeedDto[] dtos = this.gson
                .fromJson(new FileReader(PARTS_FILE_PATH), PartSeedDto[].class);

        this.partService.seedParts(dtos);
        System.out.println("Parts data seeded to database.");
    }

    private void seedSuppliers() throws FileNotFoundException {
        SupplierSeedDto[] dtos = this.gson
                .fromJson(new FileReader(SUPPLIERS_FILE_PATH), SupplierSeedDto[].class);

        this.supplierService.seedSuppliers(dtos);
        System.out.println("Suppliers data seeded to database.");
    }

    private void seedDatabase() throws FileNotFoundException {
        this.seedSuppliers();
        this.seedParts();
        this.seedCars();
        this.seedCustomers();
        this.seedSales();
    }
}
