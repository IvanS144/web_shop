package com.ip.web_shop.service;

import com.ip.web_shop.model.User;
import com.ip.web_shop.model.dto.request.ActivationRequest;
import com.ip.web_shop.model.dto.request.UserRequest;

import java.util.List;

public interface UserService {
    <T> T add(User user, Class<T> returnType);

    <T> T addFromRequest(UserRequest userRequest, Class<T> returnType);

    <T> List<T> get(Integer pageSize, Integer page, Class<T> returnType);

    <T> T update(User user, int id, Class<T> returnType);

    <T> T updateFromRequest(UserRequest userRequest, int id, Class<T> returnType);

    boolean activate(ActivationRequest activationRequest);
    <T> T findById(int id, Class<T> returnType);
}
