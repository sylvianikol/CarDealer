package com.carDealer.services;

import com.carDealer.models.dtos.seed.CarSeedDto;
import com.carDealer.models.dtos.views.CarWithListOfPartsViewDto;
import com.carDealer.models.dtos.views.CarWithMakeViewDto;
import com.carDealer.models.entitties.Car;

import java.util.List;
import java.util.Set;

public interface CarService {

    void seedCars(CarSeedDto[] dtos);

    Car getRandomCar();

    Set<Car> getRandomCars();

    List<CarWithMakeViewDto> getCarsWithMake(String make);

    List<CarWithListOfPartsViewDto> getCarsWithParts();
}
