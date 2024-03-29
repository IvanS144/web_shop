package com.ip.web_shop.repository;

import com.ip.web_shop.model.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    @Query("SELECT offer FROM Offer offer WHERE offer.user.userId = :id")
    Page<Offer> findAllByUserId(Integer id, Pageable pageable);
    @Query("SELECT offer from Offer offer INNER JOIN FETCH offer.attributes")
    <T> List<T> getOffersWithAttributes(Class<T> type);

//    @Query("SELECT offer FROM Offer offer")
//    <T> Page<T> findAll(Pageable pageRequest, Class<T> returnType);
    @Query("""
    SELECT DISTINCT offer FROM Offer offer LEFT JOIN FETCH offer.categories category INNER JOIN FETCH offer.attributes
    WHERE (offer.deleted = false) AND (offer.quantity > 0) AND
    ((:text IS NULL) OR (offer.title LIKE %:text%))
    AND ((:categoryId IS NULL) OR (category.categoryId=:categoryId))
    AND ((:priceFrom IS NULL) OR (offer.price >= :priceFrom))
    AND ((:priceTo IS NULL) OR (offer.price <= :priceTo))
    AND ((:isNew IS NULL) OR (offer.isNew = :isNew))
    """)
    List<Offer> findByTitleAndCategory(String text, Integer categoryId, Double priceFrom, Double priceTo, Boolean isNew);

    @Modifying
    @Query("UPDATE Offer o SET o.deleted = :deleted WHERE o.offerId = :offerId")
    void setDeleted(Integer offerId, Boolean deleted);

    Page<Offer> findAllByDeletedIsFalseAndQuantityGreaterThan(Integer quantity, Pageable page);

    Page<Offer> findAllByQuantityGreaterThan(Integer quantity, Pageable pageable);

    Page<Offer> findAllByDeletedIsTrueAndUserUserId(Integer userId, Pageable pageable);

    @Query("SELECT offer FROM Offer offer WHERE offer.quantity = 0 AND offer.deleted = false AND offer.user.userId = :userId")
    Page<Offer> findConcludedByUserId(Integer userId, Pageable pageable);
}
