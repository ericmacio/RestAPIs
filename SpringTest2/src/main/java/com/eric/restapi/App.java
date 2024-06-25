package com.eric.restapi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        //IoC container - beans location
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Student student = (Student) context.getBean("student_bean");
        student.showInfo();

        ((ConfigurableApplicationContext) context).close();
    }
}
