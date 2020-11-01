package com.carDealer.repositories;

import com.carDealer.models.entitties.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);

    Supplier getById(Long id);

    List<Supplier> findAllByImporterFalse();
}
