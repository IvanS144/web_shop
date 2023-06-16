package com.ip.web_shop.model.composite_keys;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Embeddable
public class OfferHasAttributeKey implements Serializable {
    @Column(name="offer_id")
    private int offerId;
    @Column(name="attribute_id")
    private int attributeId;
}
