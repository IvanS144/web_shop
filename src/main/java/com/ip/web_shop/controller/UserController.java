package com.ip.web_shop.controller;

import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.dto.UserDTO;
import com.ip.web_shop.model.dto.request.ActivationRequest;
import com.ip.web_shop.model.dto.request.UserRequest;
import com.ip.web_shop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(@RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                 @RequestParam(name="page", defaultValue = "0", required = false) Integer page)
    {
        List<UserDTO> users = userService.get(pageSize, page, UserDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(users);

    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserRequest userRequest){
        //User user = modelMapper.map(userRequest, User.class);
        UserDTO userReply = userService.addFromRequest(userRequest, UserDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(userReply);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserRequest userRequest){
        UserDTO userReply = userService.updateFromRequest(userRequest, id, UserDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(userReply);
    }

    @PostMapping("/activation")
    public ResponseEntity<Boolean> activate(ActivationRequest request){
        if(userService.activate(request)){
            return ResponseEntity.status(200).body(true);
        }
        else{
            return ResponseEntity.status(200).body(false);
        }
    }


}
