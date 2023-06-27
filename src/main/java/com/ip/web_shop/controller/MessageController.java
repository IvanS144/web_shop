package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.request.MessageRequest;
import com.ip.web_shop.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public void addMessage(@RequestBody @Valid MessageRequest request){
        messageService.addMessage(request);
    }
}
