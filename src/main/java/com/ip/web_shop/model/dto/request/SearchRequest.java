package com.ip.web_shop.model.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class SearchRequest {
    private Integer categoryId;
    private Double priceFrom;
    private Double priceTo;
    private Boolean isNew;
    private String text;
    private List<AttributeRequest> attributes;
}
