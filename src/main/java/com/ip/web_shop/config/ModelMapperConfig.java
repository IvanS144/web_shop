package com.ip.web_shop.config;

import com.ip.web_shop.model.City;
import com.ip.web_shop.model.User;
import com.ip.web_shop.model.dto.request.UserRequest;
import com.ip.web_shop.repository.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    private final OfferRepository offerRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;
    private final OfferHasAttributeRepository offerAttributeRepository;
    private final CityRepository cityRepository;

    public ModelMapperConfig(OfferRepository offerRepository, PurchaseRepository purchaseRepository, UserRepository userRepository, CategoryRepository categoryRepository, AttributeRepository attributeRepository, OfferHasAttributeRepository offerAttributeRepository, CityRepository cityRepository) {
        this.offerRepository = offerRepository;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.attributeRepository = attributeRepository;
        this.offerAttributeRepository = offerAttributeRepository;
        this.cityRepository = cityRepository;
    }

    @Bean
    public ModelMapper provideModelMapper(){
        Converter<Integer, User> integerUserConverter = ctx -> userRepository.getReferenceById(ctx.getSource());
        Converter<Integer, City> integerCityConverter = ctx -> cityRepository.getReferenceById(ctx.getSource());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
//        mapper.createTypeMap(UserRequest.class, User.class)
//                .addMappings(m -> m.using(integerCityConverter).map(UserRequest::getCityId, User::setCity));
        return mapper;
    }
}
