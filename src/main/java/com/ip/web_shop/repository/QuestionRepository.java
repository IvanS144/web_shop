package com.ip.web_shop.repository;

import com.ip.web_shop.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllByOfferOfferId(Integer offerId);
}
