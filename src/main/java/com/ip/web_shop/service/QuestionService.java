package com.ip.web_shop.service;

import com.ip.web_shop.model.Answer;
import com.ip.web_shop.model.Question;
import com.ip.web_shop.model.dto.AnswerDTO;
import com.ip.web_shop.model.dto.QuestionDTO;
import com.ip.web_shop.model.dto.request.AnswerRequest;
import com.ip.web_shop.model.dto.request.QuestionRequest;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> findQuestionsByOfferId(Integer offerId);
    QuestionDTO addQuestion(QuestionRequest request);
    AnswerDTO addAnswer(AnswerRequest request);
}
