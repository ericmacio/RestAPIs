package com.eric.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class Student {

    @Autowired
    @Qualifier(value="second_address")
    private Address address;

    public Student() {
    }

    public void showInfo() {
        System.out.println("Address: " + address);
    }
}
