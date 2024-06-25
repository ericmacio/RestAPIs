package com.eric.restapi.car_rental_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "brand cannot be null or empty")
    private String brand;

    @NotEmpty(message = "model cannot be null or empty")
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Status status;

    private Long ownerId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(length = 30)
    private Date associatedDate;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, Status status, Long ownerId, Date associatedDate) {
        this.brand = brand;
        this.model = model;
        this.status = status;
        this.ownerId = ownerId;
        this.associatedDate = associatedDate;
    }

    public Vehicle(Long id, String brand, String model, Status status, Long ownerId, Date associatedDate) {
        this(brand, model, status, ownerId, associatedDate);
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Date getAssociatedDate() {
        return associatedDate;
    }
}
