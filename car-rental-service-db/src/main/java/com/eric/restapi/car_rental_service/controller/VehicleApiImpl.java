package com.eric.restapi.car_rental_service.controller;

import com.eric.restapi.car_rental_service.exceptions.NotFoundException;
import com.eric.restapi.car_rental_service.model.Vehicle;
import com.eric.restapi.car_rental_service.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehicleApiImpl implements VehicleApi {

    private static final Logger LOG = LoggerFactory.getLogger(VehicleApiImpl.class);

    @Autowired
    private VehicleService vehicleService;

    @Override
    public ResponseEntity<List<Vehicle>> getVehicles() {
        return new ResponseEntity<>(vehicleService.getVehicles(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Vehicle> getVehicle(Long id) {
        return vehicleService.get(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException("Vehicle not found for id: " + id + ". Cannot get"));
    }

    @Override
    public ResponseEntity<Vehicle> postVehicle(Vehicle vehicle) {
        LOG.info("postVehicle");
        return new ResponseEntity<>(vehicleService.create(vehicle), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Vehicle> postAssociate(Long id, Long userId) {
        vehicleService.validate(id);
        return new ResponseEntity<>(vehicleService.associate(id, userId), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Vehicle> postDisassociate(Long id, Long userId) {
        vehicleService.validate(id);
        return new ResponseEntity<>(vehicleService.disassociate(id, userId), HttpStatus.CREATED);
    }

}
