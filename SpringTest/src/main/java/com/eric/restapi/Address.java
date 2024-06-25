package com.eric.restapi;

public class Address {

    private String address;

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }
}
