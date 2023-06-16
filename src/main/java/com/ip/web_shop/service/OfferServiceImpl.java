package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.*;
import com.ip.web_shop.model.composite_keys.OfferHasAttributeKey;
import com.ip.web_shop.model.dto.OfferDTO;
import com.ip.web_shop.model.dto.request.AttributeRequest;
import com.ip.web_shop.model.dto.request.OfferRequest;
import com.ip.web_shop.model.dto.request.SearchRequest;
import com.ip.web_shop.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private final ModelMapper modelMapper;
    private final OfferRepository offerRepository;
    private final CategoryRepository categoryRepository;
    private final OfferHasAttributeRepository offerAttributeRepository;
    private final AttributeRepository attributeRepository;
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public OfferServiceImpl(ModelMapper modelMapper, OfferRepository offerRepository, CategoryRepository categoryRepository, OfferHasAttributeRepository offerAttributeRepository, AttributeRepository attributeRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.offerRepository = offerRepository;
        this.categoryRepository = categoryRepository;
        this.offerAttributeRepository = offerAttributeRepository;
        this.attributeRepository = attributeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public <T> T add(Offer offer, Class<T> returnType){
        offer.setOfferId(null);
        return modelMapper.map(offerRepository.saveAndFlush(offer), returnType);
    }

    @Override
    public <T> T addFromRequest(OfferRequest offerRequest, Class<T> returnType){
        //TODO da li su svi atributi iz odgovarajucih kategorija
        Offer offer = modelMapper.map(offerRequest, Offer.class);
        Set<Category> categories = categoryRepository.findByCategoryIdIn(new ArrayList<>(offerRequest.getCategoryIds()));
        offer.setCategories(categories);
        categories.forEach(category -> category.getOffers().add(offer));
        User user = userRepository.findById(offerRequest.getUserId())
                .orElseThrow(()-> new NotFoundException("User with id "+ offerRequest.getUserId()+" not found"));
        offer.setUser(user);
        user.getOffers().add(offer);
        offer.setOfferId(null);
        offerRepository.saveAndFlush(offer);
        entityManager.refresh(offer);

        //memorisanje vrijednosti atributa
        for(AttributeRequest attributeRequest : offerRequest.getAttributeRequests()){
            Attribute attribute = attributeRepository.findById(attributeRequest.getAttributeId())
                    .orElseThrow(() -> new NotFoundException("Attribute with id "+ attributeRequest.getAttributeId() +" not found"));
            OfferHasAttribute pair = new OfferHasAttribute();
            OfferHasAttributeKey key = new OfferHasAttributeKey();
            key.setOfferId(offer.getOfferId());
            key.setAttributeId(attribute.getAttributeId());
            pair.setId(key);
            pair.setValue(attributeRequest.getValue());
            pair.setOffer(offer);
            pair.setAttribute(attribute);
            offer.getAttributes().add(pair);
            offerAttributeRepository.saveAndFlush(pair);
        }
        entityManager.refresh(offer);
        return modelMapper.map(offer, returnType);
    }

    @Override
    public <T> Page<T> get(Integer pageSize, Integer page, Class<T> returnType){
//        Pageable pageRequest = PageRequest.of(page, pageSize);
//        Page<Offer> offersPage = offerRepository.findAll(pageRequest);
//        List<Offer> offersList = offersPage.toList();
//        return offersList.stream().map(offer -> modelMapper.map(offer, returnType)).toList();
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Offer> offersPage = offerRepository.findAll(pageRequest);
        List<T> offerDTOList = offersPage.getContent().stream().map(offer -> modelMapper.map(offer, returnType)).toList();
        Page<T> resultPage = new PageImpl<T>(offerDTOList, pageRequest, offersPage.getTotalElements());
        return resultPage;
    }

    public Page<OfferDTO> get(Integer pageSize, Integer page){
//        Pageable pageRequest = PageRequest.of(page, pageSize);
//        Page<Offer> offersPage = offerRepository.findAll(pageRequest);
//        List<Offer> offersList = offersPage.toList();
//        return offersList.stream().map(offer -> modelMapper.map(offer, returnType)).toList();
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Offer> offersPage = offerRepository.findAll(pageRequest);
        List<OfferDTO> offerDTOList = offersPage.getContent().stream().map(offer -> modelMapper.map(offer, OfferDTO.class)).toList();
        Page<OfferDTO> resultPage = new PageImpl<>(offerDTOList, pageRequest, offersPage.getTotalElements());
        return resultPage;
    }

    @Override
    public <T> T update(Offer offer, int id, Class<T> returnType){
        if(!offerRepository.existsById(id))
            throw new NotFoundException("Could not find offer with id "+id);
        offer.setOfferId(id);
        return modelMapper.map(offerRepository.saveAndFlush(offer), returnType);
    }

    @Override
    public <T> T updateFromRequest(OfferRequest offerRequest, int id, Class<T> returnType){
        if(!offerRepository.existsById(id))
            throw new NotFoundException("Could not find offer with id "+id);
        Offer offer = modelMapper.map(offerRequest, Offer.class);
        offer.setOfferId(id);
        offerAttributeRepository.deleteAllByOfferId(id);
        Set<Category> categories = categoryRepository.findByCategoryIdIn(new ArrayList<>(offerRequest.getCategoryIds()));
        offer.setCategories(categories);
        Offer finalOffer = offer;
        categories.forEach(category -> category.getOffers().add(finalOffer));
        User user = userRepository.findById(offerRequest.getUserId())
                .orElseThrow(()-> new NotFoundException("User with id "+ offerRequest.getUserId()+" not found"));
        offer.setUser(user);
        user.getOffers().add(offer);
        offer = offerRepository.saveAndFlush(offer);
        offer.setAttributes(new HashSet<>());
        for(AttributeRequest attributeRequest : offerRequest.getAttributeRequests()){
            Attribute attribute = attributeRepository.findById(attributeRequest.getAttributeId())
                    .orElseThrow(() -> new NotFoundException("Attribute with id "+ attributeRequest.getAttributeId() +" not found"));
            OfferHasAttribute pair = new OfferHasAttribute();
            OfferHasAttributeKey key = new OfferHasAttributeKey();
            key.setOfferId(offer.getOfferId());
            key.setAttributeId(attribute.getAttributeId());
            pair.setId(key);
            pair.setValue(attributeRequest.getValue());
            pair.setOffer(offer);
            pair.setAttribute(attribute);
            offer.getAttributes().add(pair);
            offerAttributeRepository.saveAndFlush(pair);
        }
        entityManager.refresh(offer);
        return modelMapper.map(offer, returnType);
    }

    @Override
    public Page<OfferDTO> filter(SearchRequest criteria, int page, int pageSize){
        List<Offer> offers = offerRepository.findByTitleAndCategory(criteria.getText(), criteria.getCategoryId(), criteria.getPriceFrom(), criteria.getPriceTo(), criteria.getIsNew());

        if(criteria.getCategoryId()!=null){
            if(criteria.getAttributes()!=null && !criteria.getAttributes().isEmpty()) {
                offers = offers.stream().filter(offer -> offer.isSearchComplaint(criteria.getAttributes())).toList();
            }
        }
        List<OfferDTO> filteredOffers = offers.stream().map(offer -> modelMapper.map(offer, OfferDTO.class)).toList();

//        int endIndex = Math.min((page * pageSize), (Math.max(filteredOffers.size() - 1, 0)));
//        System.out.println(endIndex);
//        int startIndex = Math.max(0, endIndex-pageSize);
//        System.out.println(startIndex);
        Pageable pageable = PageRequest.of(page, pageSize);
        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex+pageSize, filteredOffers.size());
        return new PageImpl<OfferDTO>(filteredOffers.subList(startIndex, endIndex), PageRequest.of(page, pageSize), filteredOffers.size());
    }

    @Override
    public <T> T findById(int id, Class<T> returnType) {
        Offer o = offerRepository.findById(id).orElseThrow(()-> new NotFoundException("Offer with id "+ id +" not found"));
        return modelMapper.map(o, returnType);
    }

    @Override
    public <T> Page<T> findByUserId(int id, int page, int pageSize, Class<T> returnType) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Offer> offersPage =  offerRepository.findAllByUserId(id, pageRequest);
        List<T> offerDTOList = offersPage.getContent().stream().map(offer -> modelMapper.map(offer, returnType)).toList();
        Page<T> resultPage = new PageImpl<>(offerDTOList, pageRequest, offersPage.getTotalElements());
        return resultPage;
    }
}
