package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.OfferDTO;
import com.ip.web_shop.model.dto.request.OfferRequest;
import com.ip.web_shop.model.dto.request.SearchRequest;
import com.ip.web_shop.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("/offers")
@CrossOrigin(origins = "http://localhost:4200")
public class OfferController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Page<OfferDTO>> getAll(@RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                 @RequestParam(name="page", defaultValue = "0", required = false) Integer page)
    {
        Page<OfferDTO> offers = offerService.get(pageSize, page, OfferDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(offers);

    }

    @PostMapping
    public ResponseEntity<OfferDTO> create(@RequestBody OfferRequest offerRequest){
//        Offer offerRequest = modelMapper.map(offerDTO, Offer.class);
        OfferDTO offerReply = offerService.addFromRequest(offerRequest, OfferDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(offerReply);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferDTO> update(@PathVariable Integer id, @RequestBody OfferRequest offerRequest){
        //Offer offerRequest = modelMapper.map(offer, Offer.class);
        OfferDTO offerReply = offerService.updateFromRequest(offerRequest, id, OfferDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(offerReply);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<OfferDTO>> search(@RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                 @RequestParam(name="page", defaultValue = "0", required = false) Integer page,
                                                 @RequestBody SearchRequest searchRequest){
        Page<OfferDTO> offers = offerService.filter(searchRequest, page, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(offers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDTO> getById(@PathVariable Integer id){
        OfferDTO offer = offerService.findById(id, OfferDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(offer);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<OfferDTO>> getByUserId(@RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                      @RequestParam(name="page", defaultValue = "0", required = false) Integer page,
                                                      @PathVariable Integer id){
        Page<OfferDTO> offers = offerService.findByUserId(id, page, pageSize, OfferDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(offers);
    }


}