package com.ip.web_shop.repository;

import com.ip.web_shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    //@Query("SELECT category FROM Category category WHERE category.categoryId IN :list")
    //Set<Category> findAllWhereIdIn(@Param("list") List<Integer> idList);
    Set<Category> findByCategoryIdIn(List<Integer> idList);
    @Query("SELECT category FROM Category category INNER JOIN FETCH category.attributes")
    <T> List<T> getAllMapped(Class<T> returnType);
}
