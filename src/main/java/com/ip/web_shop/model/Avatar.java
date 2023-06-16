package com.ip.web_shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"avatarId"})
@Entity
@Table(name="avatar")
public class Avatar {
    @Id
    @Column(name="avatar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer avatarId;
    @Lob
    @Column(name="bytes")
    private byte[] bytes;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @Basic
    @Column(name="file_name")
    private String fileName;
    @Basic
    @Column(name="content_type")
    private String content_type;
}
