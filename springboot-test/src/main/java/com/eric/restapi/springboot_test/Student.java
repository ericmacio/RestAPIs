package com.eric.restapi.springboot_test;

import org.springframework.stereotype.Component;

@Component
public class Student {

    private Address address;

    public Student(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public String showInfo() {
        return "Student address is: " + address.getAddress();
    }
}
