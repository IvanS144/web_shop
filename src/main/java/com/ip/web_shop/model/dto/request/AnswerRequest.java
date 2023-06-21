package com.ip.web_shop.model.dto.request;

import lombok.Data;

@Data
public class AnswerRequest {
    private Integer questionId;
    private String text;
}
