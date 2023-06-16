package com.ip.web_shop.repository;

import com.ip.web_shop.model.City;
import com.ip.web_shop.model.dto.CityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    @Query("SELECT city from City city")
    List<CityDTO> getAllAsDTO();
}
