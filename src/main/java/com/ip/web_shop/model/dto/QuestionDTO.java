package com.ip.web_shop.model.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private int questionId;
    private String text;
    private UserDTO user;
    private AnswerDTO answer;
}
