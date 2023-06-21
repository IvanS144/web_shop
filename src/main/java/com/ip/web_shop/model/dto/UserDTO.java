package com.ip.web_shop.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Boolean activated;
    private CityDTO city;
    private AvatarDTO avatar;
}
