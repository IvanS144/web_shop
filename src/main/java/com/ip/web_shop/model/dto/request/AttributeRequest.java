package com.ip.web_shop.model.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(of = {"attributeId"})
public class AttributeRequest {
    @Min(value = 1)
    @NotNull
    private Integer attributeId;
    @NotBlank
    private String value;
}
