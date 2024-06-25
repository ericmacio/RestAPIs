package com.eric.restapi.user_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    @UpdateTimestamp
    @JsonProperty("update_date")
    private Date updateDate;

    private final String city = "Grenoble";

    private User() {
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(Long id, String firstName, String lastName, String email) {
        this(firstName, lastName, email);
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }
}
