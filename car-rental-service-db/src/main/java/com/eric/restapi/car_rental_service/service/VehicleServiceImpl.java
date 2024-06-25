package com.eric.restapi.car_rental_service.service;

import com.eric.restapi.car_rental_service.exceptions.NotFoundException;
import com.eric.restapi.car_rental_service.model.Status;
import com.eric.restapi.car_rental_service.model.Vehicle;
import com.eric.restapi.car_rental_service.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicleRepository.findAll().forEach(vehicles::add); //Repository returns Iterable
        return vehicles;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(new Vehicle(
                vehicle.getBrand(),
                vehicle.getModel(),
                Status.AVAILABLE,
                null,
                null));
    }

    @Override
    public Optional<Vehicle> get(Long vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    @Override
    public void validate(Long vehicleId) {
        vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Cannot find vehicle with id: " + vehicleId + ". Cannot validate vehicle"));
    }

    @Override
    public Vehicle associate(Long vehicleId, Long userId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .filter(v -> v.getStatus() == Status.AVAILABLE)
                .orElseThrow(() -> new NotFoundException("Cannot find available vehicle with id: " + vehicleId + ". Cannot associate vehicle"));
        return vehicleRepository.save(new Vehicle(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                Status.ASSOCIATED,
                userId,
                new Date())
        );
    }

    @Override
    public Vehicle disassociate(Long vehicleId, Long userId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .filter(v -> v.getStatus() == Status.ASSOCIATED)
                .filter(v -> userId.equals(v.getOwnerId()))
                .orElseThrow(() -> new NotFoundException("Cannot find associated vehicle with id: " + vehicleId + ". Cannot delete"));
        return vehicleRepository.save(new Vehicle(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                Status.AVAILABLE,
                null,
                null)
        );
    }
}
