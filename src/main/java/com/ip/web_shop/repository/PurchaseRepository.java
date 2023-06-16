package com.ip.web_shop.repository;

import com.ip.web_shop.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query("SELECT purchase FROM Purchase purchase")
    Page<Purchase> findAll(Pageable pageRequest);

    Page<Purchase> findAllByUserUserId(Integer userId, Pageable pageRequest);
}
