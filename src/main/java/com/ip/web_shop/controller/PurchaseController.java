package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.PurchaseDTO;
import com.ip.web_shop.model.dto.request.PurchaseRequest;
import com.ip.web_shop.service.PurchaseService;
import lombok.extern.apachecommons.CommonsLog;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@CrossOrigin(origins = "http://localhost:4200")
@CommonsLog
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final ModelMapper modelMapper;

    public PurchaseController(PurchaseService purchaseService, ModelMapper modelMapper) {
        this.purchaseService = purchaseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseDTO>> getAll(@RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                    @RequestParam(name="page", defaultValue = "0", required = false) Integer page)
    {
        Page<PurchaseDTO> purchases = purchaseService.get(pageSize, page, PurchaseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(purchases);

    }

    @PostMapping
    public ResponseEntity<PurchaseDTO> create(@RequestBody @Valid PurchaseRequest purchaseRequest){
        //Purchase purchaseRequest = modelMapper.map(purchaseDTO, Purchase.class);
        PurchaseDTO purchaseReply = purchaseService.addFromRequest(purchaseRequest, PurchaseDTO.class);
        log.info("User "+ purchaseReply.getUser().getUserName() +" [id = "+purchaseReply.getUser().getUserId()+"] completed a purchase, purchaseId =  "+ purchaseReply.getPurchaseId());
        return ResponseEntity.status(HttpStatus.OK).body(purchaseReply);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Page<PurchaseDTO>> getByUserId(@PathVariable Integer id,
                                                         @RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                         @RequestParam(name="page", defaultValue = "0", required = false) Integer page){
        Page<PurchaseDTO> purchases = purchaseService.findByUserId(id, page, pageSize, PurchaseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(purchases);
    }


}
