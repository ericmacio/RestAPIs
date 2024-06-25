package com.eric.restapi.springboot_test;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Scope("singleton") //default
public class SingletonBean {

    @Autowired
    private ObjectFactory<PrototypeBean> prototypeBeanObjectFactory;

    public String get() {
        return this.prototypeBeanObjectFactory.getObject().get();
    }
}
