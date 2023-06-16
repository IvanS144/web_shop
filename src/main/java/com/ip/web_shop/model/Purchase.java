package com.ip.web_shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"purchaseId"})
@Entity
@Table(name="purchase")
public class Purchase {
    @Id
    @Column(name = "purchase_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseId;
    @Basic
    @Column(name="quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
