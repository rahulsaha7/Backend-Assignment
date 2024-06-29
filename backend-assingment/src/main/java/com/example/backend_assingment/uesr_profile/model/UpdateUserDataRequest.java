package com.example.backend_assingment.uesr_profile.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDataRequest {

    private String firstName;

    private String lastName;

    private String mobile;

    private String password;

    private String userName;

    public boolean isEmpty() {
        return firstName == null
            && lastName == null
            && mobile == null
            && password == null;
    }

}
