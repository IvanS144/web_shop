package com.ip.web_shop.model.dto;

import lombok.Data;

@Data
public class OfferAttributeDTO {
    private AttributeDTO attribute;
    private String value;
    public boolean isSearchComplaint(int attributeId, String value){
        return attribute.getAttributeId() == attributeId && this.value.equalsIgnoreCase(value);

    }
}
