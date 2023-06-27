package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.UserDTO;
import com.ip.web_shop.model.dto.request.LoginRequest;
import com.ip.web_shop.service.LoginService;
import com.ip.web_shop.service.UserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@CommonsLog
public class LoginController {
    private final UserService userService;
    private final LoginService loginService;

    public LoginController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid LoginRequest request){
        UserDTO u = loginService.login(request);
        log.info("User" + u.getUserName()+ " [id = "+ u.getUserId()+"] has logged in");
        return ResponseEntity.ok(u);
    }
}
