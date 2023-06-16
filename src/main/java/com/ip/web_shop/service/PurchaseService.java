package com.ip.web_shop.service;

import com.ip.web_shop.model.Purchase;
import com.ip.web_shop.model.dto.request.PurchaseRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PurchaseService {
    <T> T add(Purchase purchase, Class<T> returnType);

    <T> T addFromRequest(PurchaseRequest purchaseRequest, Class<T> returnType);

    <T> Page<T> get(Integer pageSize, Integer page, Class<T> returnType);

    <T> Page<T> findByUserId(int userId, int page, int pageSize, Class<T> returnType);
}
