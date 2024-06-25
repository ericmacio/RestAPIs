package com.eric.restapi.springboot_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    private Student student;
    @Autowired
    private SingletonBean bean;

    public WebController(Student student) {
        this.student = student;
    }

    @GetMapping(value = "/info")
    public String display() {
        return student.showInfo();
    }

    @GetMapping(value = "/student")
    public ResponseEntity<Student> getStudent() {
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/api")
    public String getMethod() throws InterruptedException {
        String str = bean.get();
        Thread.sleep(5000);
        String strEnd = str.concat("  - AFTER SLEEP -  ").concat(bean.get());
        System.out.println(strEnd);
        return strEnd;
    }
}
