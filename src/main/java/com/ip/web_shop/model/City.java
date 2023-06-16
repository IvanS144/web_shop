package com.ip.web_shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"cityId"})
@Entity
@Table(name="city")
public class City {
    @Id
    @Column(name="city_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cityId;
    @Basic
    @Column(name="name")
    private String name;
}
