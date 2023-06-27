package com.ip.web_shop.model.dto.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserRequest {
    //private Integer userId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @NotBlank
    private String userName;
    @Size(min = 1)
    @NotBlank
    private String password;
    @Min(value = 1)
    @NotNull
    private Integer cityId;
}
