package com.ip.web_shop.model.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PurchaseRequest {
    //private Integer purchaseId;
    @Min(value = 1)
    @NotNull
    private Integer offerId;
    @Min(value = 1)
    @NotNull
    private int quantity;
    @Min(value = 1)
    @NotNull
    private Integer userId;
}
