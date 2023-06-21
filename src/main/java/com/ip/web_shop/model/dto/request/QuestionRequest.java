package com.ip.web_shop.model.dto.request;

import lombok.Data;

@Data
public class QuestionRequest {
    private Integer userId;
    private String text;
    private Integer offerId;
}
