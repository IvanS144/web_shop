package com.ip.web_shop.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="answer")
public class Answer {
    @Id
    @Column(name="question_id")
    Integer questionId;
    @Basic
    @Column(name="text")
    String text;
    @OneToOne
    @MapsId
    @JoinColumn(name="question_id")
    private Question question;
}
