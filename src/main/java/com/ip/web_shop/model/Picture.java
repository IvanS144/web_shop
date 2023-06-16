package com.ip.web_shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"pictureId"})
@Entity
@Table(name="picture")
public class Picture {
    @Id
    @Column(name="picture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pictureId;
    @Lob
    @Column(name="bytes")
    private byte[] bytes;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
    @Basic
    @Column(name="file_name")
    private String fileName;
    @Basic
    @Column(name="content_type")
    private String content_type;
}
