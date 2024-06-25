package com.eric.restapi;

public class Student {

    private Address address;
    private String name;

    public Student(Address address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showInfo() {
        System.out.println("Name: " + name + ", address: " + address);
    }

}
