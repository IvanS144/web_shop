package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.OfferDTO;
import com.ip.web_shop.model.dto.request.OfferRequest;
import com.ip.web_shop.model.dto.request.SearchRequest;
import com.ip.web_shop.service.OfferService;
import lombok.extern.apachecommons.CommonsLog;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/offers")
@CrossOrigin(origins = "http://localhost:4200")
@CommonsLog
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
    public ResponseEntity<OfferDTO> create(@RequestBody @Valid OfferRequest offerRequest){
//        Offer offerRequest = modelMapper.map(offerDTO, Offer.class);
        OfferDTO offerReply = offerService.addFromRequest(offerRequest, OfferDTO.class);
        log.info("User "+offerReply.getUser().getUserName()+" created new offer with id "+ offerReply.getOfferId());
        return ResponseEntity.status(HttpStatus.OK).body(offerReply);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferDTO> update(@PathVariable Integer id, @RequestBody @Valid OfferRequest offerRequest){
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

    @GetMapping("/users/{id}")
    public ResponseEntity<Page<OfferDTO>> getByUserId(@RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                      @RequestParam(name="page", defaultValue = "0", required = false) Integer page,
                                                      @PathVariable Integer id){
        Page<OfferDTO> offers = offerService.findByUserId(id, page, pageSize, OfferDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(offers);
    }

    @DeleteMapping("/{id}")
    public void markAsDeleted(@PathVariable Integer id){
        offerService.setDeleted(id, true);
    }

    @PatchMapping("/{id}")
    public void applySmallChanges(@PathVariable Integer id, @RequestBody OfferDTO offerDTO){
        if(offerDTO.getDeleted()!=null){
            offerService.setDeleted(id, offerDTO.getDeleted());
        }
    }

    @GetMapping("/users/{id}/deleted")
    public ResponseEntity<Page<OfferDTO>> getDeletedByUserId(@RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                             @RequestParam(name="page", defaultValue = "0", required = false) Integer page,
                                                             @PathVariable Integer id){
        Page<OfferDTO> offers= offerService.findDeletedByUserId(id, page, pageSize, OfferDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(offers);
    }

    @GetMapping("/users/{id}/concluded")
    public ResponseEntity<Page<OfferDTO>> getConcludedByUserId(@RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                               @RequestParam(name="page", defaultValue = "0", required = false) Integer page,
                                                               @PathVariable Integer id){
        Page<OfferDTO> offers = offerService.findConcludedByUserId(id, page, pageSize, OfferDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(offers);
    }


}
