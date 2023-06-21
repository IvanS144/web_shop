package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.AnswerDTO;
import com.ip.web_shop.model.dto.QuestionDTO;
import com.ip.web_shop.model.dto.request.AnswerRequest;
import com.ip.web_shop.model.dto.request.QuestionRequest;
import com.ip.web_shop.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
public class QuestionsController {
    private final QuestionService questionService;

    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/questions")
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody QuestionRequest questionRequest){
        QuestionDTO questionDTO = questionService.addQuestion(questionRequest);
        return ResponseEntity.status(HttpStatus.OK).body(questionDTO);
    }

    @PostMapping("/questions/{id}/answers")
    public ResponseEntity<AnswerDTO> addAnswer(@RequestBody AnswerRequest answerRequest, @PathVariable Integer id){
        AnswerDTO answerDTO = questionService.addAnswer(answerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(answerDTO);
    }
}
