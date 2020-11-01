package com.carDealer.repositories;

import com.carDealer.models.entitties.Car;
import com.carDealer.models.entitties.Customer;
import com.carDealer.models.entitties.Part;
import com.carDealer.models.entitties.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    Part findByNameAndPrice(String name, BigDecimal price);

    Optional<Part> findById(Long id);

    int countAllBySupplier(Supplier supplier);

    @Query("SELECT sum(p.price) FROM Part p " +
            "JOIN p.cars c " +
            "JOIN Sale s ON c.id = s.car.id " +
            "JOIN Customer cus ON s.customer.id = :customer_id ")
    BigDecimal sumPriceByCustomer(@Param(value = "customer_id") Long id);
}
