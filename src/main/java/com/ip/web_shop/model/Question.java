package com.ip.web_shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"questionId"})
@Entity
@Table(name="question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    Integer questionId;
    @Basic
    @Column(name="text")
    String text;
    @ManyToOne
    @JoinColumn(name="offer_id", referencedColumnName = "offer_id")
    private Offer offer;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;
    @OneToOne(mappedBy = "question")
    @PrimaryKeyJoinColumn
    private Answer answer;
}
