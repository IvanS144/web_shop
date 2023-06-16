package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.Offer;
import com.ip.web_shop.model.Purchase;
import com.ip.web_shop.model.User;
import com.ip.web_shop.model.dto.request.PurchaseRequest;
import com.ip.web_shop.repository.OfferRepository;
import com.ip.web_shop.repository.PurchaseRepository;
import com.ip.web_shop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {
    private final ModelMapper modelMapper;
    private final PurchaseRepository purchaseRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public PurchaseServiceImpl(ModelMapper modelMapper, PurchaseRepository purchaseRepository, OfferRepository offerRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.purchaseRepository = purchaseRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public <T> T add(Purchase purchase, Class<T> returnType){
        purchase.setPurchaseId(null);
        return modelMapper.map(purchaseRepository.saveAndFlush(purchase), returnType);
    }

    @Override
    public <T> T addFromRequest(PurchaseRequest purchaseRequest, Class<T> returnType){
        Purchase purchase = modelMapper.map(purchaseRequest, Purchase.class);
        Offer offer = offerRepository.findById(purchaseRequest.getOfferId())
                .orElseThrow(() -> new NotFoundException("Offer with id "+ purchaseRequest.getOfferId()+ " not found"));
        int prevQuantity = offer.getQuantity();
        offer.setQuantity(prevQuantity - purchaseRequest.getQuantity());
        offer = offerRepository.saveAndFlush(offer);
        purchase.setOffer(offer);
        offer.getPurchases().add(purchase);
        User user = userRepository.findById(purchaseRequest.getUserId())
                .orElseThrow(()-> new NotFoundException("User with id "+ purchaseRequest.getUserId() +" not found"));
        purchase.setUser(user);
        user.getPurchases().add(purchase);
        return add(purchase, returnType);
    }

    @Override
    public <T> Page<T> get(Integer pageSize, Integer page, Class<T> returnType){
//        Pageable pageRequest = PageRequest.of(page, pageSize);
//        Page<Purchase> purchasesPage = purchaseRepository.findAll(pageRequest);
//        List<Purchase> purchasesList = purchasesPage.toList();
//        return purchasesList.stream().map(purchase -> modelMapper.map(purchase, returnType)).toList();
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Purchase> purchasesPage = purchaseRepository.findAll(pageRequest);
        List<T> purchaseDTOList = purchasesPage.getContent().stream().map(purchase -> modelMapper.map(purchase, returnType)).toList();
        Page<T> resultPage = new PageImpl<>(purchaseDTOList, pageRequest, purchasesPage.getTotalElements());
        return resultPage;
    }

    @Override
    public <T> Page<T> findByUserId(int userId, int page, int pageSize, Class<T> returnType) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Purchase> purchasesPage = purchaseRepository.findAllByUserUserId(userId, pageRequest);
        List<T> purchaseDTOList = purchasesPage.getContent().stream().map(purchase -> modelMapper.map(purchase, returnType)).toList();
        Page<T> resultPage = new PageImpl<>(purchaseDTOList, pageRequest, purchasesPage.getTotalElements());
        return resultPage;
    }
}
