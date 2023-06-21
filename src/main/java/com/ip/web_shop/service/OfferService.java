package com.ip.web_shop.service;

import com.ip.web_shop.model.Offer;
import com.ip.web_shop.model.dto.OfferDTO;
import com.ip.web_shop.model.dto.request.OfferRequest;
import com.ip.web_shop.model.dto.request.SearchRequest;
import org.springframework.data.domain.Page;

public interface OfferService {
    <T> T add(Offer offer, Class<T> returnType);
    <T> T addFromRequest(OfferRequest offerRequest, Class<T> returnType);
    <T> Page<T> get(Integer pageSize, Integer page, Class<T> returnType);
    <T> T update(Offer offer, int id, Class<T> returnType);
    <T> T updateFromRequest(OfferRequest offerRequest, int id, Class<T> returnType);
    Page<OfferDTO> filter(SearchRequest criteria, int page, int pageSize);
    <T> T findById(int id, Class<T> returnType);
    <T> Page<T> findByUserId(int id, int page, int pageSize, Class<T> returnType);
    void setDeleted(int offerId, boolean deleted);

}
