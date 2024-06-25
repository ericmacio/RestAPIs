package com.eric.restapi.car_rental_service.controller;

import com.eric.restapi.car_rental_service.model.Vehicle;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public interface VehicleApi {

    @GetMapping(produces = "application/json")
    ResponseEntity<List<Vehicle>> getVehicles();

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<Vehicle> getVehicle(@PathVariable("id") Long id);

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Vehicle> postVehicle(@RequestBody @Valid Vehicle vehicle);

    @PostMapping(value = "/{id}/users/{userId}", produces = "application/json")
    ResponseEntity<Vehicle> postAssociate(@PathVariable Long id, @PathVariable Long userId);

    @DeleteMapping(value = "/{id}/users/{userId}", produces = "application/json")
    ResponseEntity<Vehicle> postDisassociate(@PathVariable Long id, @PathVariable Long userId);

}
