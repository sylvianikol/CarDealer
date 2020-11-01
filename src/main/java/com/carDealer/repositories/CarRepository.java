package com.carDealer.repositories;

import com.carDealer.models.entitties.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByMakeAndModelAndTravelledDistance(String make, String model, Long distance);

    Optional<Car> findById(Long id);

    List<Car> findAllByMakeLikeOrderByModelAscTravelledDistanceDesc(String make);
}
