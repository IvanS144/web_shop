package com.ip.web_shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"userId"})
@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Basic
    @Column(name="first_name")
    private String firstName;
    @Basic
    @Column(name="last_name")
    private String lastName;
    @Basic
    @Column(name="e_mail")
    private String email;
    @Basic
    @Column(name="user_name")
    private String userName;
    @Basic
    @Column(name="password")
    private String password;
    @Basic
    @Column(name = "activated")
    private Boolean activated;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @OneToMany(mappedBy = "user")
    private Set<Purchase> purchases;
    @OneToOne(mappedBy = "user")
    private Avatar avatar;
    @OneToMany(mappedBy = "user")
    private Set<Offer> offers;
    @OneToMany(mappedBy = "user")
    private Set<ActivationCode> activationCodes;
}
