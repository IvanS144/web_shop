package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.Attribute;
import com.ip.web_shop.model.Offer;
import com.ip.web_shop.repository.AttributeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AttributeService {
    private final ModelMapper modelMapper;
    private final AttributeRepository attributeRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public AttributeService(ModelMapper modelMapper, AttributeRepository attributeRepository) {
        this.modelMapper = modelMapper;
        this.attributeRepository = attributeRepository;
    }

    public <T> T add(Attribute attribute, Class<T> returnType){
        attribute.setAttributeId(null);
        return modelMapper.map(attributeRepository.saveAndFlush(attribute), returnType);
    }

    public <T> List<T> get(Integer pageSize, Integer page, Class<T> returnType){
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Attribute> attributesPage = attributeRepository.findAll(pageRequest);
        List<Attribute> attributesList = attributesPage.toList();
        return attributesList.stream().map(attribute -> modelMapper.map(attribute, returnType)).toList();
    }

    public <T> T update(Attribute attribute, int id, Class<T> returnType){
        if(!attributeRepository.existsById(id))
            throw new NotFoundException("Could not find attribute with id "+id);
        attribute.setAttributeId(id);
        return modelMapper.map(attributeRepository.saveAndFlush(attribute), returnType);
    }
}
