package com.carDealer.repositories;

import com.carDealer.models.entitties.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.carDealer.sql.SqlQueries.SELECT_TOTAL_SALES_BY_CUSTOMER;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByNameAndBirthDate(String name, LocalDateTime birthDate);

    Optional<Customer> findById(Long id);

    List<Customer> findAllByOrderByBirthDateAscYoungDriverAsc();

    @Query("SELECT c FROM Customer c JOIN Sale s ON c.id = s.customer.id GROUP BY c.id")
    List<Customer> getAllWithSales();

    @Query(SELECT_TOTAL_SALES_BY_CUSTOMER)
    List<Customer> getCustomerTotalSales();
}
