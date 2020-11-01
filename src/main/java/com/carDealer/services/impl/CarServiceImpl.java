package com.carDealer.services.impl;

import com.carDealer.models.dtos.seed.CarSeedDto;
import com.carDealer.models.dtos.views.CarViewDto;
import com.carDealer.models.dtos.views.CarWithMakeViewDto;
import com.carDealer.models.entitties.Car;
import com.carDealer.models.entitties.Customer;
import com.carDealer.models.entitties.Part;
import com.carDealer.repositories.CarRepository;
import com.carDealer.repositories.CustomerRepository;
import com.carDealer.repositories.SaleRepository;
import com.carDealer.services.CarService;
import com.carDealer.services.PartService;
import com.carDealer.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PartService partService;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository,
                          PartService partService,
                          CustomerRepository customerRepository,
                          SaleRepository saleRepository,
                          ModelMapper modelMapper,
                          ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.partService = partService;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    @Transactional
    public void seedCars(CarSeedDto[] dtos) {

        for (CarSeedDto dto : dtos) {

            Car car = this.carRepository.findByMakeAndModelAndTravelledDistance(
                    dto.getMake(), dto.getModel(), dto.getTravelledDistance());

            if (car != null) {
                continue;
            }

            if (this.validationUtil.isValid(dto)) {
                car = this.modelMapper.map(dto, Car.class);
                List<Part> parts = this.partService.getRandomParts();

                for (Part part : parts) {
                    part.getCars().add(car);
                }

                car.setParts(parts);

                this.carRepository.saveAndFlush(car);
            } else {
                this.validationUtil.violations(dto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        }
    }

    @Override
    public Car getRandomCar() {
        Random random = new Random();
        long id = random.nextInt((int) this.carRepository.count()) + 1;
        Car car = this.carRepository.findById(id).orElse(null);
        return car;
    }

    @Override
    public Set<Car> getRandomCars() {
        long carBatchSize = this.carRepository.count() / this.customerRepository.count();
        Set<Car> randomCars = new HashSet<>();

        while (carBatchSize > 0) {
           Car car = this.getRandomCar();
            if (this.saleRepository.findByCar(car) != null) {
                continue;
            }
           randomCars.add(car);

           --carBatchSize;
        }

        return randomCars;
    }

    @Override
    public List<CarWithMakeViewDto> getCarsWithMake(String make) {
        List<Car> cars = this.carRepository
                .findAllByMakeLikeOrderByModelAscTravelledDistanceDesc(make);
        List<CarWithMakeViewDto> carWithMakeViewDtos = new ArrayList<>();

        for (Car car : cars) {
            CarWithMakeViewDto carWithMakeViewDto =
                    this.modelMapper.map(car, CarWithMakeViewDto.class);

            carWithMakeViewDtos.add(carWithMakeViewDto);
        }

        return carWithMakeViewDtos;
    }

    @Override
    public List<CarViewDto> getCarsWithParts() {
        List<Car> cars = this.carRepository.findAll();
        List<CarViewDto> carViewDtos = new ArrayList<>();


        for (Car car : cars) {
            CarViewDto carDto =
                    this.modelMapper.map(car, CarViewDto.class);

            carViewDtos.add(carDto);
        }

        return carViewDtos;
    }


}
