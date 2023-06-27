package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.HttpException;
import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.ActivationCode;
import com.ip.web_shop.model.User;
import com.ip.web_shop.model.dto.UserDTO;
import com.ip.web_shop.model.dto.request.LoginRequest;
import com.ip.web_shop.repository.ActivationCodeRepository;
import com.ip.web_shop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{
    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public LoginServiceImpl(UserRepository userRepository, ActivationCodeRepository activationCodeRepository, ModelMapper modelMapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.activationCodeRepository = activationCodeRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    @Override
    public UserDTO login(LoginRequest request) {
        User u = userRepository.findByUsernameAndPassword(request.getUserName(), request.getPassword()).orElseThrow(()-> new NotFoundException("Wrong credentials"));
        if(!u.getActivated()){
            if(u.getActivationCodes().stream().noneMatch(code -> code.getValidUntil().isAfter(LocalDateTime.now()))){
                ActivationCode activationCode = new ActivationCode();
                String activationCodeValue = String.valueOf(new SecureRandom().nextInt(10000));
                activationCode.setValue(activationCodeValue);
                activationCode.setValidUntil(LocalDateTime.now().plusMinutes(1L));
                activationCode.setUser(u);
                activationCodeRepository.saveAndFlush(activationCode);
                emailService.sendEmail("Naslov", activationCodeValue, u.getEmail());
            }
        }
        return modelMapper.map(u, UserDTO.class);
    }
}
