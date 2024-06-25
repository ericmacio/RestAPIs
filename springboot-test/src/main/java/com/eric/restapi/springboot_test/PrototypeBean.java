package com.eric.restapi.springboot_test;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Scope("prototype")
public class PrototypeBean {

    private String str;

    public PrototypeBean() {
        System.out.println("Prototype created");
        this.str = new Date().toString();
    }

    public String get() {
        return str;
    }
}
