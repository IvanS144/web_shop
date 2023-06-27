package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.ActivationCode;
import com.ip.web_shop.model.City;
import com.ip.web_shop.model.User;
import com.ip.web_shop.model.dto.request.ActivationRequest;
import com.ip.web_shop.model.dto.request.UserRequest;
import com.ip.web_shop.repository.ActivationCodeRepository;
import com.ip.web_shop.repository.CityRepository;
import com.ip.web_shop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final EmailService emailService;
    @PersistenceContext
    private EntityManager entityManager;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, CityRepository cityRepository, ActivationCodeRepository activationCodeRepository, EmailService emailService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.activationCodeRepository = activationCodeRepository;
        this.emailService = emailService;
    }

    @Override
    public <T> T add(User user, Class<T> returnType){
        user.setUserId(null);
        return modelMapper.map(userRepository.saveAndFlush(user), returnType);
    }

    @Override
    public <T> T addFromRequest(UserRequest userRequest, Class<T> returnType){
        User user = modelMapper.map(userRequest, User.class);
        City city = cityRepository.findById(userRequest.getCityId())
                .orElseThrow(()-> new NotFoundException("City with id " + userRequest.getCityId() +" not found"));
        user.setCity(city);
        user.setUserId(null);
        user.setActivated(false);
        user = userRepository.saveAndFlush(user);
        ActivationCode activationCode = new ActivationCode();
        activationCode.setValue(String.valueOf(new SecureRandom().nextInt(1000,10000)));
        activationCode.setValidUntil(LocalDateTime.now().plusMinutes(1L));
        activationCode.setUser(user);
        activationCodeRepository.saveAndFlush(activationCode);
        emailService.sendEmail("Naslov", activationCode.getValue(), user.getEmail());
        return modelMapper.map(user, returnType);
    }

    @Override
    public <T> List<T> get(Integer pageSize, Integer page, Class<T> returnType){
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<User> usersPage = userRepository.findAll(pageRequest);
        List<User> usersList = usersPage.toList();
        return usersList.stream().map(user -> modelMapper.map(user, returnType)).toList();
    }

    @Override
    public <T> T update(User user, int id, Class<T> returnType){
        user.setUserId(id);
        return modelMapper.map(userRepository.saveAndFlush(user), returnType);
    }

    @Override
    public <T> T updateFromRequest(UserRequest userRequest, int id, Class<T> returnType) {
        if(!userRepository.existsById(id))
            throw new NotFoundException("Could not find user with id "+id);
        User user = modelMapper.map(userRequest, User.class);
        user.setActivated(true);
        City city = cityRepository.findById(userRequest.getCityId())
                .orElseThrow(()-> new NotFoundException("City with id " + userRequest.getCityId() +" not found"));
        user.setCity(city);
        return update(user, id, returnType);
    }

    @Override
    public boolean activate(ActivationRequest activationRequest) {
        User u = userRepository.findById(activationRequest.getUserId()).orElseThrow(()-> new NotFoundException("User with id" + activationRequest.getUserId()+ " not found"));
        if(u.getActivationCodes().stream().anyMatch(code ->code.getValidUntil().isAfter(LocalDateTime.now()) && code.getValue().equals(activationRequest.getPin()))){
            u.setActivated(true);
            return true;
        }
        return false;
    }

    @Override
    public <T> T findById(int id, Class<T> returnType) {
        User user =  userRepository.findById(id).orElseThrow(()-> new NotFoundException("User with id "+ id +" not found"));
        return modelMapper.map(user, returnType);
    }
}
