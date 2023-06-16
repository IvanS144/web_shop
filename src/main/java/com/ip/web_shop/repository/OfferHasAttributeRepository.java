package com.ip.web_shop.repository;

import com.ip.web_shop.model.Offer;
import com.ip.web_shop.model.OfferHasAttribute;
import com.ip.web_shop.model.composite_keys.OfferHasAttributeKey;
import com.ip.web_shop.model.dto.request.AttributeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferHasAttributeRepository extends JpaRepository<OfferHasAttribute, OfferHasAttributeKey> {
    @Modifying
    @Query("DELETE FROM OfferHasAttribute offerAttribute WHERE offerAttribute.offer.offerId = :offerId")
    void deleteAllByOfferId(Integer offerId);
}
