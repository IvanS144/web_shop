package com.ip.web_shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"categoryId"})
@Entity
@Table(name="category")
public class Category {
    @Id
    @Column(name="category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Basic
    @Column(name="name")
    private String name;
    @ManyToMany(mappedBy = "categories")
    private Set<Offer> offers;
    @OneToMany(mappedBy = "category")
    private Set<Attribute> attributes;
}
