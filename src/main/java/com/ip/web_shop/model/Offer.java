package com.ip.web_shop.model;

import com.ip.web_shop.model.dto.request.AttributeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"offerId"})
@Entity
@Table(name="offer")
public class Offer {
    @Id
    @Column(name = "offer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer offerId;
    @Basic
    @Column(name="title")
    private String title;
    @Basic
    @Column(name="description")
    private String description;
    @Basic
    @Column(name="price")
    private double price;
    @Basic
    @Column(name="location")
    private String location;
    @Basic
    @Column(name="is_new")
    private boolean isNew;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToMany
    @JoinTable(
            name="offer_has_category",
            joinColumns = @JoinColumn(name="offer_id"),
            inverseJoinColumns = @JoinColumn(name="category_id")
    )
    private Set<Category> categories;
    @OneToMany(mappedBy = "offer")
    private Set<Picture> pictures;
    @OneToMany(mappedBy = "offer")
    private Set<Purchase> purchases;
    @OneToMany(mappedBy = "offer")
    private Set<OfferHasAttribute> attributes;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public boolean isSearchComplaint(List<AttributeRequest> criteriaList){
        //TODO pomocu equals i contains
        criteriaList = criteriaList.stream().filter(criteria -> (!criteria.getValue().isEmpty())&&(!criteria.getValue().isBlank())).toList();
        int count = 0;
        for(var attribute : attributes){
            if(count==criteriaList.size()){
                return true;
            }
            for(var criteria : criteriaList ) {
                if (attribute.isSearchComplaint(criteria.getAttributeId(), criteria.getValue())){
                    count++;
                    break;
                }
            }
        }
        return count == criteriaList.size();
    }
}
