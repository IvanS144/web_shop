package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.AnswerDTO;
import com.ip.web_shop.model.dto.QuestionDTO;
import com.ip.web_shop.model.dto.request.AnswerRequest;
import com.ip.web_shop.model.dto.request.QuestionRequest;
import com.ip.web_shop.service.QuestionService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("http://localhost:4200")
@CommonsLog
public class QuestionsController {
    private final QuestionService questionService;

    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/questions")
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody @Valid QuestionRequest questionRequest){
        QuestionDTO questionDTO = questionService.addQuestion(questionRequest);
        log.info("User "+ questionDTO.getUser().getUserName()+"[id = "+ questionDTO.getUser().getUserId()+"] asked a question on the question thread for offer "+ questionRequest.getOfferId());
        return ResponseEntity.status(HttpStatus.OK).body(questionDTO);
    }

    @PostMapping("/questions/{id}/answers")
    public ResponseEntity<AnswerDTO> addAnswer(@RequestBody @Valid AnswerRequest answerRequest, @PathVariable Integer id){
        AnswerDTO answerDTO = questionService.addAnswer(answerRequest);
        log.info("Dealer answered question "+ answerRequest.getQuestionId());
        return ResponseEntity.status(HttpStatus.OK).body(answerDTO);
    }
}
