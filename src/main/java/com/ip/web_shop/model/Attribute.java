package com.ip.web_shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"attributeId"})
@Entity
@Table(name="attribute")
public class Attribute {
    @Id
    @Column(name = "attribute_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attributeId;
    @Basic
    @Column(name="name")
    private String name;
    @ManyToOne
    @JoinColumn(name ="category_id", referencedColumnName = "category_id")
    private Category category;
}
