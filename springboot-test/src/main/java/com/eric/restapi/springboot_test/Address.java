package com.eric.restapi.springboot_test;

import org.springframework.stereotype.Component;

@Component
public class Address {

    private String address = "default";

    public String getAddress() {
        return address;
    }
}
