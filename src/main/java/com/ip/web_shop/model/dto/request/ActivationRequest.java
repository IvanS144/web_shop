package com.ip.web_shop.model.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ActivationRequest {
    @NotNull
    @Min(1)
    private Integer userId;
    @NotBlank
    private String pin;
}
