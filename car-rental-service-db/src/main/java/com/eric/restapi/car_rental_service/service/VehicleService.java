package com.eric.restapi.car_rental_service.service;

import com.eric.restapi.car_rental_service.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    List<Vehicle> getVehicles();

    Vehicle create(Vehicle vehicle);

    Optional<Vehicle> get(Long id);

    void validate(Long id);

    Vehicle associate(Long vehicleId, Long userId);

    Vehicle disassociate(Long vehicleId, Long userId);

}
