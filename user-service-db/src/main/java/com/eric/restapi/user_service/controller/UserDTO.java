package com.eric.restapi.user_service.controller;

import com.eric.restapi.user_service.model.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @JsonProperty("first_name")
    @NotEmpty(message = "First name cannot be null or empty")
    private String firstName;

    @JsonProperty("last_name")
    @NotEmpty(message = "Last name cannot be null or empty")
    private String lastName;

    @NotEmpty(message = "email cannot be null or empty")
    //@Email
    @Pattern(regexp = "^(.+)@(.+)$", message = "email is not valid")
    private String email;

    public UserDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}
