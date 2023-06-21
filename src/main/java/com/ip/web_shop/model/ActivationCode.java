package com.ip.web_shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = {"activationCodeId"})
@Entity
@Table(name = "activation_code")
public class ActivationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="activation_code_id")
    private Integer activationCodeId;
    @Basic
    @Column(name = "value")
    private String value;
    @Column(name = "valid_until")
    private LocalDateTime validUntil;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
