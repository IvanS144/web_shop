package com.ip.web_shop.model;

import com.ip.web_shop.model.composite_keys.OfferHasAttributeKey;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="offer_has_attribute")
public class OfferHasAttribute {
    @EmbeddedId
    private OfferHasAttributeKey id;
    @Basic
    @Column(name="value")
    private String value;
    @ManyToOne
    @MapsId("offerId")
    @JoinColumn(name = "offer_id")
    private Offer offer;
    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    public boolean isSearchComplaint(int attributeId, String value){
        return id.getAttributeId() == attributeId && this.value.equalsIgnoreCase(value);

    }
}
