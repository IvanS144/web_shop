package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.User;
import com.ip.web_shop.model.dto.UserDTO;
import com.ip.web_shop.model.dto.request.LoginRequest;
import com.ip.web_shop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public LoginServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO login(LoginRequest request) {
        User u = userRepository.findByUsernameAndPassword(request.getUserName(), request.getPassword()).orElseThrow(()-> new NotFoundException("Wrong credentials"));
        return modelMapper.map(u, UserDTO.class);
    }
}
