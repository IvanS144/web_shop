package com.ip.web_shop.controller;

import com.ip.web_shop.model.Attribute;
import com.ip.web_shop.model.Offer;
import com.ip.web_shop.model.dto.AttributeDTO;
import com.ip.web_shop.service.AttributeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attributes")
@CrossOrigin(origins = "http://localhost:4200")
public class AttributeController {
    private final AttributeService attributeService;
    private final ModelMapper modelMapper;

    public AttributeController(AttributeService attributeService, ModelMapper modelMapper) {
        this.attributeService = attributeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<AttributeDTO>> getAll(@RequestParam(name ="page_size", defaultValue = "10", required = false) Integer pageSize,
                                                     @RequestParam(name="page", defaultValue = "0", required = false) Integer page)
    {
        List<AttributeDTO> attributes = attributeService.get(pageSize, page, AttributeDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(attributes);

    }

    @PostMapping
    public ResponseEntity<AttributeDTO> create(@RequestBody AttributeDTO attributeDTO){
        Attribute attributeRequest = modelMapper.map(attributeDTO, Attribute.class);
        AttributeDTO attributeReply = attributeService.add(attributeRequest, AttributeDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(attributeReply);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttributeDTO> update(@PathVariable Integer id, @RequestBody AttributeDTO attributeDTO){
        Attribute attributeRequest = modelMapper.map(attributeDTO, Attribute.class);
        AttributeDTO attributeReply = attributeService.update(attributeRequest, id, AttributeDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(attributeReply);
    }


}
