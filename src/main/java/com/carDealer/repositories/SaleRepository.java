package com.carDealer.repositories;

import com.carDealer.models.entitties.Car;
import com.carDealer.models.entitties.Customer;
import com.carDealer.models.entitties.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Sale findByCarAndCustomer(Car car, Customer customer);

    Sale findByCar(Car car);

    Set<Sale> findAllByCustomer(Customer customer);

    Sale findSaleByCustomerAndCar(Customer customer, Car car);
}
