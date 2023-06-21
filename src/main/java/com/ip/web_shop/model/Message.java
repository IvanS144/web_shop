package com.ip.web_shop.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_id")
    private Integer messageId;
    @Basic
    @Column(name="text")
    private String text;
    @Basic
    @Column(name="is_read")
    private Boolean read;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
