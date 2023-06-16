package com.ip.web_shop.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
