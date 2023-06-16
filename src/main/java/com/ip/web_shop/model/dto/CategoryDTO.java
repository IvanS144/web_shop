package com.ip.web_shop.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Integer categoryId;
    private String name;
    private List<AttributeDTO> attributes;
}
