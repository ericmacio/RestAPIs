package com.eric.restapi.car_rental_service.repository;

import com.eric.restapi.car_rental_service.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    Optional<Vehicle> findVehicleById(Long id);
    Optional<Integer> deleteVehicleById(Long id);
}
