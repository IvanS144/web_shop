package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.Message;
import com.ip.web_shop.model.User;
import com.ip.web_shop.model.dto.request.MessageRequest;
import com.ip.web_shop.repository.MessageRepository;
import com.ip.web_shop.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addMessage(MessageRequest request) {
        Message m = new Message();
        m.setText(request.getText());
        m.setRead(false);
        User u = userRepository.findById(request.getUserId()).orElseThrow(()-> new NotFoundException(null));
        m.setUser(u);
        messageRepository.saveAndFlush(m);
    }
}
