package com.ip.web_shop.service;

import com.ip.web_shop.model.Category;

import java.util.List;

public interface CategoryService {
    <T> T add(Category category, Class<T> returnType);

    <T> List<T> get(Integer pageSize, Integer page, Class<T> returnType);

    <T> T update(Category category, int id, Class<T> returnType);

    <T> List<T> get(Class<T> returnType);
}
