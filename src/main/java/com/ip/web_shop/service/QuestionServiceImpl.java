package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.Answer;
import com.ip.web_shop.model.Offer;
import com.ip.web_shop.model.Question;
import com.ip.web_shop.model.User;
import com.ip.web_shop.model.dto.AnswerDTO;
import com.ip.web_shop.model.dto.QuestionDTO;
import com.ip.web_shop.model.dto.request.AnswerRequest;
import com.ip.web_shop.model.dto.request.QuestionRequest;
import com.ip.web_shop.repository.AnswerRepository;
import com.ip.web_shop.repository.OfferRepository;
import com.ip.web_shop.repository.QuestionRepository;
import com.ip.web_shop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository, OfferRepository offerRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<QuestionDTO> findQuestionsByOfferId(Integer offerId) {
        return questionRepository.findAllByOfferOfferId(offerId).stream().map(question -> modelMapper.map(question, QuestionDTO.class)).toList();
    }

    @Override
    public QuestionDTO addQuestion(QuestionRequest request) {
        Offer offer = offerRepository.findById(request.getOfferId()).orElseThrow(()-> new NotFoundException(null));
        User u = userRepository.findById(request.getUserId()).orElseThrow(()-> new NotFoundException(null));
        Question question = new Question();
        question.setText(request.getText());
        question.setUser(u);
        question.setOffer(offer);
        questionRepository.saveAndFlush(question);
        entityManager.refresh(question);
        return modelMapper.map(question, QuestionDTO.class);

    }

    @Override
    public AnswerDTO addAnswer(AnswerRequest request) {
        Question question = questionRepository.findById(request.getQuestionId()).orElseThrow(()-> new NotFoundException(null));
        Answer answer = new Answer();
        //answer.setQuestionId(question.getQuestionId());
        answer.setQuestion(question);
        answer.setText(request.getText());
        answerRepository.saveAndFlush(answer);
        return modelMapper.map(answer, AnswerDTO.class);
    }
}
