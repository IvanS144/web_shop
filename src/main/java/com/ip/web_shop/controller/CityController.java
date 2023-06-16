package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.CityDTO;
import com.ip.web_shop.service.CityService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
@CrossOrigin(origins = "http://localhost:4200")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    List<CityDTO> getAll(){
        return cityService.getAll();
    }
}
