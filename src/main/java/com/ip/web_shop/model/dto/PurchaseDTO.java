package com.ip.web_shop.model.dto;

import lombok.Data;

@Data
public class PurchaseDTO {
    private Integer purchaseId;
    private OfferDTO offer;
    private int quantity;
    private UserDTO user;
}
