package com.ip.web_shop.service;

import com.ip.web_shop.model.dto.UserDTO;
import com.ip.web_shop.model.dto.request.LoginRequest;

public interface LoginService {
    UserDTO login(LoginRequest request);
}
