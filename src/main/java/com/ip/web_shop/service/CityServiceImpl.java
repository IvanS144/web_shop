package com.ip.web_shop.service;

import com.ip.web_shop.model.dto.CityDTO;
import com.ip.web_shop.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class CityServiceImpl implements CityService{
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CityDTO> getAll() {
        return cityRepository.findAll().stream().map(city -> modelMapper.map(city, CityDTO.class)).toList();
    }
}
