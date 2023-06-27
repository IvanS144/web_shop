package com.ip.web_shop.model.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class OfferRequest {
    //private Integer offerId;
    @NotBlank
    private String title;
    @NotNull
    private Double price;
    @NotBlank
    private String location;
    private Boolean isNew;
    @Min(value = 1)
    @NotNull
    private Integer userId;
    @Min(value = 1)
    @NotNull
    private Integer quantity;
    @NotBlank
    private String description;
    @NotEmpty
    private Set<@Min(value = 1) Integer> categoryIds;
    @NotEmpty
    private Set<@Valid AttributeRequest> attributeRequests;
}
