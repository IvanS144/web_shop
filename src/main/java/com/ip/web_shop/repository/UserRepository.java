package com.ip.web_shop.repository;

import com.ip.web_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.avatar WHERE u.userId = :userId")
    Optional<User> findByIdIncludeAvatar(int userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.activationCodes WHERE u.userName = :userName AND u.password = :password")
    Optional<User> findByUsernameAndPassword(String userName, String password);
}
