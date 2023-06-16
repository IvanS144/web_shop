package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.Category;
import com.ip.web_shop.model.dto.CategoryDTO;
import com.ip.web_shop.repository.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public <T> T add(Category category, Class<T> returnType){
        category.setCategoryId(null);
        return modelMapper.map(categoryRepository.saveAndFlush(category), returnType);
    }

    @Override
    public <T> List<T> get(Integer pageSize, Integer page, Class<T> returnType){
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Category> categoriesPage = categoryRepository.findAll(pageRequest);
        List<Category> categoriesList = categoriesPage.toList();
        return categoriesList.stream().map(category -> modelMapper.map(category, returnType)).toList();
    }

    @Override
    public <T> T update(Category category, int id, Class<T> returnType){
        if(!categoryRepository.existsById(id))
            throw new NotFoundException("Could not find category with id "+id);
        category.setCategoryId(id);
        return modelMapper.map(categoryRepository.saveAndFlush(category), returnType);
    }

    @Override
    public <T> List<T> get(Class<T> returnType) {
        return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, returnType)).toList();
    }
}
