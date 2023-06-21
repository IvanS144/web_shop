package com.ip.web_shop.repository;

import com.ip.web_shop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
